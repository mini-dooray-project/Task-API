package com.nhnacademy.minidooray.taskapi.exception;

public class TagNotExistException extends RuntimeException{

    public TagNotExistException(String message) {
        super(message);
    }
}
