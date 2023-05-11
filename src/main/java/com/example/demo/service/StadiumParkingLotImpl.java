package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.common.cons.VTYPE;
import com.example.demo.exceptions.SpotOutOfBoundException;
import com.example.demo.response.ParkingTicket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;

@Service
public class StadiumParkingLotImpl implements ParkingLot {
    private HashMap<VGTYPE, HashMap<Long, ParkingTicket>> spot = new HashMap<>();
    private HashMap<String, ParkingEntry> parkingRegister = new HashMap<>();
    private final String name="Stadium";
    private final SpotManager spotManager;

    public StadiumParkingLotImpl(SpotManager spotManager) throws SpotOutOfBoundException {
        this.spotManager = spotManager;
        spotManager.setSpot(spot,VGTYPE.TWOWHEELER,1000);
        spotManager.setSpot(spot,VGTYPE.FOURWHEELER,1500);
    }

    @Override
    public String getName() {
        return name;
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
        long hours = duration.toHours();
        long fee = 0;
        switch (vtype) {
            case MOTORCYCLE:
            case SCOOTER:
                if (hours >= 4) {
                    fee = 4 * 30;
                } else {
                    return hours * 30;
                }
                if (hours >= 12) {
                    fee = fee + (8 * 60);
                } else {
                    return fee + ((hours - 4) * 60);
                }
                fee = fee + ((hours - 12) * 100);
                return fee;
            case CAR:
            case SUV:
                if (hours >= 4) {
                    fee = 4 * 60;
                } else {
                    return hours * 60;
                }
                if (hours >= 12) {
                    fee = fee + (8 * 120);
                } else {
                    return fee + ((hours - 4) * 120);
                }
                fee = fee + ((hours - 12) * 200);
                return fee;
        }
        return 0;
    }
}
