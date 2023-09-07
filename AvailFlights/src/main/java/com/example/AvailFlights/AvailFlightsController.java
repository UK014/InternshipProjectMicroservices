package com.example.AvailFlights;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/availflights")
@RequiredArgsConstructor
public class AvailFlightsController {
    @Autowired
    private RestTemplate restTemplate; // Inject RestTemplate
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    @PostMapping(path = "/availflights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight chooseflight(@RequestParam(value = "business",required = false) String business, @RequestParam(value = "economy",required = false) String economy,@RequestParam(value = "business2",required = false) String business2, @RequestParam(value = "economy2",required = false) String economy2, Model model) throws JsonProcessingException {
        String apiUrl = "http://localhost:8070/api/v1/ports/selectPorts"; // Update with the actual URL

        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        Flight flight = new Flight();
        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String oneway = jsonNode.get("oneway").asText();
            String roundtrip = jsonNode.get("roundtrip").asText();
            String departure = jsonNode.get("departure").asText();
            String arrival = jsonNode.get("arrival").asText();
            String depdate = jsonNode.get("depdate").asText();
            String retdate = jsonNode.get("retdate").asText();
            JsonNode passengerCountNode = jsonNode.get("passengercount");
            int passengerCount = (passengerCountNode != null) ? passengerCountNode.asInt() : 0;
            depdate = depdate.substring(0,10);
            retdate = retdate.substring(0,10);
            Integer cost = 0;
            Flight flight1 = flightRepository.findFlightsByDepartureAndArrival(departure,arrival);
            Flight flight2 = flightRepository.findFlightsByDepartureAndArrival(arrival,departure);
            Reservations reservations = new Reservations();
            if (business!= null && business.equals("on") && business2!= null && business2.equals("on")){
                cost = (1500+1500)*passengerCount;
                reservations.setCost(cost);
                reservations.setPNR(generateRandomText());
                flight1.setPNR(reservations.getPNR());
                flight2.setPNR(reservations.getPNR());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = sdf.parse(depdate);
                    Date date2 = sdf.parse(retdate);
                    flight1.setDepdate(date2);
                    flight1.setArrdate(date2);
                    flight2.setDepdate(date1);
                    flight2.setArrdate(date1);
                    flightRepository.save(flight1);
                    flightRepository.save(flight2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            else if(business!= null && business.equals("on") && economy2!= null && economy2.equals("on")){
                cost = (1500+300)*passengerCount;
                reservations.setCost(cost);
                reservations.setPNR(generateRandomText());
                flight1.setPNR(reservations.getPNR());
                flight2.setPNR(reservations.getPNR());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = sdf.parse(depdate);
                    Date date2 = sdf.parse(retdate);
                    flight1.setDepdate(date2);
                    flight1.setArrdate(date2);
                    flight2.setDepdate(date1);
                    flight2.setArrdate(date1);
                    flightRepository.save(flight1);
                    flightRepository.save(flight2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else if(business2!= null && business2.equals("on") && economy!= null && economy.equals("on")){
                cost = (1500+300)*passengerCount;
                reservations.setCost(cost);
                reservations.setPNR(generateRandomText());
                flight1.setPNR(reservations.getPNR());
                flight2.setPNR(reservations.getPNR());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = sdf.parse(depdate);
                    Date date2 = sdf.parse(retdate);
                    flight1.setDepdate(date2);
                    flight1.setArrdate(date2);
                    flight2.setDepdate(date1);
                    flight2.setArrdate(date1);
                    flightRepository.save(flight1);
                    flightRepository.save(flight2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else {
                cost = (300+300)*passengerCount;
                reservations.setCost(cost);
                reservations.setPNR(generateRandomText());
                flight1.setPNR(reservations.getPNR());
                flight2.setPNR(reservations.getPNR());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = sdf.parse(depdate);
                    Date date2 = sdf.parse(retdate);
                    flight1.setDepdate(date2);
                    flight1.setArrdate(date2);
                    flight2.setDepdate(date1);
                    flight2.setArrdate(date1);
                    flightRepository.save(flight1);
                    flightRepository.save(flight2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            reservationRepository.save(reservations);
        } else {
            System.err.println("Error making GET request: " + response.getStatusCode());
        }

        return flight;
    }
    public static String generateRandomText() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomText = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = new Random().nextInt(characters.length());
            randomText.append(characters.charAt(index));
        }

        return randomText.toString();
    }

}
