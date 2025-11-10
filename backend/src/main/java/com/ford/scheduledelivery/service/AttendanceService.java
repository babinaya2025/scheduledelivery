package com.ford.scheduledelivery.service;

import com.ford.scheduledelivery.dto.*;
import com.ford.scheduledelivery.model.*;
import com.ford.scheduledelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceService {

  @Autowired
  private AttendanceRepository attendanceRepository;

  @Autowired(required = false)
  private ScheduleRepository scheduleRepository;

  @Autowired(required = false)
  private EnrollmentRepository enrollmentRepository;

  @Autowired(required = false)
  private UserRepository userRepository;

  @Autowired(required = false)
  private EventRepository eventRepository;

  @Autowired(required = false)
  private TrainerRepository trainerRepository;

  // Get active schedules with details
  public List<ScheduleWithDetailsDTO> getActiveSchedules() {
    if (scheduleRepository == null) {
      return new ArrayList<>();
    }

    List<Schedule> schedules = scheduleRepository.findAll();
    List<ScheduleWithDetailsDTO> result = new ArrayList<>();

    for (Schedule schedule : schedules) {
      String eventName = "";
      String trainerName = "";

      if (eventRepository != null && schedule.getEventId() != null) {
        Optional<Event> event = eventRepository.findById(schedule.getEventId());
        if (event.isPresent()) {
          eventName = event.get().getEventName();
        }
      }

      if (trainerRepository != null && schedule.getTrainerId() != null) {
        Optional<Trainer> trainer = trainerRepository.findById(schedule.getTrainerId());
        if (trainer.isPresent()) {
          trainerName = trainer.get().getTrainerFirstName() + " " + trainer.get().getTrainerLastName();
        }
      }

      ScheduleWithDetailsDTO dto = new ScheduleWithDetailsDTO(
        schedule.getScheduleId(),
        schedule.getEventId(),
        eventName,
        schedule.getStartDateTime(),
        schedule.getEndDateTime(),
        schedule.getTrainerId(),
        trainerName,
        schedule.getLocation()
      );

      result.add(dto);
    }

    return result;
  }

  // Get enrolled employees for a schedule
  public List<EnrolledEmployeeDTO> getEnrolledEmployees(Integer scheduleId) {
    if (enrollmentRepository == null || userRepository == null) {
      return new ArrayList<>();
    }

    List<Enrollment> enrollments = enrollmentRepository.findByScheduleId(scheduleId);
    List<EnrolledEmployeeDTO> result = new ArrayList<>();

    for (Enrollment enrollment : enrollments) {
      if (enrollment.getUserId() != null) {
        Optional<User> userOpt = userRepository.findById(enrollment.getUserId());

        if (userOpt.isPresent()) {
          User user = userOpt.get();
          EnrolledEmployeeDTO dto = new EnrolledEmployeeDTO(
            enrollment.getEnrollmentId(),
            user.getUserId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getDepartmentId() != null ? user.getDepartmentId().toString() : ""
          );
          dto.setCurrentAttendanceStatus("Present");
          result.add(dto);
        }
      }
    }

    return result;
  }

  // Mark attendance in bulk
  public void markAttendanceBulk(AttendanceMarkRequestDTO request) {
    List<Attendance> attendanceList = new ArrayList<>();

    for (AttendanceMarkRequestDTO.AttendanceEntryDTO entry : request.getAttendances()) {
      Attendance existing = attendanceRepository.findByEnrollmentIdAndAttendanceDate(
        entry.getEnrollmentId(), request.getAttendanceDate());

      if (existing != null) {
        existing.setStatus(entry.getStatus());
        attendanceList.add(existing);
      } else {
        Attendance attendance = new Attendance(
          null,
          entry.getEnrollmentId(),
          request.getAttendanceDate(),
          entry.getStatus()
        );
        attendanceList.add(attendance);
      }
    }

    attendanceRepository.saveAll(attendanceList);
  }

  // Get attendance by schedule and date range
  public List<AttendanceRecordDTO> getAttendanceByScheduleAndDateRange(
    Integer scheduleId, Date startDate, Date endDate) {

    List<Attendance> attendanceList = attendanceRepository.findByScheduleAndDateRange(
      scheduleId, startDate, endDate);

    List<AttendanceRecordDTO> result = new ArrayList<>();

    for (Attendance attendance : attendanceList) {
      if (enrollmentRepository != null && attendance.getEnrollmentId() != null) {
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findById(attendance.getEnrollmentId());

        if (enrollmentOpt.isPresent() && userRepository != null) {
          Enrollment enrollment = enrollmentOpt.get();
          Optional<User> userOpt = userRepository.findById(enrollment.getUserId());

          if (userOpt.isPresent()) {
            User user = userOpt.get();
            AttendanceRecordDTO dto = new AttendanceRecordDTO(
              attendance.getAttendanceId(),
              attendance.getEnrollmentId(),
              user.getUserId(),
              user.getFirstName(),
              user.getLastName(),
              user.getEmail(),
              attendance.getAttendanceDate(),
              attendance.getStatus()
            );
            result.add(dto);
          }
        }
      }
    }

    return result;
  }

  // Update attendance status
  public Attendance updateAttendanceStatus(Integer attendanceId, String status) {
    Optional<Attendance> optionalAttendance = attendanceRepository.findById(attendanceId);

    if (optionalAttendance.isPresent()) {
      Attendance attendance = optionalAttendance.get();
      attendance.setStatus(status);
      return attendanceRepository.save(attendance);
    }

    return null;
  }

  // Generate attendance report
  public AttendanceReportDTO generateReport(Integer scheduleId, Date startDate, Date endDate) {
    List<AttendanceRecordDTO> records = getAttendanceByScheduleAndDateRange(scheduleId, startDate, endDate);

    // Group by user and calculate summary
    Map<Integer, AttendanceSummaryDTO> summaryMap = new HashMap<>();

    for (AttendanceRecordDTO record : records) {
      Integer userId = record.getUserId();

      if (!summaryMap.containsKey(userId)) {
        AttendanceSummaryDTO summary = new AttendanceSummaryDTO();
        summary.setUserId(userId);
        summary.setFirstName(record.getFirstName());
        summary.setLastName(record.getLastName());
        summary.setEmail(record.getEmail());
        summary.setTotalPresent(0);
        summary.setTotalAbsent(0);
        summary.setTotalLate(0);
        summary.setTotalSessions(0);
        summaryMap.put(userId, summary);
      }

      AttendanceSummaryDTO summary = summaryMap.get(userId);
      summary.setTotalSessions(summary.getTotalSessions() + 1);

      switch (record.getStatus()) {
        case "Present":
          summary.setTotalPresent(summary.getTotalPresent() + 1);
          break;
        case "Absent":
          summary.setTotalAbsent(summary.getTotalAbsent() + 1);
          break;
        case "Late":
          summary.setTotalLate(summary.getTotalLate() + 1);
          break;
      }
    }

    // Calculate attendance percentage
    for (AttendanceSummaryDTO summary : summaryMap.values()) {
      if (summary.getTotalSessions() > 0) {
        double percentage = (summary.getTotalPresent() * 100.0) / summary.getTotalSessions();
        summary.setAttendancePercentage(percentage);
      } else {
        summary.setAttendancePercentage(0.0);
      }
    }

    // Get event name
    String eventName = "";
    if (scheduleRepository != null && eventRepository != null) {
      Optional<Schedule> scheduleOpt = scheduleRepository.findById(scheduleId);
      if (scheduleOpt.isPresent()) {
        Optional<Event> eventOpt = eventRepository.findById(scheduleOpt.get().getEventId());
        if (eventOpt.isPresent()) {
          eventName = eventOpt.get().getEventName();
        }
      }
    }

    List<AttendanceSummaryDTO> summaryList = new ArrayList<>(summaryMap.values());

    return new AttendanceReportDTO(records, summaryList, eventName);
  }

  // Get all attendance
  public List<Attendance> getAllAttendance() {
    return attendanceRepository.findAll();
  }
}
