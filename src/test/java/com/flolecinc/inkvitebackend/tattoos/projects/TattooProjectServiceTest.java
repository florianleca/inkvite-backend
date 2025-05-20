package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TattooProjectServiceTest {

    @Mock
    private TattooProjectRepository tattooProjectRepository;

    @InjectMocks
    private TattooProjectService tattooProjectService;

    @Test
    void saveClientFromIdentity_nominal_repositoryCalled() {
        // Given
        TattooProjectEntity project = new TattooProjectEntity();
        TattooArtistEntity artist = new TattooArtistEntity();
        TattooClientEntity client = new TattooClientEntity();

        // When
        tattooProjectService.bindEntitiesAndSaveProject(project, artist, client);

        // Then
        verify(tattooProjectRepository).save(project);
        assertEquals(artist, project.getTattooArtist());
        assertEquals(client, project.getTattooClient());
    }

}