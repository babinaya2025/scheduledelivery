// src/main/java/com/ford/scheduledelivery/dto/WeekDTO.java
package com.ford.scheduledelivery.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeekDTO {
    private Long weekId;
    private Integer weekNumber;
    private String driveLink;
    private String githubLink;
    private String assignmentLink;
    private List<TopicDTO> topics = new ArrayList<>();
    
    public WeekDTO() {
    }
    
    public WeekDTO(Long weekId, Integer weekNumber, String driveLink, String githubLink, String assignmentLink) {
        this.weekId = weekId;
        this.weekNumber = weekNumber;
        this.driveLink = driveLink;
        this.githubLink = githubLink;
        this.assignmentLink = assignmentLink;
    }
    
    public Long getWeekId() {
        return weekId;
    }
    
    public void setWeekId(Long weekId) {
        this.weekId = weekId;
    }
    
    public Integer getWeekNumber() {
        return weekNumber;
    }
    
    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }
    
    public String getDriveLink() {
        return driveLink;
    }
    
    public void setDriveLink(String driveLink) {
        this.driveLink = driveLink;
    }
    
    public String getGithubLink() {
        return githubLink;
    }
    
    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }
    
    public String getAssignmentLink() {
        return assignmentLink;
    }
    
    public void setAssignmentLink(String assignmentLink) {
        this.assignmentLink = assignmentLink;
    }
    
    public List<TopicDTO> getTopics() {
        return topics;
    }
    
    public void setTopics(List<TopicDTO> topics) {
        this.topics = topics;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeekDTO weekDTO = (WeekDTO) o;
        return Objects.equals(weekId, weekDTO.weekId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(weekId);
    }
    
    @Override
    public String toString() {
        return "WeekDTO{" +
                "weekId=" + weekId +
                ", weekNumber=" + weekNumber +
                ", topicsCount=" + topics.size() +
                '}';
    }
}