package com.example.PassengerInfo;

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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
public class PassengerController {
    @Autowired
    private RestTemplate restTemplate;
    private final PassengerRepository passengerRepository;
    List<Passenger> user = new ArrayList<>();
    private final ReservationRepository reservationRepository;
    @PostMapping(path = "/passengerinfo")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Passenger> SavePassengerInfo(@RequestParam("name") String[] name, @RequestParam("surname") String[] surname, @RequestParam("passport") String[] passport, @RequestParam("birthDate") String[] birthDateStr, @RequestParam("SSN") String[] ssn, @RequestParam("phone") String[] phone, @RequestParam("email") String[] email, @RequestParam("gender") String[] gender, Model model) throws JsonProcessingException {

        String apiUrl = "http://localhost:8070/api/v1/ports/selectPorts";
        String responseBody = "{\"oneway\":null,\"roundtrip\":\"ROUND TRIP\",\"departure\":\"Istanbul\",\"arrival\":\"New York\",\"depdate\":\"2023-09-15T21:00:00.000+00:00\",\"retdate\":\"2023-09-22T21:00:00.000+00:00\",\"passengercount\":1}";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            int passengerCount = jsonNode.get("passengercount").asInt();
            Integer i = 0;
            for(;i < name.length;i++){
                Passenger passenger = new Passenger();
                passenger.setName(name[i]);
                passenger.setSurname(surname[i]);
                passenger.setPassportno(passport[i]);
                passenger.setSSN(ssn[i]);
                passenger.setPhone(phone[i]);
                passenger.setEmail(email[i]);
                passenger.setGender(gender[i]);
                passenger.setBirthdate(birthDateStr[i]);
                passenger.setPNR(reservationRepository.findLatestReservation().getPNR());
                passengerRepository.save(passenger);
                user.add(passenger);

            }
        }
        else {
        }


        return user;
    }
    @GetMapping(path = "/passengerinfo")
    public List<Passenger> show(){

        return user;
    }

}
