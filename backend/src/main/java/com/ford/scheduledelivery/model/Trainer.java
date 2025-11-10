// src/main/java/com/ford/scheduledelivery/model/Trainer.java
package com.ford.scheduledelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects; // For equals and hashCode

@Entity
@Table(name = "trainer")
public class Trainer {

    @Id
    private Integer trainerId;
    private String trainerFirstName;
    private String trainerLastName;
    private String trainerEmailId;
    private String expertiseArea;

    // @NoArgsConstructor
    public Trainer() {
    }

    // @AllArgsConstructor
    public Trainer(Integer trainerId, String trainerFirstName, String trainerLastName, String trainerEmailId, String expertiseArea) {
        this.trainerId = trainerId;
        this.trainerFirstName = trainerFirstName;
        this.trainerLastName = trainerLastName;
        this.trainerEmailId = trainerEmailId;
        this.expertiseArea = expertiseArea;
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
    public String getTrainerFirstName() {
        return trainerFirstName;
    }

    // @Setter
    public void setTrainerFirstName(String trainerFirstName) {
        this.trainerFirstName = trainerFirstName;
    }

    // @Getter
    public String getTrainerLastName() {
        return trainerLastName;
    }

    // @Setter
    public void setTrainerLastName(String trainerLastName) {
        this.trainerLastName = trainerLastName;
    }

    // @Getter
    public String getTrainerEmailId() {
        return trainerEmailId;
    }

    // @Setter
    public void setTrainerEmailId(String trainerEmailId) {
        this.trainerEmailId = trainerEmailId;
    }

    // @Getter
    public String getExpertiseArea() {
        return expertiseArea;
    }

    // @Setter
    public void setExpertiseArea(String expertiseArea) {
        this.expertiseArea = expertiseArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(trainerId, trainer.trainerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainerId);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", trainerFirstName='" + trainerFirstName + '\'' +
                ", trainerLastName='" + trainerLastName + '\'' +
                ", trainerEmailId='" + trainerEmailId + '\'' +
                ", expertiseArea='" + expertiseArea + '\'' +
                '}';
    }
}
