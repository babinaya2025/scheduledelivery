package com.ford.scheduledelivery.dto;

public class AttendanceSummaryDTO {
  private Integer userId;
  private String firstName;
  private String lastName;
  private String email;
  private Integer totalPresent;
  private Integer totalAbsent;
  private Integer totalLate;
  private Integer totalSessions;
  private Double attendancePercentage;

  // Constructors
  public AttendanceSummaryDTO() {}

  public AttendanceSummaryDTO(Integer userId, String firstName, String lastName, String email,
                              Integer totalPresent, Integer totalAbsent, Integer totalLate,
                              Integer totalSessions, Double attendancePercentage) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.totalPresent = totalPresent;
    this.totalAbsent = totalAbsent;
    this.totalLate = totalLate;
    this.totalSessions = totalSessions;
    this.attendancePercentage = attendancePercentage;
  }

  // Getters and Setters
  public Integer getUserId() { return userId; }
  public void setUserId(Integer userId) { this.userId = userId; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public Integer getTotalPresent() { return totalPresent; }
  public void setTotalPresent(Integer totalPresent) { this.totalPresent = totalPresent; }

  public Integer getTotalAbsent() { return totalAbsent; }
  public void setTotalAbsent(Integer totalAbsent) { this.totalAbsent = totalAbsent; }

  public Integer getTotalLate() { return totalLate; }
  public void setTotalLate(Integer totalLate) { this.totalLate = totalLate; }

  public Integer getTotalSessions() { return totalSessions; }
  public void setTotalSessions(Integer totalSessions) { this.totalSessions = totalSessions; }

  public Double getAttendancePercentage() { return attendancePercentage; }
  public void setAttendancePercentage(Double attendancePercentage) {
    this.attendancePercentage = attendancePercentage;
  }
}
