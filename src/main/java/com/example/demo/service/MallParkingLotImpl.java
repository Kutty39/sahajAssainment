package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.common.cons.VTYPE;
import com.example.demo.exceptions.SpotOutOfBoundException;
import com.example.demo.response.ParkingTicket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;

@Service
public class MallParkingLotImpl implements ParkingLot {
    private HashMap<VGTYPE, HashMap<Long, ParkingTicket>> spot = new HashMap<>();
    private HashMap<String, ParkingEntry> parkingRegister = new HashMap<>();
    private final String name = "Mall";
    private final SpotManager spotManager;

    public MallParkingLotImpl(SpotManager spotManager) throws SpotOutOfBoundException {
        this.spotManager = spotManager;
        spotManager.setSpot(spot,VGTYPE.TWOWHEELER,100);
        spotManager.setSpot(spot,VGTYPE.FOURWHEELER,80);
    }

    @Override
    public HashMap<VGTYPE, HashMap<Long, ParkingTicket>> getSpot() {
        return this.spot;
    }

    @Override
    public HashMap<String, ParkingEntry> getParkingRegister() {
        return this.parkingRegister;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long feeCalculation(VTYPE vtype, Duration duration) {
        switch (vtype) {
            case MOTORCYCLE:
            case SCOOTER:
                return duration.toHours() * 10;
            case CAR:
            case SUV:
                return duration.toHours() * 20;
            case BUS:
            case TRUCK:
                return duration.toHours() * 50;
        }
        return 0;
    }
}
