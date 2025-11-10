// src/main/java/com/ford/scheduledelivery/repository/ScheduleRepository.java
package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    // ✅ EXISTING METHODS - Keep these
    Optional<Schedule> findByEventId(String eventId);
    boolean existsByTrainerIdAndStartDateTimeBeforeAndEndDateTimeAfter(Integer trainerId, LocalDateTime endDateTime, LocalDateTime startDateTime);
    boolean existsByTrainerIdAndEventIdNotAndStartDateTimeBeforeAndEndDateTimeAfter(Integer trainerId, String eventId, LocalDateTime endDateTime, LocalDateTime startDateTime);
    
    // ✅ NEW METHOD - Add this for Support Delivery
    List<Schedule> findAllByEventId(String eventId);
}