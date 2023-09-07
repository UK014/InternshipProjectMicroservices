package com.example.ReservationInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationInfo {
    @Autowired
    private RestTemplate restTemplate;
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;

    public ReservationInfo(ReservationRepository reservationRepository, FlightRepository flightRepository) {
        this.reservationRepository = reservationRepository;
        this.flightRepository = flightRepository;
    }
    @GetMapping
    public String reservationsPage(Model model) throws JsonProcessingException {
        String apiUrl = "http://localhost:8050/api/v1/passenger/passengerinfo";
        String apiUrl2 = "http://localhost:8070/api/v1/ports/selectPorts";
        ResponseEntity<String> response2 = restTemplate.getForEntity(apiUrl2, String.class);
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            ObjectMapper objectMapper2 = new ObjectMapper();
            JsonNode jsonNode2 = objectMapper2.readTree(response2.getBody());

            String departure = jsonNode2.get("departure").asText();
            String arrival = jsonNode2.get("arrival").asText();
            String depdate = jsonNode2.get("depdate").asText();
            String retdate = jsonNode2.get("retdate").asText();
            depdate = depdate.substring(0,10);
            retdate = retdate.substring(0,10);
            int passengerCount = jsonNode2.get("passengercount").asInt();

            List<Passenger> passengers = objectMapper.readValue(
                    jsonNode.toString(),
                    new TypeReference<List<Passenger>>() {}
            );
            List<Passenger> passengerList = new ArrayList<>();
            passengerList.addAll(passengers);
            Integer j = 0;
            for(;j < passengerList.size();j++) {
                if (j == 0){
                    model.addAttribute("dep", departure);
                    model.addAttribute("arr", arrival);
                }
                else{
                    model.addAttribute("dep", arrival);
                    model.addAttribute("arr", departure);
                }
                model.addAttribute("passengers",passengerList);
                model.addAttribute("name",passengerList.get(j).getName());
                model.addAttribute("sname", passengerList.get(j).getSurname());
                model.addAttribute("gender",passengerList.get(j).getGender());
                model.addAttribute("birthdate",passengerList.get(j).getBirthdate());
                model.addAttribute("cost", reservationRepository.findLatestReservation().getCost());
                model.addAttribute("flighttime1",flightRepository.findFlightsByDepartureAndArrival(departure,arrival).getFlighttime());
                model.addAttribute("flighttime2",flightRepository.findFlightsByDepartureAndArrival(arrival,departure).getFlighttime());
                model.addAttribute("flightno1",flightRepository.findFlightsByDepartureAndArrival(departure,arrival).getFlightno());
                model.addAttribute("flightno2",flightRepository.findFlightsByDepartureAndArrival(arrival,departure).getFlightno());
                model.addAttribute("depdate", depdate);
                model.addAttribute("arrdate",retdate);
                model.addAttribute("pnr",reservationRepository.findLatestReservation().getPNR());
            }
        }
        return "ReservationInfo";

    }



}
