package com.example.demo.expection;

public class NotExistException extends RuntimeException {
    public NotExistException(String message) {
        super(message);
    }
}
