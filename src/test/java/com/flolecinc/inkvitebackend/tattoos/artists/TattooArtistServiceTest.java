package com.flolecinc.inkvitebackend.tattoos.artists;

import com.flolecinc.inkvitebackend.exceptions.TattooArtistNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
        TattooArtistEntity result = tattooArtistService.retrieveTattooArtist(artistId);

        // Then
        assertEquals(artist, result);
        verify(tattooArtistRepository).findById(artistId);
    }

    @Test
    void retrieveTattooArtist_artistDoesntExist_emptyReturned() {
        // Given
        UUID artistId = UUID.randomUUID();
        when(tattooArtistRepository.findById(artistId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TattooArtistNotFoundException.class, () -> tattooArtistService.retrieveTattooArtist(artistId));
        verify(tattooArtistRepository).findById(artistId);
    }

}