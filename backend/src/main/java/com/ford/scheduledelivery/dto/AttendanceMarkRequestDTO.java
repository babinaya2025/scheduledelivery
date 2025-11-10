package com.ford.scheduledelivery.dto;

import java.util.Date;
import java.util.List;

public class AttendanceMarkRequestDTO {
  private Integer scheduleId;
  private Date attendanceDate;
  private List<AttendanceEntryDTO> attendances;

  // Constructors
  public AttendanceMarkRequestDTO() {}

  public AttendanceMarkRequestDTO(Integer scheduleId, Date attendanceDate,
                                  List<AttendanceEntryDTO> attendances) {
    this.scheduleId = scheduleId;
    this.attendanceDate = attendanceDate;
    this.attendances = attendances;
  }

  // Getters and Setters
  public Integer getScheduleId() { return scheduleId; }
  public void setScheduleId(Integer scheduleId) { this.scheduleId = scheduleId; }

  public Date getAttendanceDate() { return attendanceDate; }
  public void setAttendanceDate(Date attendanceDate) { this.attendanceDate = attendanceDate; }

  public List<AttendanceEntryDTO> getAttendances() { return attendances; }
  public void setAttendances(List<AttendanceEntryDTO> attendances) {
    this.attendances = attendances;
  }

  // Inner class for attendance entries
  public static class AttendanceEntryDTO {
    private Integer enrollmentId;
    private Integer userId;
    private String status;

    public AttendanceEntryDTO() {}

    public AttendanceEntryDTO(Integer enrollmentId, Integer userId, String status) {
      this.enrollmentId = enrollmentId;
      this.userId = userId;
      this.status = status;
    }

    public Integer getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Integer enrollmentId) { this.enrollmentId = enrollmentId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
  }
}
