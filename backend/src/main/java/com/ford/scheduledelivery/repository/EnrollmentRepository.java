package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

  // Their existing methods (KEEP THESE!)
  List<Enrollment> findByUserId(Integer userId);
  List<Enrollment> findByScheduleIdIn(List<Integer> scheduleIds);

  // NEW METHOD for attendance tracking
  List<Enrollment> findByScheduleId(Integer scheduleId);
}
