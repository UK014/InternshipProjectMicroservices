package com.example.PnrSearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PnrSearch {
    @Autowired
    private RestTemplate restTemplate;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;
    public PnrSearch(PassengerRepository passengerRepository, FlightRepository flightRepository, ReservationRepository reservationRepository) {
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping(path = "/")
    public String showPnrSearch() {

        return "PnrSearch";
    }
    Pnr pnr = new Pnr();
    @PostMapping(path = "/api/v1/pnr/search")
    public String search(@RequestParam("search") String search){
        pnr.setPnr(search);
        return "redirect:/api/v1/pnr/search/result";
    }
    @GetMapping(path = "/api/v1/pnr/search/result")
    public String showInfo(Model model){
        pnr.getPnr();
        System.out.println(pnr.getPnr());
        List<Passenger> passengers = passengerRepository.findByPNR(pnr.getPnr());
        List<Flight> flights = flightRepository.findByPNR(pnr.getPnr());
        Integer j = 0;
        for(;j < passengers.size();j++) {

            model.addAttribute("dep", flights.get(j).getDeparture());
            model.addAttribute("arr", flights.get(j).getArrival());
            model.addAttribute("passengers",passengers);
            model.addAttribute("name",passengers.get(j).getName());
            model.addAttribute("sname", passengers.get(j).getSurname());
            model.addAttribute("gender",passengers.get(j).getGender());
            model.addAttribute("birthdate",passengers.get(j).getBirthdate());
            model.addAttribute("cost", reservationRepository.findLatestReservation().getCost());
            model.addAttribute("flighttime1",flightRepository.findFlightsByDepartureAndArrival(flights.get(j).getDeparture(),flights.get(j).getArrival()).getFlighttime());
            model.addAttribute("flighttime2",flightRepository.findFlightsByDepartureAndArrival(flights.get(j).getArrival(),flights.get(j).getDeparture()).getFlighttime());
            model.addAttribute("flightno1",flightRepository.findFlightsByDepartureAndArrival(flights.get(j).getArrival(),flights.get(j).getDeparture()).getFlightno());
            model.addAttribute("flightno2",flightRepository.findFlightsByDepartureAndArrival(flights.get(j).getDeparture(),flights.get(j).getArrival()).getFlightno());
            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            String depdateString = dateFormat.format(flightRepository.findFlightsByDepartureAndArrival(flights.get(j).getDeparture(),flights.get(j).getArrival()).getDepdate());
            SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
            String arrdateString = dateFormat1.format(flightRepository.findFlightsByDepartureAndArrival(flights.get(j).getArrival(),flights.get(j).getDeparture()).getArrdate());
            model.addAttribute("depdate",depdateString.substring(0,10));
            model.addAttribute("arrdate",arrdateString.substring(0,10));
            model.addAttribute("pnr",pnr.getPnr());
        }
        return  "ReservationInfo";
    }
}
