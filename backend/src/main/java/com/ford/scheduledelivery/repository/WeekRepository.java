// src/main/java/com/ford/scheduledelivery/repository/WeekRepository.java
package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
    List<Week> findByEventIdOrderByWeekNumberAsc(String eventId);
    Optional<Week> findByEventIdAndWeekNumber(String eventId, Integer weekNumber);
}