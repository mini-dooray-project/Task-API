package com.nhnacademy.minidooray.taskapi.exception;

public class TaskTagNotExistException extends RuntimeException{
    public TaskTagNotExistException(String message) {
        super(message);
    }
}
