package com.nhnacademy.minidooray.taskapi.exception;

public class TaskTagAlreadyExistException extends RuntimeException{
    public TaskTagAlreadyExistException(String message) {
        super(message);
    }
}
