package com.example.ReservationInfo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationInfoController {

    private final ReservationRepository reservationRepository;
    @PostMapping(path = "/info")
    public Reservations cancel(){
        Reservations reservations = reservationRepository.findLatestReservation();
        reservations.setStatus("canceled");
        reservationRepository.save(reservations);

        return reservations;
    }
}
