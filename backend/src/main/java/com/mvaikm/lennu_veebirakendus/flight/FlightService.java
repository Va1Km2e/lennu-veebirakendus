package com.mvaikm.lennu_veebirakendus.flight;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public void addFlight(FlightDTO flightDTO) {
        if (flightDTO == null) {
            throw new RuntimeException("FlightDTO is null");
        }

        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setFlightNumber(flightDTO.getFlightNumber());
        flightEntity.setDepartureCity(flightDTO.getDepartureCity());
        flightEntity.setDestinationCity(flightDTO.getDestinationCity());
        flightEntity.setDepartureTime(flightDTO.getDepartureTime());
        flightEntity.setArrivalTime(flightDTO.getArrivalTime());

        Duration duration = Duration.between(flightDTO.getDepartureTime(), flightDTO.getArrivalTime());
        flightEntity.setDurationInMin(BigDecimal.valueOf(duration.toMinutes()));

        flightEntity.setBasePrice(flightDTO.getBasePrice());
        flightEntity.setAvailableSeats(flightDTO.getAvailableSeats());

        flightRepository.save(flightEntity);
    }

    public void deleteFlightById(Long id) {
        flightRepository.deleteById(id);
    }
}
