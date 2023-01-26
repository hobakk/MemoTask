package com.sparta.memotask.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private final Long statusCode;
    private final String statusMessage;

    public ResponseDto(Long statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
