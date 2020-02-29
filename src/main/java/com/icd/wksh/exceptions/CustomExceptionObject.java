package com.icd.wksh.exceptions;

import java.time.LocalDateTime;

public class CustomExceptionObject {
    private String status;
    private String message;
    private LocalDateTime errorTime;

    public CustomExceptionObject(String status, String message, LocalDateTime errorTime) {
        this.status = status;
        this.message = message;
        this.errorTime = errorTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(LocalDateTime errorTime) {
        this.errorTime = errorTime;
    }
}
