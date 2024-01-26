package com.nhnacademy.minidooray.taskapi.exception;

public class ProjectStatusNotExistException extends RuntimeException{

    public ProjectStatusNotExistException() {
        super("프로젝트 상태가 존재하지 않습니다.");
    }
}
