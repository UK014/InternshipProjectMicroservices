package com.example.ReservationInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.departure = :departure AND f.arrival = :arrival")
    Flight findFlightsByDepartureAndArrival(String departure, String arrival);

    List<Flight> findByPNR(String pnr);
}
