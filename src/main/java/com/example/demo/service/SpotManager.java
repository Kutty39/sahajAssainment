package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.exceptions.SpotOutOfBoundException;
import com.example.demo.exceptions.VehicleTypeMismatch;
import com.example.demo.response.ParkingTicket;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.example.demo.service.UtilService.getVGTYPE;

@Service
public class SpotManager {
    public void freeSpot(HashMap<VGTYPE, HashMap<Long, ParkingTicket>> spot, ParkingTicket ticket) throws VehicleTypeMismatch {
        VGTYPE vgtype = getVGTYPE(ticket.getVehicleType());
        spot.get(vgtype).put(ticket.getSpot(),null);
    }
    public void setSpot(HashMap<VGTYPE, HashMap<Long, ParkingTicket>> spot,VGTYPE vtype, int spotCount) throws SpotOutOfBoundException {
        if (spotCount >= 10000) {
            throw new SpotOutOfBoundException("Spot can not be increased above 9999.Please purchase new land and create parking lot");
        }
        spot.put(vtype, createSpot(spotCount));
    }
    private HashMap<Long, ParkingTicket> createSpot(int spotCount) {
        HashMap<Long, ParkingTicket> ticketHashMap = new HashMap<>();
        for (long i = 0; i < spotCount; i++) {
            ticketHashMap.put(i + 1, null);
        }
        return ticketHashMap;
    }

    long getAvailableSpot(HashMap<VGTYPE, HashMap<Long, ParkingTicket>> spot,VGTYPE vgtype) {
        HashMap<Long, ParkingTicket> parkingTicketHashMap = spot.getOrDefault(vgtype, new HashMap<>());
        if (parkingTicketHashMap.isEmpty()) {
            return 0;
        }
        return parkingTicketHashMap.entrySet().stream().filter(stringParkingTicketEntry -> stringParkingTicketEntry.getValue() == null).count();
    }
}
