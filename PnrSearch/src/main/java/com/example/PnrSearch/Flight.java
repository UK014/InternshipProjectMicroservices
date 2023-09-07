package com.example.PnrSearch;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {
    @Id
    private long id;
    private String departure;
    private String arrival;
    private Integer business;
    private Integer economy;
    private Date depdate;
    private Date arrdate;
    private String PNR;
    private String flightno;
    private String flighttime;
}
