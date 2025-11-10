package com.ford.scheduledelivery.dto;

import java.time.LocalDateTime;

public class ScheduleWithDetailsDTO {
  private Integer scheduleId;
  private String eventId;  // String, not Integer!
  private String eventName;
  private LocalDateTime startDate;  // LocalDateTime, not Date!
  private LocalDateTime endDate;
  private Integer trainerId;
  private String trainerName;
  private String location;

  // Constructors
  public ScheduleWithDetailsDTO() {}

  public ScheduleWithDetailsDTO(Integer scheduleId, String eventId, String eventName,
                                LocalDateTime startDate, LocalDateTime endDate, Integer trainerId,
                                String trainerName, String location) {
    this.scheduleId = scheduleId;
    this.eventId = eventId;
    this.eventName = eventName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.trainerId = trainerId;
    this.trainerName = trainerName;
    this.location = location;
  }

  // Getters and Setters
  public Integer getScheduleId() { return scheduleId; }
  public void setScheduleId(Integer scheduleId) { this.scheduleId = scheduleId; }

  public String getEventId() { return eventId; }
  public void setEventId(String eventId) { this.eventId = eventId; }

  public String getEventName() { return eventName; }
  public void setEventName(String eventName) { this.eventName = eventName; }

  public LocalDateTime getStartDate() { return startDate; }
  public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

  public LocalDateTime getEndDate() { return endDate; }
  public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

  public Integer getTrainerId() { return trainerId; }
  public void setTrainerId(Integer trainerId) { this.trainerId = trainerId; }

  public String getTrainerName() { return trainerName; }
  public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

  public String getLocation() { return location; }
  public void setLocation(String location) { this.location = location; }
}
