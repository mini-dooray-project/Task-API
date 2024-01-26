package com.nhnacademy.minidooray.taskapi.exception;

public class MilestoneNotExistException extends RuntimeException{
    public MilestoneNotExistException() {
        super("마일스톤이 존재하지 않습니다.");
    }
}
