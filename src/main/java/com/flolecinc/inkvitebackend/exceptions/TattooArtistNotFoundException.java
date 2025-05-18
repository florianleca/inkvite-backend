package com.flolecinc.inkvitebackend.exceptions;

import java.util.UUID;

public class TattooArtistNotFoundException extends RuntimeException {

    public TattooArtistNotFoundException(UUID id) {
        super("Tattoo artist with ID " + id + " not found");
    }

}
