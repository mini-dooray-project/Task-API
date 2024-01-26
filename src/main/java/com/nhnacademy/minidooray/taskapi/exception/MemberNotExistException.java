package com.nhnacademy.minidooray.taskapi.exception;

public class MemberNotExistException extends RuntimeException{
    public MemberNotExistException() {
        super("프로젝트와 일치하는 유저가 존재하지 않습니다.");
    }
}
