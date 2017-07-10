package com.stackroute.gupshup.circleservice.exception;

//import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CircleException extends Exception {

	private static final long serialVersionUID = 1L;

	public CircleException(String message) {
        super(message);
    }

}