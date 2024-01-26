package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;

@Getter
public class DeleteResponse {
    private String response;

    public DeleteResponse(String response) {
        this.response = response;
    }
}
