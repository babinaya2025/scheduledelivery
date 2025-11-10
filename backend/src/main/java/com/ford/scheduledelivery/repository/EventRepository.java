package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> { // Event ID is String
    List<Event> findByTrainerId(Integer trainerId);
    List<Event> findByEventIdIn(List<String> eventIds);
}
