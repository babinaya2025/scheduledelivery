// src/main/java/com/ford/scheduledelivery/model/Enrollment.java
package com.ford.scheduledelivery.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects; // For equals and hashCode

@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrollmentId;

    private Integer scheduleId;
    private Integer userId;
    private Date enrollmentDate;
    private String status;

    // @NoArgsConstructor
    public Enrollment() {
    }

    // @AllArgsConstructor
    public Enrollment(Integer enrollmentId, Integer scheduleId, Integer userId, Date enrollmentDate, String status) {
        this.enrollmentId = enrollmentId;
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    // @Getter
    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    // @Setter
    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    // @Getter
    public Integer getScheduleId() {
        return scheduleId;
    }

    // @Setter
    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    // @Getter
    public Integer getUserId() {
        return userId;
    }

    // @Setter
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // @Getter
    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    // @Setter
    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    // @Getter
    public String getStatus() {
        return status;
    }

    // @Setter
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(enrollmentId, that.enrollmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", scheduleId=" + scheduleId +
                ", userId=" + userId +
                ", enrollmentDate=" + enrollmentDate +
                ", status='" + status + '\'' +
                '}';
    }
}
