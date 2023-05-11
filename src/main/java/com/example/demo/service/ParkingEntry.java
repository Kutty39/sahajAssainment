package com.example.demo.service;

import com.example.demo.response.ParkingTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingEntry {
    private ParkingTicket ticket;
    private ParkingReceipt receipt;
}
