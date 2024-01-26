package com.nhnacademy.minidooray.taskapi.exception;

public class ValidationException extends RuntimeException{
    public ValidationException() {
        super("부적절한 입력 양식입니다.");
    }
}
