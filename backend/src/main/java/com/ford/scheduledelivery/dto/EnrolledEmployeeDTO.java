package com.ford.scheduledelivery.dto;

public class EnrolledEmployeeDTO {
  private Integer enrollmentId;
  private Integer userId;
  private String firstName;
  private String lastName;
  private String email;
  private String departmentId;
  private String currentAttendanceStatus;

  // Constructors
  public EnrolledEmployeeDTO() {}

  public EnrolledEmployeeDTO(Integer enrollmentId, Integer userId, String firstName,
                             String lastName, String email, String departmentId) {
    this.enrollmentId = enrollmentId;
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.departmentId = departmentId;
  }

  // Getters and Setters
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

  public String getDepartmentId() { return departmentId; }
  public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

  public String getCurrentAttendanceStatus() { return currentAttendanceStatus; }
  public void setCurrentAttendanceStatus(String currentAttendanceStatus) {
    this.currentAttendanceStatus = currentAttendanceStatus;
  }
}
