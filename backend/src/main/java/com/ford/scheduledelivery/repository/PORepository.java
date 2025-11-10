package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.PO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PORepository extends JpaRepository<PO, Integer> { // Order ID is Integer
    List<PO> findByEventIdAndPoStatus(String eventId, String poStatus);
    List<PO> findByEventIdAndPoStatusAndUserIdIn(String eventId, String poStatus, List<Integer> userIds);
}
