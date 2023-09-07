package com.example.SelectPorts;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/ports")
@RequiredArgsConstructor
public class SelectPortsController {

    SelectPorts selectPorts = new SelectPorts();
    @PostMapping(path = "/selectPorts")
    @ResponseStatus(HttpStatus.CREATED)
    public SelectPorts selectports(@RequestParam(value = "oneway",required = false) String oneway, @RequestParam(value = "roundtrip",required = false) String roundtrip, @RequestParam("Departure") String departure, @RequestParam("Arrival") String arrival, @RequestParam("depdate") String depdate, @RequestParam(value = "retdate",required = false) String retdate, @RequestParam("adultcount") Integer adultcount, @RequestParam("childcount") Integer childcount, @RequestParam("infantcount") Integer infantcount, Model model){

        String departuredate = depdate;
        String arrivaldate = retdate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        selectPorts.setArrival(arrival);
        selectPorts.setDeparture(departure);
        selectPorts.setOneway(oneway);
        selectPorts.setRoundtrip(roundtrip);
        selectPorts.setPassengercount(adultcount+childcount+infantcount);
        try {
            // Parse the string into a java.util.Date object
            Date date1 = sdf.parse(departuredate);
            Date date2 = sdf.parse(arrivaldate);
            selectPorts.setDepdate(date1);
            selectPorts.setRetdate(date2);

        } catch (ParseException e) {
            // Handle any parsing errors here
            System.err.println("Error parsing date: " + e.getMessage());
        }

        return selectPorts;
    }
    @GetMapping(path = "/selectPorts")
    public SelectPorts showports(){

        return selectPorts;
    }
}
