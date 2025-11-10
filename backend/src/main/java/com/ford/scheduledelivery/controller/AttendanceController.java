package com.ford.scheduledelivery.controller;

import com.ford.scheduledelivery.dto.*;
import com.ford.scheduledelivery.model.Attendance;
import com.ford.scheduledelivery.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "false")
public class AttendanceController {

  @Autowired
  private AttendanceService attendanceService;

  // Get active schedules with details
  @GetMapping("/schedules")
  public ResponseEntity<List<ScheduleWithDetailsDTO>> getActiveSchedules() {
    List<ScheduleWithDetailsDTO> schedules = attendanceService.getActiveSchedules();
    return ResponseEntity.ok(schedules);
  }

  // Get enrolled employees for a schedule
  @GetMapping("/schedules/{scheduleId}/enrollments")
  public ResponseEntity<List<EnrolledEmployeeDTO>> getEnrolledEmployees(@PathVariable Integer scheduleId) {
    List<EnrolledEmployeeDTO> employees = attendanceService.getEnrolledEmployees(scheduleId);
    return ResponseEntity.ok(employees);
  }

  // Mark attendance in bulk
  @PostMapping("/attendance/mark-bulk")
  public ResponseEntity<String> markAttendanceBulk(@RequestBody AttendanceMarkRequestDTO request) {
    attendanceService.markAttendanceBulk(request);
    return ResponseEntity.ok("Attendance marked successfully");
  }

  // Get attendance records by schedule and date range
  @GetMapping("/attendance/by-schedule")
  public ResponseEntity<List<AttendanceRecordDTO>> getAttendanceBySchedule(
    @RequestParam Integer schedule_id,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start_date,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date) {

    List<AttendanceRecordDTO> records = attendanceService.getAttendanceByScheduleAndDateRange(
      schedule_id, start_date, end_date);
    return ResponseEntity.ok(records);
  }

  // Update attendance status
  @PutMapping("/attendance/{attendanceId}")
  public ResponseEntity<Attendance> updateAttendance(
    @PathVariable Integer attendanceId,
    @RequestParam String status) {

    Attendance updated = attendanceService.updateAttendanceStatus(attendanceId, status);

    if (updated != null) {
      return ResponseEntity.ok(updated);
    }
    return ResponseEntity.notFound().build();
  }

  // Generate attendance report
  @GetMapping("/attendance/report")
  public ResponseEntity<AttendanceReportDTO> generateReport(
    @RequestParam Integer schedule_id,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start_date,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date) {

    AttendanceReportDTO report = attendanceService.generateReport(schedule_id, start_date, end_date);
    return ResponseEntity.ok(report);
  }

  // Get all attendance (for testing)
  @GetMapping("/attendance/all")
  public ResponseEntity<List<Attendance>> getAllAttendance() {
    List<Attendance> attendanceList = attendanceService.getAllAttendance();
    return ResponseEntity.ok(attendanceList);
  }
}
