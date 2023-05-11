package com.example.demo.service;

import com.example.demo.common.cons.VGTYPE;
import com.example.demo.common.cons.VTYPE;
import com.example.demo.exceptions.VehicleTypeMismatch;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
    public static VGTYPE getVGTYPE(VTYPE vehicleType) throws VehicleTypeMismatch {
        switch (vehicleType){
            case MOTORCYCLE:
            case SCOOTER:
                return VGTYPE.TWOWHEELER;
            case CAR:
            case SUV:
                return VGTYPE.FOURWHEELER;
            case BUS:
            case TRUCK:
                return VGTYPE.HEAVY;
        }
        throw new VehicleTypeMismatch("This type is very new. in feature will add this in our parking :). now it is not available");
    }
}
