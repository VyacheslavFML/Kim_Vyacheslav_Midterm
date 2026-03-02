package com.example.kim.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super (message);
    }
}
