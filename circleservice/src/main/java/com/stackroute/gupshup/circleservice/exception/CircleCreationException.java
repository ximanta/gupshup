package com.stackroute.gupshup.circleservice.exception;

//import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CircleCreationException extends RuntimeException {
//    public CircleCreationException() {
//        super();
//    }
//    public CircleCreationException(String message, Throwable cause) {
//        super(message, cause);
//    }
    public CircleCreationException(String message) {
        super(message);
    }
//    public CircleCreationException(Throwable cause) {
//        super(cause);
//    }
}