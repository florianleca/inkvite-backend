package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistService;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientService;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectService;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestFormServiceTest {

    @Mock
    private TattooArtistService tattooArtistService;

    @Mock
    private TattooClientService tattooClientService;

    @Mock
    private TattooProjectService tattooProjectService;

    @Mock
    private TattooReferenceService tattooReferenceService;

    @InjectMocks
    private RequestFormService requestFormService;

    @Test
    void handleNewRequestForm_nominal_servicesCalled() {
        // Given
        UUID artistId = UUID.randomUUID();
        TattooArtistEntity artist = new TattooArtistEntity();
        artist.setId(artistId);
        RequestFormDto requestForm = new RequestFormDto();
        RequestFormDto.IdentityDto identity = new RequestFormDto.IdentityDto();
        RequestFormDto.ProjectDetailsDto projectDetails = new RequestFormDto.ProjectDetailsDto();
        RequestFormDto.ReferenceDto referenceDto1 = new RequestFormDto.ReferenceDto();
        RequestFormDto.ReferenceDto referenceDto2 = new RequestFormDto.ReferenceDto();
        TattooClientEntity client = new TattooClientEntity();
        TattooProjectEntity project = new TattooProjectEntity();
        requestForm.setIdentity(identity);
        requestForm.setProjectDetails(projectDetails);
        projectDetails.setReferences(List.of(referenceDto1, referenceDto2));
        when(tattooArtistService.retrieveTattooArtist(artistId)).thenReturn(artist);
        when(tattooClientService.saveClientFromIdentity(identity)).thenReturn(client);
        when(tattooProjectService.saveProjectFromProjectDetails(projectDetails, artist, client)).thenReturn(project);

        // When
        requestFormService.handleNewRequestForm(artistId, requestForm);

        // Then
        verify(tattooArtistService).retrieveTattooArtist(artistId);
        verify(tattooClientService).saveClientFromIdentity(identity);
        verify(tattooProjectService).saveProjectFromProjectDetails(projectDetails, artist, client);
        verify(tattooReferenceService).saveReferencesFromFormReferences(List.of(referenceDto1, referenceDto2), project);
    }

}