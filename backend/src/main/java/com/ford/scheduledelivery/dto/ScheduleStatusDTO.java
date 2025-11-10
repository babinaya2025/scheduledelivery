// src/main/java/com/ford/scheduledelivery/dto/ScheduleStatusDTO.java
package com.ford.scheduledelivery.dto;

import java.time.LocalDateTime;
import java.util.Objects; // For equals and hashCode

public class ScheduleStatusDTO {
    private String courseId;
    private boolean locked; // Renamed from isLocked to locked
    private String lockedBy;
    private LocalDateTime lockedAt;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String trainerId;
    private String trainerName;
    private String location;

    // @NoArgsConstructor
    public ScheduleStatusDTO() {
    }

    // @AllArgsConstructor
    public ScheduleStatusDTO(String courseId, boolean locked, String lockedBy, LocalDateTime lockedAt, LocalDateTime startDateTime, LocalDateTime endDateTime, String trainerId, String trainerName, String location) {
        this.courseId = courseId;
        this.locked = locked;
        this.lockedBy = lockedBy;
        this.lockedAt = lockedAt;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.location = location;
    }

    // @Getter
    public String getCourseId() {
        return courseId;
    }

    // @Setter
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    // @Getter (isLocked() for boolean)
    public boolean isLocked() {
        return locked;
    }

    // @Setter
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    // @Getter
    public String getLockedBy() {
        return lockedBy;
    }

    // @Setter
    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    // @Getter
    public LocalDateTime getLockedAt() {
        return lockedAt;
    }

    // @Setter
    public void setLockedAt(LocalDateTime lockedAt) {
        this.lockedAt = lockedAt;
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
    public String getTrainerId() {
        return trainerId;
    }

    // @Setter
    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    // @Getter
    public String getTrainerName() {
        return trainerName;
    }

    // @Setter
    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    // @Getter
    public String getLocation() {
        return location;
    }

    // @Setter
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleStatusDTO that = (ScheduleStatusDTO) o;
        return locked == that.locked && Objects.equals(courseId, that.courseId) && Objects.equals(lockedBy, that.lockedBy) && Objects.equals(lockedAt, that.lockedAt) && Objects.equals(startDateTime, that.startDateTime) && Objects.equals(endDateTime, that.endDateTime) && Objects.equals(trainerId, that.trainerId) && Objects.equals(trainerName, that.trainerName) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, locked, lockedBy, lockedAt, startDateTime, endDateTime, trainerId, trainerName, location);
    }

    @Override
    public String toString() {
        return "ScheduleStatusDTO{" +
                "courseId='" + courseId + '\'' +
                ", locked=" + locked +
                ", lockedBy='" + lockedBy + '\'' +
                ", lockedAt=" + lockedAt +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", trainerId='" + trainerId + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
