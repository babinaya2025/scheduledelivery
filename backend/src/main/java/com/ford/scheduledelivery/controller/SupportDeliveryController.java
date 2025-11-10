// src/main/java/com/ford/scheduledelivery/controller/SupportDeliveryController.java
package com.ford.scheduledelivery.controller;

import com.ford.scheduledelivery.dto.*;
import com.ford.scheduledelivery.exception.InvalidInputException;
import com.ford.scheduledelivery.service.SupportDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support-delivery")
public class SupportDeliveryController {

    private final SupportDeliveryService supportDeliveryService;

    @Autowired
    public SupportDeliveryController(SupportDeliveryService supportDeliveryService) {
        this.supportDeliveryService = supportDeliveryService;
    }

    // ========== ADMIN ENDPOINTS ==========

    /**
     * Get all courses (Admin view)
     * GET /api/support-delivery/admin/courses?status=ongoing
     */
    @GetMapping("/admin/courses")
    public ResponseEntity<List<CourseDetailDTO>> getAllCoursesForAdmin(
            @RequestParam(required = false, defaultValue = "all") String status) {
        List<CourseDetailDTO> courses = supportDeliveryService.getAllCoursesForAdmin(status);
        return ResponseEntity.ok(courses);
    }

    // ========== TRAINER ENDPOINTS ==========

    /**
     * Get courses for a specific trainer
     * GET /api/support-delivery/trainer/courses?trainerId=1&status=ongoing
     */
    @GetMapping("/trainer/courses")
    public ResponseEntity<List<CourseDetailDTO>> getCoursesForTrainer(
            @RequestParam Integer trainerId,
            @RequestParam(required = false, defaultValue = "all") String status) {
        List<CourseDetailDTO> courses = supportDeliveryService.getCoursesForTrainer(trainerId, status);
        return ResponseEntity.ok(courses);
    }

    /**
     * Update topic covered status (Trainer only)
     * PUT /api/support-delivery/trainer/topics/{topicId}/status
     */
    @PutMapping("/trainer/topics/{topicId}/status")
    public ResponseEntity<TopicDTO> updateTopicStatus(
            @PathVariable Long topicId,
            @RequestParam Boolean covered) {
        TopicDTO updated = supportDeliveryService.updateTopicStatus(topicId, covered);
        return ResponseEntity.ok(updated);
    }

    /**
     * Update week links (Trainer only)
     * PUT /api/support-delivery/trainer/weeks/{weekId}/links
     */
    @PutMapping("/trainer/weeks/{weekId}/links")
    public ResponseEntity<WeekDTO> updateWeekLinks(
            @PathVariable Long weekId,
            @RequestBody UpdateLinksRequestDTO request) {
        WeekDTO updated = supportDeliveryService.updateWeekLinks(weekId, request);
        return ResponseEntity.ok(updated);
    }

    // ========== TRAINEE ENDPOINTS ==========

    /**
     * Get courses for a specific trainee
     * GET /api/support-delivery/trainee/courses?traineeId=100&status=ongoing
     */
    @GetMapping("/trainee/courses")
    public ResponseEntity<List<CourseDetailDTO>> getCoursesForTrainee(
            @RequestParam Integer traineeId,
            @RequestParam(required = false, defaultValue = "all") String status) {
        List<CourseDetailDTO> courses = supportDeliveryService.getCoursesForTrainee(traineeId, status);
        return ResponseEntity.ok(courses);
    }
}