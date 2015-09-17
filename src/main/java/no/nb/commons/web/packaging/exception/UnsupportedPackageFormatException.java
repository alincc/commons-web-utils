package no.nb.commons.web.packaging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unsupported package format")
public class UnsupportedPackageFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnsupportedPackageFormatException(String message) {
        super(message);
    }
}