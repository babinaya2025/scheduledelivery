// src/main/java/com/ford/scheduledelivery/controller/SchedulingController.java
package com.ford.scheduledelivery.controller;

import com.ford.scheduledelivery.dto.CourseDTO;
import com.ford.scheduledelivery.dto.ParticipantDTO;
import com.ford.scheduledelivery.dto.ScheduleRequestDTO;
import com.ford.scheduledelivery.dto.ScheduleStatusDTO;
import com.ford.scheduledelivery.dto.TrainerDTO;
import com.ford.scheduledelivery.exception.InvalidInputException; // NEW
import com.ford.scheduledelivery.dto.ConflictCheckResultDTO;
import com.ford.scheduledelivery.service.SchedulingService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Removed MessagingException and UnsupportedEncodingException imports as they are now handled by EmailSendingException
// import jakarta.mail.MessagingException;
// import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SchedulingController {

    private final SchedulingService schedulingService;

    @Autowired
    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    // --- User Story 1: Retrieve final list of participants ---

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(schedulingService.getAllEvents());
    }

    @GetMapping("/trainers")
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(schedulingService.getAllTrainers());
    }

    @GetMapping("/participants/approved/{courseId}")
    public ResponseEntity<List<ParticipantDTO>> getApprovedParticipants(@PathVariable("courseId") String eventId) {
        List<ParticipantDTO> participants = schedulingService.getApprovedParticipants(eventId);
        return ResponseEntity.ok(participants);
    }

    // --- User Story 2: Define and lock final training schedule ---

    @PostMapping("/schedules/check-conflicts")
    public ResponseEntity<ConflictCheckResultDTO> checkConflicts(@RequestBody ScheduleRequestDTO request) {
        if (request.getCourseId() == null || request.getTrainerId() == null || request.getStartDateTime() == null || request.getEndDateTime() == null || request.getLocation() == null) {
            throw new InvalidInputException("Course ID, Trainer ID, Start Date/Time, End Date/Time, and Location are required.");
        }
        if (request.getStartDateTime().isAfter(request.getEndDateTime()) || request.getStartDateTime().isEqual(request.getEndDateTime())) {
            throw new InvalidInputException("End Date/Time must be after Start Date/Time.");
        }

        ConflictCheckResultDTO result = schedulingService.checkConflicts(request.getCourseId(), request.getTrainerId(), request.getStartDateTime(), request.getEndDateTime());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/schedules/lock/{courseId}")
    public ResponseEntity<ScheduleStatusDTO> lockSchedule(@PathVariable String courseId, @RequestParam String adminId, @RequestBody ScheduleRequestDTO request) {
        if (request.getTrainerId() == null || request.getStartDateTime() == null || request.getEndDateTime() == null || request.getLocation() == null) {
            throw new InvalidInputException("Trainer ID, Start Date/Time, End Date/Time, and Location are required.");
        }
        if (request.getStartDateTime().isAfter(request.getEndDateTime()) || request.getStartDateTime().isEqual(request.getEndDateTime())) {
            throw new InvalidInputException("End Date/Time must be after Start Date/Time.");
        }
        ScheduleStatusDTO status = schedulingService.lockSchedule(courseId, adminId, request.getTrainerId(), request.getStartDateTime(), request.getEndDateTime(), request.getLocation());
        return ResponseEntity.ok(status);
    }

    @PutMapping("/schedules/unlock/{courseId}")
    public ResponseEntity<ScheduleStatusDTO> unlockSchedule(@PathVariable String courseId, @RequestParam String adminId) {
        ScheduleStatusDTO status = schedulingService.unlockSchedule(courseId, adminId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/schedules/status/{courseId}")
    public ResponseEntity<ScheduleStatusDTO> getScheduleStatus(@PathVariable String courseId) {
        ScheduleStatusDTO status = schedulingService.getScheduleStatus(courseId);
        return ResponseEntity.ok(status);
    }

    // --- User Story 3: Generate and send calendar invite ---

    @PostMapping("/schedules/{courseId}/send-invite")
    public ResponseEntity<Map<String, String>> sendCalendarInvite(@PathVariable String courseId) throws MessagingException {
        // The service now throws EmailSendingException (a RuntimeException),
        // which will be caught by GlobalExceptionHandler.
        String message = schedulingService.sendCalendarInvite(courseId);
        return ResponseEntity.ok(Map.of("message", message));
    }
}
