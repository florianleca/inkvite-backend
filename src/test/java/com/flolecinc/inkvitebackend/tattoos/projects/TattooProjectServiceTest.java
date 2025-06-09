package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtist;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClient;
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
        TattooProject project = new TattooProject();
        TattooArtist artist = new TattooArtist();
        TattooClient client = new TattooClient();

        // When
        tattooProjectService.bindEntitiesAndSaveProject(project, artist, client);

        // Then
        verify(tattooProjectRepository).save(project);
        assertEquals(artist, project.getTattooArtist());
        assertEquals(client, project.getTattooClient());
    }

}