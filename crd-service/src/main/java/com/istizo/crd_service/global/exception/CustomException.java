package com.istizo.crd_service.global.exception;


import com.istizo.crd_service.global.response.status.ErrorStatus;

public class CustomException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public CustomException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}