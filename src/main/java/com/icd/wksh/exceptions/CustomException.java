package com.icd.wksh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    public CustomException(String message) {
        super(message);
    }

    public CustomException (String message, Throwable cause) {
        super(message, cause);
    }
}
