package com.flolecinc.inkvitebackend.exceptions;

import java.net.MalformedURLException;

public class InvalidDownloadURLException extends RuntimeException {

    public InvalidDownloadURLException(String message) {
        super(message);
    }

    public InvalidDownloadURLException(MalformedURLException e) {
        super(e);
    }

}
