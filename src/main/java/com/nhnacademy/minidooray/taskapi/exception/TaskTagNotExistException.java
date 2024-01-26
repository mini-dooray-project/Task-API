package com.nhnacademy.minidooray.taskapi.exception;

public class TaskTagNotExistException extends RuntimeException{
    public TaskTagNotExistException() {
        super("대응하는 업무-태그가 존재하지 않습니다.");
    }
}
