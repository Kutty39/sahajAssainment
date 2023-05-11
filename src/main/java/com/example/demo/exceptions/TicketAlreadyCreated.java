package com.example.demo.exceptions;

public class TicketAlreadyCreated extends Throwable {
    public TicketAlreadyCreated(String s) {
        super(s);
    }
}
