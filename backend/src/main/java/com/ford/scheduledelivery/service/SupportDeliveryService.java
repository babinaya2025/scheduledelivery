// src/main/java/com/ford/scheduledelivery/service/SupportDeliveryService.java
package com.ford.scheduledelivery.service;

import com.ford.scheduledelivery.dto.*;
import com.ford.scheduledelivery.exception.InvalidInputException;
import com.ford.scheduledelivery.exception.ResourceNotFoundException;
import com.ford.scheduledelivery.model.*;
import com.ford.scheduledelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportDeliveryService {

    private final EventRepository eventRepository;
    private final TrainerRepository trainerRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final WeekRepository weekRepository;
    private final TopicRepository topicRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public SupportDeliveryService(
            EventRepository eventRepository,
            TrainerRepository trainerRepository,
            EnrollmentRepository enrollmentRepository,
            UserRepository userRepository,
            WeekRepository weekRepository,
            TopicRepository topicRepository,
            ScheduleRepository scheduleRepository) {
        this.eventRepository = eventRepository;
        this.trainerRepository = trainerRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.weekRepository = weekRepository;
        this.topicRepository = topicRepository;
        this.scheduleRepository = scheduleRepository;
    }

    // ========== ADMIN ENDPOINTS ==========

    /**
     * Get all courses with full details for Admin
     */
    public List<CourseDetailDTO> getAllCoursesForAdmin(String status) {
        List<Event> events = eventRepository.findAll();
        
        return events.stream()
                .map(this::mapToCourseDetailDTO)
                .filter(course -> status == null || status.equals("all") || course.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    // ========== TRAINER ENDPOINTS ==========

    /**
     * Get courses for a specific trainer
     */
    public List<CourseDetailDTO> getCoursesForTrainer(Integer trainerId, String status) {
        if (trainerId == null) {
            throw new InvalidInputException("Trainer ID is required");
        }
        
        List<Event> events = eventRepository.findByTrainerId(trainerId);
        
        return events.stream()
                .map(this::mapToCourseDetailDTO)
                .filter(course -> status == null || status.equals("all") || course.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    /**
     * Update topic covered status (Trainer only)
     */
    @Transactional
    public TopicDTO updateTopicStatus(Long topicId, Boolean covered) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with ID: " + topicId));
        
        topic.setCovered(covered);
        Topic updatedTopic = topicRepository.save(topic);
        
        return mapToTopicDTO(updatedTopic);
    }

    /**
     * Update week links (Trainer only)
     */
    @Transactional
    public WeekDTO updateWeekLinks(Long weekId, UpdateLinksRequestDTO request) {
        Week week = weekRepository.findById(weekId)
                .orElseThrow(() -> new ResourceNotFoundException("Week not found with ID: " + weekId));
        
        if (request.getDriveLink() != null) {
            week.setDriveLink(request.getDriveLink());
        }
        if (request.getGithubLink() != null) {
            week.setGithubLink(request.getGithubLink());
        }
        if (request.getAssignmentLink() != null) {
            week.setAssignmentLink(request.getAssignmentLink());
        }
        
        Week updatedWeek = weekRepository.save(week);
        return mapToWeekDTO(updatedWeek);
    }

    // ========== TRAINEE ENDPOINTS ==========

    /**
     * Get courses for a specific trainee
     */
    public List<CourseDetailDTO> getCoursesForTrainee(Integer traineeId, String status) {
        if (traineeId == null) {
            throw new InvalidInputException("Trainee ID is required");
        }
        
        // Find all enrollments for this trainee
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(traineeId);
        
        // Get schedule IDs from enrollments
        List<Integer> scheduleIds = enrollments.stream()
                .map(Enrollment::getScheduleId)
                .collect(Collectors.toList());
        
        if (scheduleIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Find schedules and get event IDs
        List<Schedule> schedules = scheduleRepository.findAllById(scheduleIds);
        List<String> eventIds = schedules.stream()
                .map(Schedule::getEventId)
                .collect(Collectors.toList());
        
        // Find events
        List<Event> events = eventRepository.findByEventIdIn(eventIds);
        
        return events.stream()
                .map(this::mapToCourseDetailDTO)
                .filter(course -> status == null || status.equals("all") || course.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    // ========== HELPER METHODS ==========

    private CourseDetailDTO mapToCourseDetailDTO(Event event) {
        CourseDetailDTO dto = new CourseDetailDTO();
        dto.setId(event.getEventId());
        dto.setName(event.getEventName());
        dto.setTrainerId(event.getTrainerId());
        
        // Get trainer details
        if (event.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(event.getTrainerId()).orElse(null);
            if (trainer != null) {
                dto.setTrainerName(trainer.getTrainerFirstName() + " " + trainer.getTrainerLastName());
                dto.setTrainerEmail(trainer.getTrainerEmailId());
            }
        }
        
        // Determine status based on schedule
        String status = determineEventStatus(event.getEventId());
        dto.setStatus(status);
        
        // Get trainees enrolled in this course
        List<TraineeDTO> trainees = getTraineesForEvent(event.getEventId());
        dto.setTrainees(trainees);
        
        // Get weeks for this course
        List<Week> weeks = weekRepository.findByEventIdOrderByWeekNumberAsc(event.getEventId());
        List<WeekDTO> weekDTOs = weeks.stream()
                .map(this::mapToWeekDTO)
                .collect(Collectors.toList());
        dto.setWeeks(weekDTOs);
        
        return dto;
    }

    private WeekDTO mapToWeekDTO(Week week) {
        WeekDTO dto = new WeekDTO();
        dto.setWeekId(week.getWeekId());
        dto.setWeekNumber(week.getWeekNumber());
        dto.setDriveLink(week.getDriveLink());
        dto.setGithubLink(week.getGithubLink());
        dto.setAssignmentLink(week.getAssignmentLink());
        
        // Get topics for this week
        List<Topic> topics = week.getTopics();
        List<TopicDTO> topicDTOs = topics.stream()
                .map(this::mapToTopicDTO)
                .collect(Collectors.toList());
        dto.setTopics(topicDTOs);
        
        return dto;
    }

    private TopicDTO mapToTopicDTO(Topic topic) {
        return new TopicDTO(topic.getTopicId(), topic.getTopicName(), topic.getCovered());
    }
    // ✅ UPDATED CODE
    private List<TraineeDTO> getTraineesForEvent(String eventId) {
        // Try to get trainees from PO_ORDER table first (Scheduling module uses this)
        List<User> usersFromPO = userRepository.findByPoOrders_EventIdAndPoOrders_PoStatus(eventId, "APPROVED");
        
        if (!usersFromPO.isEmpty()) {
            return usersFromPO.stream()
                    .map(user -> new TraineeDTO(
                            String.valueOf(user.getUserId()),
                            user.getFirstName() + " " + user.getLastName(),
                            user.getEmail()
                    ))
                    .collect(Collectors.toList());
        }
        
        // Fallback: Try ENROLLMENT table (Support Delivery module may use this)
        List<Schedule> schedules = scheduleRepository.findAllByEventId(eventId);
        
        if (schedules.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Get schedule IDs
        List<Integer> scheduleIds = schedules.stream()
                .map(Schedule::getScheduleId)
                .collect(Collectors.toList());
        
        // Find enrollments
        List<Enrollment> enrollments = enrollmentRepository.findByScheduleIdIn(scheduleIds);
        
        if (enrollments.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Get user IDs
        List<Integer> userIds = enrollments.stream()
                .map(Enrollment::getUserId)
                .distinct()
                .collect(Collectors.toList());
        
        // Get users and filter by role (support both "Participant" and "TRAINEE")
        List<User> users = userRepository.findAllById(userIds);
        
        return users.stream()
                .filter(user -> "TRAINEE".equalsIgnoreCase(user.getRole()) || 
                            "Participant".equalsIgnoreCase(user.getRole()))
                .map(user -> new TraineeDTO(
                        String.valueOf(user.getUserId()),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    private String determineEventStatus(String eventId) {
        List<Schedule> schedules = scheduleRepository.findAllByEventId(eventId);
        
        if (schedules.isEmpty()) {
            return "upcoming";
        }
        
        Schedule schedule = schedules.get(0);
        
        if ("LOCKED".equalsIgnoreCase(schedule.getScheduleStatus())) {  // ✅ Correct
            // Check if end date is in the past
            if (schedule.getEndDateTime() != null && schedule.getEndDateTime().isBefore(java.time.LocalDateTime.now())) {
                return "completed";
            }
            // Check if start date is in the future
            if (schedule.getStartDateTime() != null && schedule.getStartDateTime().isAfter(java.time.LocalDateTime.now())) {
                return "upcoming";
            }
            return "ongoing";
        }
        
        return "upcoming";
    }
    
}
