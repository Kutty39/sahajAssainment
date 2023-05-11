package com.example.demo.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReceipt {
    private String receiptNumber;
    private LocalDateTime entryDateTime;
    private LocalDateTime exitDateTime;
    private long fee;
}
