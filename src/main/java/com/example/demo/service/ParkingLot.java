package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.common.cons.VTYPE;
import com.example.demo.response.ParkingTicket;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;

@Component
public interface ParkingLot {
    long feeCalculation(VTYPE vtype, Duration duration);
    String getName();
    HashMap<VGTYPE, HashMap<Long, ParkingTicket>> getSpot();
    HashMap<String, ParkingEntry> getParkingRegister();
}
