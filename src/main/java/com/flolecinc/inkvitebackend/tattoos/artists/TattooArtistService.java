package com.flolecinc.inkvitebackend.tattoos.artists;

import com.flolecinc.inkvitebackend.exceptions.TattooArtistNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TattooArtistService {

    private TattooArtistRepository tattooArtistRepository;

    public TattooArtistEntity retrieveTattooArtistFromUsername(String tattooArtistUsername) {
        return tattooArtistRepository.findByUsername(tattooArtistUsername).orElseThrow(
                () -> new TattooArtistNotFoundException(tattooArtistUsername)
        );
    }

}
