package com.example.demo.exceptions;

import com.example.demo.response.ExpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExHandler {
    @ExceptionHandler(Throwable.class)
    public ExpResponse handleAll(Throwable e){
        return new ExpResponse(e.getCause().getMessage());
    }

}
