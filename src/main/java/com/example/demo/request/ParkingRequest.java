package com.example.demo.request;

import com.example.demo.common.cons.VTYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingRequest {
    private VTYPE vehicleType;
    private String vehicleNumber;
    private String location;
}
