// src/main/java/com/ford/scheduledelivery/dto/CourseDTO.java
package com.ford.scheduledelivery.dto;

import java.util.Objects; // For equals and hashCode

public class CourseDTO {
    private String id;
    private String name;
    private String trainerId;

    // @NoArgsConstructor
    public CourseDTO() {
    }

    // @AllArgsConstructor
    public CourseDTO(String id, String name, String trainerId) {
        this.id = id;
        this.name = name;
        this.trainerId = trainerId;
    }

    // @Getter
    public String getId() {
        return id;
    }

    // @Setter
    public void setId(String id) {
        this.id = id;
    }

    // @Getter
    public String getName() {
        return name;
    }

    // @Setter
    public void setName(String name) {
        this.name = name;
    }

    // @Getter
    public String getTrainerId() {
        return trainerId;
    }

    // @Setter
    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return Objects.equals(id, courseDTO.id) && Objects.equals(name, courseDTO.name) && Objects.equals(trainerId, courseDTO.trainerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, trainerId);
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", trainerId='" + trainerId + '\'' +
                '}';
    }
}
