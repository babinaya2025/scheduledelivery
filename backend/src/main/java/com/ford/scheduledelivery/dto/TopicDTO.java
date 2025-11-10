// src/main/java/com/ford/scheduledelivery/dto/TopicDTO.java
package com.ford.scheduledelivery.dto;

import java.util.Objects;

public class TopicDTO {
    private Long topicId;
    private String name;
    private Boolean covered;
    
    public TopicDTO() {
    }
    
    public TopicDTO(Long topicId, String name, Boolean covered) {
        this.topicId = topicId;
        this.name = name;
        this.covered = covered;
    }
    
    public Long getTopicId() {
        return topicId;
    }
    
    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
        TopicDTO topicDTO = (TopicDTO) o;
        return Objects.equals(topicId, topicDTO.topicId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(topicId);
    }
    
    @Override
    public String toString() {
        return "TopicDTO{" +
                "topicId=" + topicId +
                ", name='" + name + '\'' +
                ", covered=" + covered +
                '}';
    }
}