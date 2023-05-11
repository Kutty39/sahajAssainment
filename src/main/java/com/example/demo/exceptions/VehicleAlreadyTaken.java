package com.example.demo.exceptions;

public class VehicleAlreadyTaken extends Throwable {
    public VehicleAlreadyTaken(String s) {
        super(s);
    }
}
