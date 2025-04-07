package com.RuanPablo2.ForumAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenActionException extends RuntimeException {
    private final String errorCode;

    public ForbiddenActionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ForbiddenActionException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}