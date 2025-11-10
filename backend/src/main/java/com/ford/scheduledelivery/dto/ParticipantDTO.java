// src/main/java/com/ford/scheduledelivery/dto/ParticipantDTO.java
package com.ford.scheduledelivery.dto;

import java.util.Objects; // For equals and hashCode

public class ParticipantDTO {
    private String id;
    private String name;
    private String email;

    // @NoArgsConstructor
    public ParticipantDTO() {
    }

    // @AllArgsConstructor
    public ParticipantDTO(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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
    public String getEmail() {
        return email;
    }

    // @Setter
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantDTO that = (ParticipantDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return "ParticipantDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
