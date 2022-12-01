package com.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class Config {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }

    @Getter
    public enum ErrorCode {
        SUCCESS(200),
        BAD_REQUEST(400),
        SERVER_ERROR(500);

        int code;

        ErrorCode(int code) {
        }
    }
}
