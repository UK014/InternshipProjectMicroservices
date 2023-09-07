package com.example.ReservationInfo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Passenger {
    @Id
    @SequenceGenerator(
            name = "Passenger_sequence",
            sequenceName = "Passenger_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Passenger_sequence"
    )
    private Long id;
    private String name;
    private String surname;
    private String gender;
    private String SSN;
    private String passportno;
    private String phone;
    private String email;
    private String PNR;
    private String birthdate;
}
