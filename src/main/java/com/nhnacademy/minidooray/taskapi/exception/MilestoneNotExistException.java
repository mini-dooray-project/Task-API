package com.nhnacademy.minidooray.taskapi.exception;

public class MilestoneNotExistException extends RuntimeException{
    public MilestoneNotExistException(String message) {
        super(message);
    }
}
