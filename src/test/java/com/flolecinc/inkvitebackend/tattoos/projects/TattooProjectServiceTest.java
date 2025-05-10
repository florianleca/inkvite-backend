package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.requestforms.RequestFormDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
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
        RequestFormDto.ProjectDetailsDto projectDetails = new RequestFormDto.ProjectDetailsDto();
        TattooArtistEntity artist = new TattooArtistEntity();
        TattooClientEntity client = new TattooClientEntity();

        // When
        tattooProjectService.saveProjectFromProjectDetails(projectDetails, artist, client);

        // Then
        verify(tattooProjectRepository).save(any());
    }

}