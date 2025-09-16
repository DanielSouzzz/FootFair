package com.project.footfair.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.footfair.util.DateTimeUtils;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {

    private String message;
    private Instant timestamp;
    private Map<String, String> errors;

    public ErrorResponseDTO(String message, Instant timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponseDTO(String message, Instant timestamp, Map<String, String> errors) {
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return DateTimeUtils.formatToSeconds(timestamp);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
