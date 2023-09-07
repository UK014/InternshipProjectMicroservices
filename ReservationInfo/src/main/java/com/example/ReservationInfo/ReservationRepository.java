package com.example.ReservationInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservations,Long> {
    @Query("SELECT r FROM Reservations r WHERE r.id = (SELECT MAX(rr.id) FROM Reservations rr)")
    Reservations findLatestReservation();
}
