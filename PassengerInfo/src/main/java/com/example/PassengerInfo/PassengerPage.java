package com.example.PassengerInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class PassengerPage {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping(path = "/")
    public String showPassengerInfo(Model model) throws JsonProcessingException {
        String apiUrl = "http://localhost:8070/api/v1/ports/selectPorts";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody()); // Parse the actual response body
            int passengerCount = jsonNode.get("passengercount").asInt();
            model.addAttribute("totalPassenger", passengerCount);
        } else {
            // Handle the case where the API response is not successful
        }

        return "PassengerInfo";
    }

}
