package com.ford.scheduledelivery.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "attendance")
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer attendanceId;

  private Integer enrollmentId;
  private Date attendanceDate;
  private String status; // Present, Absent, Late

  // Constructors
  public Attendance() {}

  public Attendance(Integer attendanceId, Integer enrollmentId, Date attendanceDate, String status) {
    this.attendanceId = attendanceId;
    this.enrollmentId = enrollmentId;
    this.attendanceDate = attendanceDate;
    this.status = status;
  }

  // Getters and Setters
  public Integer getAttendanceId() {
    return attendanceId;
  }

  public void setAttendanceId(Integer attendanceId) {
    this.attendanceId = attendanceId;
  }

  public Integer getEnrollmentId() {
    return enrollmentId;
  }

  public void setEnrollmentId(Integer enrollmentId) {
    this.enrollmentId = enrollmentId;
  }

  public Date getAttendanceDate() {
    return attendanceDate;
  }

  public void setAttendanceDate(Date attendanceDate) {
    this.attendanceDate = attendanceDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Attendance that = (Attendance) o;
    return Objects.equals(attendanceId, that.attendanceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attendanceId);
  }

  @Override
  public String toString() {
    return "Attendance{" +
      "attendanceId=" + attendanceId +
      ", enrollmentId=" + enrollmentId +
      ", attendanceDate=" + attendanceDate +
      ", status='" + status + '\'' +
      '}';
  }
}
