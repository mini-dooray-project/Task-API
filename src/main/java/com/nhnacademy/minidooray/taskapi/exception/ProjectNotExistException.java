package com.nhnacademy.minidooray.taskapi.exception;

public class ProjectNotExistException extends RuntimeException{
    public ProjectNotExistException() {
        super("프로젝트가 존재하지 않습니다.");
    }
}
