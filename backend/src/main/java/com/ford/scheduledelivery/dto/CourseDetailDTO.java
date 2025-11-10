// src/main/java/com/ford/scheduledelivery/dto/CourseDetailDTO.java
package com.ford.scheduledelivery.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseDetailDTO {
    private String id;
    private String name;
    private String status; // "completed", "ongoing", "upcoming"
    private String trainerName;
    private String trainerEmail;
    private Integer trainerId;
    private List<TraineeDTO> trainees = new ArrayList<>();
    private List<WeekDTO> weeks = new ArrayList<>();
    
    public CourseDetailDTO() {
    }
    
    public CourseDetailDTO(String id, String name, String status, String trainerName, String trainerEmail, Integer trainerId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.trainerName = trainerName;
        this.trainerEmail = trainerEmail;
        this.trainerId = trainerId;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getTrainerName() {
        return trainerName;
    }
    
    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
    
    public String getTrainerEmail() {
        return trainerEmail;
    }
    
    public void setTrainerEmail(String trainerEmail) {
        this.trainerEmail = trainerEmail;
    }
    
    public Integer getTrainerId() {
        return trainerId;
    }
    
    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }
    
    public List<TraineeDTO> getTrainees() {
        return trainees;
    }
    
    public void setTrainees(List<TraineeDTO> trainees) {
        this.trainees = trainees;
    }
    
    public List<WeekDTO> getWeeks() {
        return weeks;
    }
    
    public void setWeeks(List<WeekDTO> weeks) {
        this.weeks = weeks;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDetailDTO that = (CourseDetailDTO) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "CourseDetailDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", trainerId=" + trainerId +
                ", traineesCount=" + trainees.size() +
                ", weeksCount=" + weeks.size() +
                '}';
    }
}