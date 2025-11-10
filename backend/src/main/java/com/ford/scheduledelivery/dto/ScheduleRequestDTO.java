// src/main/java/com/ford/scheduledelivery/dto/ScheduleRequest.java
package com.ford.scheduledelivery.dto;

import java.time.LocalDateTime;
import java.util.Objects; // For equals and hashCode

public class ScheduleRequestDTO {
    private String courseId;
    private String trainerId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;

    // @NoArgsConstructor
    public ScheduleRequestDTO() {
    }

    // @AllArgsConstructor
    public ScheduleRequestDTO(String courseId, String trainerId, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        this.courseId = courseId;
        this.trainerId = trainerId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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

    // @Getter
    public String getTrainerId() {
        return trainerId;
    }

    // @Setter
    public void setTrainerId(String trainerId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleRequestDTO that = (ScheduleRequestDTO) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(trainerId, that.trainerId) && Objects.equals(startDateTime, that.startDateTime) && Objects.equals(endDateTime, that.endDateTime) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, trainerId, startDateTime, endDateTime, location);
    }

    @Override
    public String toString() {
        return "ScheduleRequest{" +
                "courseId='" + courseId + '\'' +
                ", trainerId='" + trainerId + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", location='" + location + '\'' +
                '}';
    }
}
