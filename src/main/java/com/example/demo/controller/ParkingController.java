package com.example.demo.controller;

import com.example.demo.exceptions.*;
import com.example.demo.request.ParkingRequest;
import com.example.demo.response.ParkingTicket;
import com.example.demo.service.ParkingReceipt;
import com.example.demo.service.ParkingService;
import com.example.demo.service.VehicleNotParckedHere;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController {
    private final ParkingService service;

    public ParkingController(ParkingService service) {
        this.service = service;
    }

    @PostMapping("/parking")
    public ParkingTicket parking(@RequestBody ParkingRequest request) throws NoLocationAvailableException, SpotNotAvailable, VehicleTypeMismatch, TicketAlreadyCreated {
        return service.parking( request);
    }
    @PutMapping("/parking")
    public ParkingReceipt unParking(@RequestBody ParkingTicket ticket) throws NoLocationAvailableException, VehicleNotParckedHere, VehicleTypeMismatch, VehicleAlreadyTaken {
        return service.unParking(ticket);
    }
}
