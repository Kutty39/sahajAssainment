package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.common.cons.VTYPE;
import com.example.demo.exceptions.*;
import com.example.demo.request.ParkingRequest;
import com.example.demo.response.ParkingTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.service.UtilService.getVGTYPE;

@Service
public class ParkingService {
    private final SpotManager spotManager;
    private final ParkingLotManager parkingLotManager;

    @Autowired
    List<ParkingLot> lots;

    public ParkingService(SpotManager spotManager, ParkingLotManager parkingLotManager) {
        this.spotManager = spotManager;
        this.parkingLotManager = parkingLotManager;
    }


    public ParkingTicket parking(ParkingRequest parkingInfo) throws NoLocationAvailableException, SpotNotAvailable, VehicleTypeMismatch, TicketAlreadyCreated {
        return vehicleParking(getLot(parkingInfo.getLocation()), parkingInfo);
    }

    public ParkingReceipt unParking(ParkingTicket ticket) throws VehicleNotParckedHere, NoLocationAvailableException, VehicleTypeMismatch, VehicleAlreadyTaken {
        return vehicleUnPark(getLot(ticket.getLocation()), ticket.getTicketNumber());
    }

    ParkingLot getLot(String location) throws NoLocationAvailableException {
        Optional<ParkingLot> lot = lots.stream().filter(lot1 -> lot1.getName().equals(location)).findFirst();
        if (lot.isPresent()) {
            return lot.get();
        }
        throw new NoLocationAvailableException("We don't have parkinglot in this location :)");
    }

    public ParkingReceipt vehicleUnPark(ParkingLot parkingLot, String ticketNumber) throws VehicleNotParckedHere, VehicleTypeMismatch, VehicleAlreadyTaken {
        ParkingEntry parkingEntry = parkingLotManager.vehicleEntry(parkingLot.getParkingRegister(), ticketNumber);
        if (parkingEntry == null) {
            throw new VehicleNotParckedHere("Sorry i think you have a memory problem :). your vehicle is not parked here");
        }
        if (parkingEntry.getReceipt() != null) {
            throw new VehicleAlreadyTaken("Some one took your vehicle using your ticket already. please do compliant to police");
        }
        ParkingReceipt receipt = parkingLotManager.getReceipt(parkingEntry.getTicket(), parkingLot);
        parkingEntry.setReceipt(receipt);
        spotManager.freeSpot(parkingLot.getSpot(), parkingEntry.getTicket());
        parkingLotManager.deRegister(parkingLot.getParkingRegister(), ticketNumber, parkingEntry);
        return receipt;
    }

    public ParkingTicket vehicleParking(ParkingLot parkingLot, ParkingRequest parkingRequest) throws SpotNotAvailable, VehicleTypeMismatch, TicketAlreadyCreated {
        VTYPE vehicleType = parkingRequest.getVehicleType();
        VGTYPE vgtype = getVGTYPE(vehicleType);
        if(parkingLot.getSpot().get(vgtype)==null){
            throw new SpotNotAvailable("No " + vehicleType.name() + " parking spot available");
        }
        long availableSpot = spotManager.getAvailableSpot(parkingLot.getSpot(), vgtype);
        if (availableSpot == 0) {
            throw new SpotNotAvailable(vgtype.name() + " parking full");
        }
        if (parkingLotManager.isAlreadyParked(parkingLot.getParkingRegister(), parkingRequest.getVehicleNumber())) {
            throw new TicketAlreadyCreated("Sorry we will give one ticket to one vehicle. so please do park and enjoy");
        }
        return parkingLotManager.parkVehicle(parkingLot.getSpot(), parkingLot.getParkingRegister(), parkingRequest);
    }
}
