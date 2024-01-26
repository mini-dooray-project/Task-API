package com.nhnacademy.minidooray.taskapi.exception;

public class TaskTagAlreadyExistException extends RuntimeException{
    public TaskTagAlreadyExistException() {
        super("대응하는 업무-태그가 존재합니다.");
    }
}
