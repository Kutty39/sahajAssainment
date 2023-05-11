package com.example.demo.response;

import com.example.demo.common.cons.VTYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParkingTicket {
    private String ticketNumber;
    private String vehicleNumber;
    private VTYPE vehicleType;
    private Long spot;
    private String location;
    private LocalDateTime entryDateTime;

    public ParkingTicket(LocalDateTime now) {
        this.entryDateTime=now;
    }
}
