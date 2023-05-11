package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.exceptions.VehicleTypeMismatch;
import com.example.demo.request.ParkingRequest;
import com.example.demo.response.ParkingTicket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.service.UtilService.getVGTYPE;

@Service
public class ParkingLotManager {
    public ParkingTicket parkVehicle(HashMap<VGTYPE, HashMap<Long, ParkingTicket>> spot, HashMap<String, ParkingEntry> parkingRegister, ParkingRequest parkingRequest) throws VehicleTypeMismatch {
        ParkingTicket ticket = new ParkingTicket(LocalDateTime.now());
        HashMap<Long, ParkingTicket> ticketHashMap = spot.get(getVGTYPE(parkingRequest.getVehicleType()));
        Long spotNumber = ticketHashMap.entrySet().stream().filter(stringParkingTicketEntry -> stringParkingTicketEntry.getValue() == null).sorted(Comparator.comparing(Map.Entry::getKey)).findFirst().get().getKey();
        String ticketNumber = String.format("%04d", parkingRegister.size() + 1);
        ticket.setTicketNumber(ticketNumber);
        ticket.setSpot(spotNumber);
        ticket.setVehicleNumber(parkingRequest.getVehicleNumber());
        ticket.setVehicleType(parkingRequest.getVehicleType());
        ticket.setLocation(parkingRequest.getLocation());
        addParkingEntry(parkingRegister,ticket);
        ticketHashMap.put(spotNumber,ticket);
        return ticket;
    }

    public ParkingEntry vehicleEntry(HashMap<String, ParkingEntry> parkingRegister, String ticketNumber) {
        return parkingRegister.get(ticketNumber);
    }
    public ParkingReceipt getReceipt(ParkingTicket ticket, ParkingLot parkingLot){
        ParkingReceipt receipt = new ParkingReceipt();
        receipt.setReceiptNumber("R-" + ticket.getTicketNumber());
        receipt.setEntryDateTime(ticket.getEntryDateTime());
        receipt.setExitDateTime(LocalDateTime.now());
        Duration duration = Duration.between(receipt.getEntryDateTime(), receipt.getExitDateTime());
        receipt.setFee(parkingLot.feeCalculation(ticket.getVehicleType(),duration));
        return receipt;
    }

    public void deRegister(HashMap<String, ParkingEntry> parkingRegister, String ticketNumber, ParkingEntry parkingEntry) {
        parkingRegister.put(ticketNumber, parkingEntry);
    }
    private void addParkingEntry(HashMap<String, ParkingEntry> parkingRegister,ParkingTicket ticket) {
        ParkingEntry entry = new ParkingEntry();
        entry.setTicket(ticket);
        parkingRegister.put(ticket.getTicketNumber(), entry);
    }
    public boolean isAlreadyParked(HashMap<String, ParkingEntry> parkingRegister, String vehicleNumber) {
        return parkingRegister.entrySet().stream().anyMatch(stringParkingEntryEntry -> stringParkingEntryEntry.getValue().getTicket().getVehicleNumber().equals(vehicleNumber));
    }
}
