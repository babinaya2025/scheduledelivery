// src/main/java/com/ford/scheduledelivery/model/Topic.java
package com.ford.scheduledelivery.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "topic")
public class Topic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id", nullable = false)
    private Week week;
    
    @Column(nullable = false)
    private String topicName;
    
    @Column(nullable = false)
    private Boolean covered = false;
    
    // Constructors
    public Topic() {
    }
    
    public Topic(Week week, String topicName, Boolean covered) {
        this.week = week;
        this.topicName = topicName;
        this.covered = covered;
    }
    
    // Getters and Setters
    public Long getTopicId() {
        return topicId;
    }
    
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
    
    public Week getWeek() {
        return week;
    }
    
    public void setWeek(Week week) {
        this.week = week;
    }
    
    public String getTopicName() {
        return topicName;
    }
    
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    
    public Boolean getCovered() {
        return covered;
    }
    
    public void setCovered(Boolean covered) {
        this.covered = covered;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(topicId, topic.topicId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(topicId);
    }
    
    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicName='" + topicName + '\'' +
                ", covered=" + covered +
                '}';
    }
}