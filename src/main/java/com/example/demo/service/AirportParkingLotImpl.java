package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.common.cons.VTYPE;
import com.example.demo.exceptions.SpotOutOfBoundException;
import com.example.demo.response.ParkingTicket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;

@Service
public class AirportParkingLotImpl implements ParkingLot {
    private HashMap<VGTYPE, HashMap<Long, ParkingTicket>> spot = new HashMap<>();
    private HashMap<String, ParkingEntry> parkingRegister = new HashMap<>();
    private final String name = "Airport";
    private final SpotManager spotManager;

    public AirportParkingLotImpl(SpotManager spotManager) throws SpotOutOfBoundException {
        this.spotManager = spotManager;
        spotManager.setSpot(spot,VGTYPE.TWOWHEELER,200);
        spotManager.setSpot(spot,VGTYPE.FOURWHEELER,500);
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
        long hours = duration.toHours();
        long fee = 0;
        switch (vtype) {
            case MOTORCYCLE:
            case SCOOTER:
                if (hours < 1) {
                    return 0;
                }
                if (hours >= 8) {
                    fee = fee + (8 * 40);
                } else {
                    return fee + ((hours - 4) * 40);
                }
                if (hours >= 24) {
                    fee = fee + (16 * 60);
                } else {
                    return fee + ((hours - 4) * 60);
                }
                fee = fee + ((duration.toDays() - 1) * 80);
                return fee;
            case CAR:
            case SUV:
                if (hours >= 12) {
                    fee = fee + (12 * 60);
                } else {
                    return (hours * 60);
                }
                if (hours >= 24) {
                    fee = fee + (12 * 80);
                } else {
                    return fee + ((hours - 4) * 80);
                }
                fee = fee + ((duration.toDays() - 1) * 100);
                return fee;
        }
        return 0;
    }
}
