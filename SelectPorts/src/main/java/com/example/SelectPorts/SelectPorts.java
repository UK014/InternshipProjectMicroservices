package com.example.SelectPorts;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectPorts {
    private String oneway;
    private String roundtrip;
    private String departure;
    private String arrival;
    private Date depdate;
    private Date retdate;
    private Integer passengercount;
}
