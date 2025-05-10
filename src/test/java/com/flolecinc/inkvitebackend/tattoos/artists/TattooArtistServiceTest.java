package com.flolecinc.inkvitebackend.tattoos.artists;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TattooArtistServiceTest {

    @Mock
    private TattooArtistRepository tattooArtistRepository;

    @InjectMocks
    private TattooArtistService tattooArtistService;

    @Test
    void retrieveTattooArtist_artistExists_artistReturned() {
        // Given
        UUID artistId = UUID.randomUUID();
        TattooArtistEntity artist = new TattooArtistEntity();
        when(tattooArtistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        // When
        Optional<TattooArtistEntity> result = tattooArtistService.retrieveTattooArtist(artistId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(artist, result.get());
        verify(tattooArtistRepository).findById(artistId);
    }

    @Test
    void retrieveTattooArtist_artistDoesntExist_emptyReturned() {
        // Given
        UUID artistId = UUID.randomUUID();
        when(tattooArtistRepository.findById(artistId)).thenReturn(Optional.empty());

        // When
        Optional<TattooArtistEntity> result = tattooArtistService.retrieveTattooArtist(artistId);

        // Then
        assertTrue(result.isEmpty());
        verify(tattooArtistRepository).findById(artistId);
    }

}