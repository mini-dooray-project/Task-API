package com.nhnacademy.minidooray.taskapi.exception;

public class TagNotExistException extends RuntimeException{

    public TagNotExistException() {
        super("태그가 존재하지 않습니다.");
    }
}
