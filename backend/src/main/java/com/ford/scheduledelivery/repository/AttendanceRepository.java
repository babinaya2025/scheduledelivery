package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

  // Find all attendance records for a specific enrollment
  List<Attendance> findByEnrollmentId(Integer enrollmentId);

  // Find attendance by enrollment and date
  Attendance findByEnrollmentIdAndAttendanceDate(Integer enrollmentId, Date attendanceDate);

  // Find all attendance records between dates for a schedule
  @Query("SELECT a FROM Attendance a WHERE a.enrollmentId IN " +
    "(SELECT e.enrollmentId FROM Enrollment e WHERE e.scheduleId = :scheduleId) " +
    "AND a.attendanceDate BETWEEN :startDate AND :endDate " +
    "ORDER BY a.attendanceDate")
  List<Attendance> findByScheduleAndDateRange(
    @Param("scheduleId") Integer scheduleId,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate
  );

  // Delete all attendance for an enrollment
  void deleteByEnrollmentId(Integer enrollmentId);
}
