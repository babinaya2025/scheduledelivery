// src/main/java/com/ford/scheduledelivery/repository/TopicRepository.java
package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByWeek_WeekId(Long weekId);
}