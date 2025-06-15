package com.flolecinc.inkvitebackend.exceptions;

public class UnsupportedImageTypeException extends RuntimeException {

    public UnsupportedImageTypeException(String contentType) {
        super("Unsupported image type: " + contentType);
    }

}
