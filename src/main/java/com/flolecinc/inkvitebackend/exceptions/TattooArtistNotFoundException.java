package com.flolecinc.inkvitebackend.exceptions;

public class TattooArtistNotFoundException extends RuntimeException {

    public TattooArtistNotFoundException(String username) {
        super("Tattoo artist with username '" + username + "' not found");
    }

}
