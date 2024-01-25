package com.nhnacademy.minidooray.taskapi.exception;

public class ProjectNotExistException extends RuntimeException{
    public ProjectNotExistException(String message) {
        super(message);
    }
}
