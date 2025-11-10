package com.ford.scheduledelivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.ford.scheduledelivery.dto.*;
import com.ford.scheduledelivery.exception.ConflictException;       // NEW
import com.ford.scheduledelivery.exception.EmailSendingException;  // NEW
import com.ford.scheduledelivery.exception.InvalidInputException;  // NEW
import com.ford.scheduledelivery.exception.ResourceNotFoundException; // NEW
import com.ford.scheduledelivery.model.*;
import com.ford.scheduledelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException; // NEW
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchedulingService {

    private final JavaMailSender mailSender;
    private final String lndAdminEmail = "lnd.2025.app@gmail.com";
    private final String lndAdminName = "LnD Admin";

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final PORepository poRepository;
    private final ScheduleRepository scheduleRepository;

    private final Random random = new Random();

    private final ObjectMapper objectMapper;


    @Autowired
    public SchedulingService(JavaMailSender mailSender,
                             EventRepository eventRepository,
                             UserRepository userRepository,
                             TrainerRepository trainerRepository,
                             PORepository poRepository,
                             ScheduleRepository scheduleRepository) {
        this.mailSender = mailSender;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.poRepository = poRepository;
        this.scheduleRepository = scheduleRepository;

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // --- User Story 1: Retrieve final list of participants ---

    @Transactional(readOnly = true)
    public List<CourseDTO> getAllEvents() {
        try {
            return eventRepository.findAll().stream()
                    .map(event -> new CourseDTO(
                            event.getEventId(),
                            event.getEventName(),
                            event.getTrainerId() != null ? String.valueOf(event.getTrainerId()) : null
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve events: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public List<TrainerDTO> getAllTrainers() {
        try {
            return trainerRepository.findAll().stream()
                    .map(trainer -> new TrainerDTO(
                            String.valueOf(trainer.getTrainerId()),
                            trainer.getTrainerFirstName() + " " + trainer.getTrainerLastName(),
                            trainer.getTrainerEmailId()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve trainers: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public List<ParticipantDTO> getApprovedParticipants(String eventId) {
        try {
            // 1. Validate Event existence
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + eventId + " not found."));

            // 2. Find approved Purchase Orders for this event
            List<PO> approvedPOs = poRepository.findByEventIdAndPoStatus(eventId, "APPROVED");

            // 3. Extract unique User IDs from these approved POs
            List<Integer> approvedUserIds = approvedPOs.stream()
                    .map(PO::getUserId)
                    .distinct() // Ensure no duplicate users if multiple POs for same user
                    .collect(Collectors.toList());

            // 4. Fetch User details for these approved User IDs
            List<User> approvedUsers = userRepository.findAllById(approvedUserIds);

            // 5. Convert User entities to ParticipantDTOs
            return approvedUsers.stream()
                    .map(user -> new ParticipantDTO(
                            String.valueOf(user.getUserId()),
                            user.getFirstName() + " " + user.getLastName(),
                            user.getEmail()
                    ))
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            throw e; // Re-throw custom exception
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve approved participants for event " + eventId + ": " + e.getMessage(), e);
        }
    }

    // --- User Story 2: Define and lock final training schedule ---

    @Transactional(readOnly = true)
    public ConflictCheckResultDTO checkConflicts(String eventId, String trainerIdStr, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Integer trainerId;
        try {
            trainerId = Integer.parseInt(trainerIdStr);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid trainer ID format: " + trainerIdStr);
        }

        Trainer trainer = trainerRepository.findByTrainerId(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer with ID " + trainerId + " not found."));

        List<String> conflicts = new ArrayList<>();

        boolean trainerConflict = scheduleRepository.existsByTrainerIdAndEventIdNotAndStartDateTimeBeforeAndEndDateTimeAfter(
                trainerId, eventId, endDateTime, startDateTime
        );
        if (trainerConflict) {
            conflicts.add("Trainer " + trainer.getTrainerFirstName() + " " + trainer.getTrainerLastName() + " is already scheduled for another event during the requested time slot.");
        }

        if (random.nextDouble() > 0.8) {
            conflicts.add("Simulated: Meeting room is unavailable for part of the requested time.");
        }
        if (random.nextDouble() > 0.9) {
            conflicts.add("Simulated: Some key participants have conflicting appointments.");
        }

        return new ConflictCheckResultDTO(!conflicts.isEmpty(), conflicts);
    }

    @Transactional
    public ScheduleStatusDTO lockSchedule(String eventId, String adminId, String trainerIdStr, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        Integer trainerId;
        try {
            trainerId = Integer.parseInt(trainerIdStr);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid trainer ID format: " + trainerIdStr);
        }

        Trainer trainer = trainerRepository.findByTrainerId(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer with ID " + trainerId + " not found."));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + eventId + " not found."));

        Optional<Schedule> existingScheduleOpt = scheduleRepository.findByEventId(eventId);
        Schedule schedule;

        if (existingScheduleOpt.isPresent()) {
            schedule = existingScheduleOpt.get();
            if ("LOCKED".equals(schedule.getScheduleStatus())) {
                throw new ConflictException("Schedule for event " + eventId + " is already locked.");
            }
        } else {
            schedule = new Schedule();
            schedule.setEventId(eventId);
            schedule.setScheduleStatus("UNLOCKED");
        }

        schedule.setTrainerId(trainerId);
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);
        schedule.setLocation(location);
        schedule.setScheduleStatus("LOCKED");
        schedule.setLastModified(LocalDateTime.now());
        schedule.setLockedByAdminId(adminId);

        try {
            Schedule savedSchedule = scheduleRepository.save(schedule);
            System.out.println("Backend Lock: Saved Schedule Status for " + eventId + ": " + savedSchedule.getScheduleStatus());
            ScheduleStatusDTO dto = convertScheduleToDTO(savedSchedule, trainer);
            System.out.println("Backend Lock: Returning DTO: " + objectMapper.writeValueAsString(dto));
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save locked schedule for event " + eventId + ": " + e.getMessage(), e);
        }
    }

    @Transactional
    public ScheduleStatusDTO unlockSchedule(String eventId, String adminId) {
        Schedule schedule = scheduleRepository.findByEventId(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule for event " + eventId + " not found."));

        if (!"LOCKED".equals(schedule.getScheduleStatus())) {
            throw new ConflictException("Schedule for event " + eventId + " is not locked and cannot be unlocked.");
        }

        schedule.setScheduleStatus("UNLOCKED");
        schedule.setLastModified(LocalDateTime.now());
        schedule.setLockedByAdminId(adminId); // Keep who unlocked it

        try {
            Schedule savedSchedule = scheduleRepository.save(schedule);
            System.out.println("Backend Unlock: Saved Schedule Status for " + eventId + ": " + savedSchedule.getScheduleStatus());
            Trainer trainer = trainerRepository.findByTrainerId(savedSchedule.getTrainerId())
                    .orElse(null);
            ScheduleStatusDTO dto = convertScheduleToDTO(savedSchedule, trainer);
            System.out.println("Backend Unlock: Returning DTO: " + objectMapper.writeValueAsString(dto));
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save unlocked schedule for event " + eventId + ": " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public ScheduleStatusDTO getScheduleStatus(String eventId) {
        try {
            Optional<Schedule> scheduleOpt = scheduleRepository.findByEventId(eventId);

            if (scheduleOpt.isPresent()) {
                Schedule schedule = scheduleOpt.get();
                Trainer trainer = trainerRepository.findByTrainerId(schedule.getTrainerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Trainer for schedule " + eventId + " not found."));
                ScheduleStatusDTO dto = convertScheduleToDTO(schedule, trainer);
                System.out.println("Backend Get: Schedule Status for " + eventId + ": " + schedule.getScheduleStatus());
                System.out.println("Backend Get: Returning DTO: " + objectMapper.writeValueAsString(dto));
                return dto;
            } else {
                System.out.println("Backend Get: No Schedule found for " + eventId + ", returning UNLOCKED default.");
                return new ScheduleStatusDTO(eventId, false, null, null, null, null, null, null, null);
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve schedule status for event " + eventId + ": " + e.getMessage(), e);
        }
    }

    private ScheduleStatusDTO convertScheduleToDTO(Schedule schedule, Trainer trainer) {
        String trainerName = (trainer != null) ? trainer.getTrainerFirstName() + " " + trainer.getTrainerLastName() : null;
        String trainerIdStr = (trainer != null) ? String.valueOf(trainer.getTrainerId()) : null;

        return new ScheduleStatusDTO(
                schedule.getEventId(),
                "LOCKED".equalsIgnoreCase(schedule.getScheduleStatus()),
                schedule.getLockedByAdminId(),
                schedule.getLastModified(),
                schedule.getStartDateTime(),
                schedule.getEndDateTime(),
                trainerIdStr,
                trainerName,
                schedule.getLocation()
        );
    }

    // --- User Story 3: Generate and send calendar invite ---

    public String sendCalendarInvite(String eventId) throws MessagingException {
        Schedule schedule = scheduleRepository.findByEventId(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule for event " + eventId + " not found."));

        if (!"LOCKED".equals(schedule.getScheduleStatus())) {
            throw new ConflictException("Schedule for event " + eventId + " must be locked before sending invites.");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + eventId + " not found."));

        Trainer trainer = trainerRepository.findByTrainerId(schedule.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer for schedule " + eventId + " not found."));

        List<ParticipantDTO> participantsDTO = getApprovedParticipants(eventId);

        List<String> recipients = participantsDTO.stream()
                .map(ParticipantDTO::getEmail)
                .collect(Collectors.toList());
        recipients.add(trainer.getTrainerEmailId());
        if (!recipients.contains("babinay1@ford.com")) {
            recipients.add("babinay1@ford.com");
        }
        if (!recipients.contains(lndAdminEmail)) {
            recipients.add(lndAdminEmail);
        }

        String subject = "Calendar Invite: " + event.getEventName() + " Training";
        String body = String.format(
                "Dear Participant/Trainer,\n\n" +
                        "You are invited to the following training session:\n\n" +
                        "Course: %s\n" +
                        "Trainer: %s\n" +
                        "Start: %s\n" +
                        "End: %s\n\n" +
                        "Location: %s\n\n" +
                        "Please find the attached .ics file to add this event to your calendar.\n\n" +
                        "Regards,\n%s",
                event.getEventName(),
                trainer.getTrainerFirstName() + " " + trainer.getTrainerLastName(),
                schedule.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                schedule.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                schedule.getLocation(),
                lndAdminName
        );

        String icsContent = generateIcsContent(event, schedule, trainer, participantsDTO);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setFrom(lndAdminEmail, lndAdminName);
            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment("training_invite.ics", new jakarta.mail.util.ByteArrayDataSource(icsContent.getBytes("UTF-8"), "text/calendar;charset=UTF-8"));
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException |
                 MailException e) { // Catch Spring's MailException too
            throw new EmailSendingException("Failed to send calendar invite for event " + eventId + ": " + e.getMessage(), e);
        }

        return "Calendar invite sent successfully for course " + eventId + "!";
    }

    private String generateIcsContent(Event event, Schedule schedule, Trainer trainer, List<ParticipantDTO> participants) {
        DateTimeFormatter icsFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
        String start = schedule.getStartDateTime().format(icsFormatter);
        String end = schedule.getEndDateTime().format(icsFormatter);
        String created = LocalDateTime.now().format(icsFormatter);

        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:VCALENDAR\n");
        builder.append("VERSION:2.0\n");
        builder.append("PRODID:-//Ford LND Scheduling//NONSGML v1.0//EN\n");
        builder.append("BEGIN:VEVENT\n");
        builder.append("UID:").append(event.getEventId()).append("-").append(start).append("@ford.com\n");
        builder.append("DTSTAMP:").append(created).append("\n");
        builder.append("DTSTART:").append(start).append("\n");
        builder.append("DTEND:").append(end).append("\n");
        builder.append("SUMMARY:").append(event.getEventName()).append(" Training\n");
        builder.append("DESCRIPTION:").append(event.getEventName()).append(" training with ").append(trainer.getTrainerFirstName()).append(" ").append(trainer.getTrainerLastName()).append(".\n");
        builder.append("LOCATION:").append(schedule.getLocation()).append("\n");
        builder.append("ORGANIZER;CN=").append(lndAdminName).append(":mailto:").append(lndAdminEmail).append("\n");
        builder.append("ATTENDEE;ROLE=REQ-PARTICIPANT;CN=").append(trainer.getTrainerFirstName()).append(" ").append(trainer.getTrainerLastName()).append(":mailto:").append(trainer.getTrainerEmailId()).append("\n");
        for (ParticipantDTO p : participants) {
            builder.append("ATTENDEE;ROLE=REQ-PARTICIPANT;CN=").append(p.getName()).append(":mailto:").append(p.getEmail()).append("\n");
        }
        builder.append("END:VEVENT\n");
        builder.append("END:VCALENDAR\n");
        return builder.toString();
    }
}
