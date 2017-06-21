package com.stackroute.gupshup.circleservice.exception;

//import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CircleCreationException extends Exception {

    public CircleCreationException(String message) {
        super(message);
    }

}