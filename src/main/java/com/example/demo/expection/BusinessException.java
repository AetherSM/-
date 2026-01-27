package com.example.demo.expection;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
