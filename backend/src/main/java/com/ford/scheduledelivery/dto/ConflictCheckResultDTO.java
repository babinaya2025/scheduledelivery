package com.ford.scheduledelivery.dto;

import java.util.List;
import java.util.Objects; // For equals and hashCode

//import lombok.AllArgsConstructor; // used for getters and setters generated automatically instead of boilerplate code
//import lombok.Data;
//import lombok.NoArgsConstructor;

//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class ConflictCheckResultDTO {


    private boolean hasConflicts;
    private List<String> conflicts;

    // @NoArgsConstructor
    public ConflictCheckResultDTO() {
    }

    // @AllArgsConstructor
    public ConflictCheckResultDTO(boolean hasConflicts, List<String> conflicts) {
        this.hasConflicts = hasConflicts;
        this.conflicts = conflicts;
    }

    // @Getter
    public boolean isHasConflicts() {
        return hasConflicts;
    }

    // @Setter
    public void setHasConflicts(boolean hasConflicts) {
        this.hasConflicts = hasConflicts;
    }

    // @Getter
    public List<String> getConflicts() {
        return conflicts;
    }

    // @Setter
    public void setConflicts(List<String> conflicts) {
        this.conflicts = conflicts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConflictCheckResultDTO that = (ConflictCheckResultDTO) o;
        return hasConflicts == that.hasConflicts && Objects.equals(conflicts, that.conflicts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasConflicts, conflicts);
    }

    @Override
    public String toString() {
        return "ConflictCheckResult{" +
                "hasConflicts=" + hasConflicts +
                ", conflicts=" + conflicts +
                '}';
    }
}


