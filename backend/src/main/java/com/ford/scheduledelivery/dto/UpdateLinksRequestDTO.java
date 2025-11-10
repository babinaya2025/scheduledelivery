// src/main/java/com/ford/scheduledelivery/dto/UpdateLinksRequestDTO.java
package com.ford.scheduledelivery.dto;

import java.util.Objects;

public class UpdateLinksRequestDTO {
    private String driveLink;
    private String githubLink;
    private String assignmentLink;
    
    public UpdateLinksRequestDTO() {
    }
    
    public UpdateLinksRequestDTO(String driveLink, String githubLink, String assignmentLink) {
        this.driveLink = driveLink;
        this.githubLink = githubLink;
        this.assignmentLink = assignmentLink;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateLinksRequestDTO that = (UpdateLinksRequestDTO) o;
        return Objects.equals(driveLink, that.driveLink) &&
                Objects.equals(githubLink, that.githubLink) &&
                Objects.equals(assignmentLink, that.assignmentLink);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(driveLink, githubLink, assignmentLink);
    }
    
    @Override
    public String toString() {
        return "UpdateLinksRequestDTO{" +
                "driveLink='" + driveLink + '\'' +
                ", githubLink='" + githubLink + '\'' +
                ", assignmentLink='" + assignmentLink + '\'' +
                '}';
    }
}