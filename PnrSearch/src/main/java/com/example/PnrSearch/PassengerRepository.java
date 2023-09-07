package com.example.PnrSearch;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
    List<Passenger> findByPNR(String pnr);
}
