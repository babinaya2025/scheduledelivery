package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> { // Trainer ID is Integer
    Optional<Trainer> findByTrainerId(Integer trainerId);
}
