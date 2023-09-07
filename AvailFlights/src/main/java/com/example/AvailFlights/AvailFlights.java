package com.example.AvailFlights;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class AvailFlights {

    @Autowired
    private RestTemplate restTemplate; // Inject RestTemplate
    @Autowired
    private final FlightRepository flightRepository;

    public AvailFlights(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping(path = "/")
    public String availflights(Model model) throws JsonProcessingException {
        // Define the URL of the microservice running on port 8060
        String apiUrl = "http://localhost:8070/api/v1/ports/selectPorts"; // Update with the actual URL


        // Make the HTTP GET request to the other microservice
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // Check the response if needed
        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON string into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(response.getBody());


            String oneway = jsonNode.get("oneway").asText();
            String roundtrip = jsonNode.get("roundtrip").asText();
            String departure = jsonNode.get("departure").asText();
            String arrival = jsonNode.get("arrival").asText();
            String depdate = jsonNode.get("depdate").asText();
            String retdate = jsonNode.get("retdate").asText();
            depdate = depdate.substring(0,10);
            retdate = retdate.substring(0,10);

            int passengercount = jsonNode.get("passengercount").asInt();
            model.addAttribute("flightno1",flightRepository.findFlightsByDepartureAndArrival(departure,arrival).getFlightno());
            model.addAttribute("bcost", flightRepository.findFlightsByDepartureAndArrival(departure,arrival).getBusiness()*passengercount);
            model.addAttribute("ecost", flightRepository.findFlightsByDepartureAndArrival(departure,arrival).getEconomy()*passengercount);
            model.addAttribute("dep",flightRepository.findFlightsByDepartureAndArrival(departure,arrival).getDeparture());
            model.addAttribute("arr",flightRepository.findFlightsByDepartureAndArrival(departure,arrival).getArrival());
            model.addAttribute("date",depdate);
            model.addAttribute("retdate",retdate);
            model.addAttribute("flightno2",flightRepository.findFlightsByDepartureAndArrival(arrival,departure).getFlightno());
            model.addAttribute("flighttime1",flightRepository.findFlightsByDepartureAndArrival(departure,arrival).getFlighttime());
            model.addAttribute("flighttime2",flightRepository.findFlightsByDepartureAndArrival(arrival,departure).getFlighttime());


        } else {
            System.err.println("Error making GET request: " + response.getStatusCode());
        }

        return "AvailFlights";
    }
}
