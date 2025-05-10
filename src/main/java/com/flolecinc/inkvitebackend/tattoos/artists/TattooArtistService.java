package com.flolecinc.inkvitebackend.tattoos.artists;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TattooArtistService {

    private TattooArtistRepository tattooArtistRepository;

    public Optional<TattooArtistEntity> retrieveTattooArtist(UUID tattooArtistId) {
        return tattooArtistRepository.findById(tattooArtistId);
    }

}
