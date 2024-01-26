package com.nhnacademy.minidooray.taskapi.exception;

public class TaskNotExistException extends RuntimeException{
    public TaskNotExistException() {
        super("업무가 존재하지 않습니다.");
    }
}
