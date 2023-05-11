package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.common.cons.VTYPE;
import com.example.demo.exceptions.SpotOutOfBoundException;
import com.example.demo.response.ParkingTicket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;

@Service
public class BikeParkingLotImpl implements ParkingLot {
    private HashMap<VGTYPE, HashMap<Long, ParkingTicket>> spot = new HashMap<>();
    private HashMap<String, ParkingEntry> parkingRegister = new HashMap<>();
    private final String name = "Bike";
    private final SpotManager spotManager;

    public BikeParkingLotImpl(SpotManager spotManager) throws SpotOutOfBoundException {
        this.spotManager = spotManager;
        spotManager.setSpot(spot,VGTYPE.TWOWHEELER,2);
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
    public long feeCalculation(VTYPE vtype, Duration duration) {
        switch (vtype) {
            case MOTORCYCLE:
            case SCOOTER:
                return duration.toHours() * 10;
        }
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }
}
