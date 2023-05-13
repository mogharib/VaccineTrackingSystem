package com.System.VaccineTracking.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorMessageCode {
    RESOURCE_NOT_FOUND_ERROR(9002, HttpStatus.NOT_FOUND.value(), "error.resource.notfound");

    private int httpStatus;
    private int errorCode;
    private String messageKey;

    ErrorMessageCode(int httpStatus, String messageKey) {
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;
    }

    ErrorMessageCode(int errorCode, int httpStatus, String messageKey) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.messageKey = messageKey;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
