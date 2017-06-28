package com.stackroute.gupshup.circleservice.exception;

//import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CircleException extends Exception {

    public CircleException(String message) {
        super(message);
    }

}