// src/main/java/com/ford/scheduledelivery/model/Event.java
package com.ford.scheduledelivery.model;

import jakarta.persistence.*;
import java.util.Objects; // For equals and hashCode

@Entity
@Table(name = "event")
public class Event {
    @Id
    private String eventId; // Using String ID to match frontend Course.id
    private String eventName;
    private Integer trainerId; // Link to Trainer entity by ID

    // NEW FIELDS for Event
    private String description;
    private String requestId;
    private Integer participantsCount;
    private Integer duration;
    private String eventType;
    private String fundingSource;


    // @NoArgsConstructor
    public Event() {
    }

    // @AllArgsConstructor
    public Event(String eventId, String eventName, Integer trainerId, String description, String requestId, Integer participantsCount, Integer duration, String eventType, String fundingSource) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.trainerId = trainerId;
        this.description = description;
        this.requestId = requestId;
        this.participantsCount = participantsCount;
        this.duration = duration;
        this.eventType = eventType;
        this.fundingSource = fundingSource;
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
    public String getEventName() {
        return eventName;
    }

    // @Setter
    public void setEventName(String eventName) {
        this.eventName = eventName;
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
    public String getDescription() {
        return description;
    }

    // @Setter
    public void setDescription(String description) {
        this.description = description;
    }

    // @Getter
    public String getRequestId() {
        return requestId;
    }

    // @Setter
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    // @Getter
    public Integer getParticipantsCount() {
        return participantsCount;
    }

    // @Setter
    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }

    // @Getter
    public Integer getDuration() {
        return duration;
    }

    // @Setter
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    // @Getter
    public String getEventType() {
        return eventType;
    }

    // @Setter
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    // @Getter
    public String getFundingSource() {
        return fundingSource;
    }

    // @Setter
    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", trainerId=" + trainerId +
                ", description='" + description + '\'' +
                ", requestId='" + requestId + '\'' +
                ", participantsCount=" + participantsCount +
                ", duration=" + duration +
                ", eventType='" + eventType + '\'' +
                ", fundingSource='" + fundingSource + '\'' +
                '}';
    }
}
