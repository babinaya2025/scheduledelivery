package com.ford.scheduledelivery.dto;

import java.util.Date;

public class AttendanceRecordDTO {
  private Integer attendanceId;
  private Integer enrollmentId;
  private Integer userId;
  private String firstName;
  private String lastName;
  private String email;
  private Date attendanceDate;
  private String status;

  // Constructors
  public AttendanceRecordDTO() {}

  public AttendanceRecordDTO(Integer attendanceId, Integer enrollmentId, Integer userId,
                             String firstName, String lastName, String email,
                             Date attendanceDate, String status) {
    this.attendanceId = attendanceId;
    this.enrollmentId = enrollmentId;
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.attendanceDate = attendanceDate;
    this.status = status;
  }

  // Getters and Setters
  public Integer getAttendanceId() { return attendanceId; }
  public void setAttendanceId(Integer attendanceId) { this.attendanceId = attendanceId; }

  public Integer getEnrollmentId() { return enrollmentId; }
  public void setEnrollmentId(Integer enrollmentId) { this.enrollmentId = enrollmentId; }

  public Integer getUserId() { return userId; }
  public void setUserId(Integer userId) { this.userId = userId; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public Date getAttendanceDate() { return attendanceDate; }
  public void setAttendanceDate(Date attendanceDate) { this.attendanceDate = attendanceDate; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}
