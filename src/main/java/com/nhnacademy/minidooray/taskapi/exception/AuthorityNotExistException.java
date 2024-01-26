package com.nhnacademy.minidooray.taskapi.exception;

public class AuthorityNotExistException extends RuntimeException{
    public AuthorityNotExistException() {
        super("일치하는 권한이 존재하지 않습니다.");
    }
}
