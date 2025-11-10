package com.ford.scheduledelivery.repository;

import com.ford.scheduledelivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { // User ID is Integer
    // This query will now work because User has a 'poOrders' property (the List<PO>).
    // Spring Data JPA will navigate from User -> poOrders -> eventId and poStatus.
    List<User> findByPoOrders_EventIdAndPoOrders_PoStatus(String eventId, String poStatus);
}
