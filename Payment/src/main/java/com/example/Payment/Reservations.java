package com.example.Payment;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservations {
    @Id
    @SequenceGenerator(
            name = "Reservations_sequence",
            sequenceName = "Reservations_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Reservations_sequence"
    )
    private Long id;
    private Integer cost;
    private String status;
    private String PNR;

    public Reservations(Integer cost, String status,String pnr) {
        this.cost = cost;
        this.status = status;
        this.PNR = pnr;
    }
}
