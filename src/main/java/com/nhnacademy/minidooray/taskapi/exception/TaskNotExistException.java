package com.nhnacademy.minidooray.taskapi.exception;

public class TaskNotExistException extends RuntimeException{
    public TaskNotExistException(String message) {
        super(message);
    }
}
