package com.mvaikm.lennu_veebirakendus.flight;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight")
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlightById(id);
    }

    @PostMapping("/register")
    public String createFlight(@RequestBody FlightDTO flightDTO) {
        try {
            flightService.addFlight(flightDTO);
            return "Flight created successfully";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }
}
