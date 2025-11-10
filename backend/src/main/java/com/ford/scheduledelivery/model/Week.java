// src/main/java/com/ford/scheduledelivery/model/Week.java
package com.ford.scheduledelivery.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "week")
public class Week {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weekId;
    
    @Column(nullable = false)
    private String eventId; // Foreign key to Event
    
    @Column(nullable = false)
    private Integer weekNumber;
    
    private String driveLink;
    private String githubLink;
    private String assignmentLink;
    
    @OneToMany(mappedBy = "week", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();
    
    // Constructors
    public Week() {
    }
    
    public Week(String eventId, Integer weekNumber, String driveLink, String githubLink, String assignmentLink) {
        this.eventId = eventId;
        this.weekNumber = weekNumber;
        this.driveLink = driveLink;
        this.githubLink = githubLink;
        this.assignmentLink = assignmentLink;
    }
    
    // Getters and Setters
    public Long getWeekId() {
        return weekId;
    }
    
    public void setWeekId(Long weekId) {
        this.weekId = weekId;
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
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
    
    public List<Topic> getTopics() {
        return topics;
    }
    
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Week week = (Week) o;
        return Objects.equals(weekId, week.weekId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(weekId);
    }
    
    @Override
    public String toString() {
        return "Week{" +
                "weekId=" + weekId +
                ", eventId='" + eventId + '\'' +
                ", weekNumber=" + weekNumber +
                ", driveLink='" + driveLink + '\'' +
                ", githubLink='" + githubLink + '\'' +
                ", assignmentLink='" + assignmentLink + '\'' +
                '}';
    }
}