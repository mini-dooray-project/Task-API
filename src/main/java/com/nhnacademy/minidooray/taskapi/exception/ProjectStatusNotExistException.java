package com.nhnacademy.minidooray.taskapi.exception;

public class ProjectStatusNotExistException extends RuntimeException{

    public ProjectStatusNotExistException(String message) {
        super(message);
    }
}
