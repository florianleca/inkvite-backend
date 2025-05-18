package com.flolecinc.inkvitebackend.tattoos.artists;

import com.flolecinc.inkvitebackend.exceptions.TattooArtistNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TattooArtistService {

    private TattooArtistRepository tattooArtistRepository;

    public TattooArtistEntity retrieveTattooArtist(UUID tattooArtistId) {
        return tattooArtistRepository.findById(tattooArtistId).orElseThrow(
                () -> new TattooArtistNotFoundException(tattooArtistId)
        );

    }

}
