package com.example.Payment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final ReservationRepository reservationRepository;
    @PostMapping(path = "/payment")
    public Reservations pay(){
        Reservations reservations = reservationRepository.findLatestReservation();
        reservations.setStatus("ticketed");
        reservationRepository.save(reservations);



        return reservations;

    }
}
