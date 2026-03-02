package com.example.kim.exception;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException (Long id){
        super("Student with id " + id + " was not found");
    }
}