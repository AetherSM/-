package com.example.demo.expection;

public class TokenNullException extends RuntimeException {
    public TokenNullException(String message) {
        super(message);
    }
}
