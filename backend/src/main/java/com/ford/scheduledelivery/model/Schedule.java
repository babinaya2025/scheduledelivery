// src/main/java/com/ford/scheduledelivery/model/Schedule.java
package com.ford.scheduledelivery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects; // For equals and hashCode

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    private String eventId;
    private Integer trainerId;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String location;
    private String scheduleStatus;
    private LocalDateTime lastModified;
    private String lockedByAdminId;

    // @NoArgsConstructor
    public Schedule() {
    }

    // @AllArgsConstructor
    public Schedule(Integer scheduleId, String eventId, Integer trainerId, LocalDateTime startDateTime, LocalDateTime endDateTime, String location, String scheduleStatus, LocalDateTime lastModified, String lockedByAdminId) {
        this.scheduleId = scheduleId;
        this.eventId = eventId;
        this.trainerId = trainerId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.scheduleStatus = scheduleStatus;
        this.lastModified = lastModified;
        this.lockedByAdminId = lockedByAdminId;
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
    public String getEventId() {
        return eventId;
    }

    // @Setter
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    // @Getter
    public Integer getTrainerId() {
        return trainerId;
    }

    // @Setter
    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    // @Getter
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    // @Setter
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    // @Getter
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    // @Setter
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    // @Getter
    public String getLocation() {
        return location;
    }

    // @Setter
    public void setLocation(String location) {
        this.location = location;
    }

    // @Getter
    public String getScheduleStatus() {
        return scheduleStatus;
    }

    // @Setter
    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    // @Getter
    public LocalDateTime getLastModified() {
        return lastModified;
    }

    // @Setter
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    // @Getter
    public String getLockedByAdminId() {
        return lockedByAdminId;
    }

    // @Setter
    public void setLockedByAdminId(String lockedByAdminId) {
        this.lockedByAdminId = lockedByAdminId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(scheduleId, schedule.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", eventId='" + eventId + '\'' +
                ", trainerId=" + trainerId +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", location='" + location + '\'' +
                ", scheduleStatus='" + scheduleStatus + '\'' +
                ", lastModified=" + lastModified +
                ", lockedByAdminId='" + lockedByAdminId + '\'' +
                '}';
    }
}
