package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtist;
import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistService;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClient;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientService;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProject;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectService;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReference;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    private static final String ARTIST_USERNAME = "artist_username";

    @Test
    void handleNewRequestForm_nominal_servicesCalled() {
        // Given
        TattooArtist artist = new TattooArtist();
        RequestFormDto requestForm = new RequestFormDto();
        TattooReference reference1 = new TattooReference();
        TattooReference reference2 = new TattooReference();
        TattooClient client = new TattooClient();
        TattooProject project = new TattooProject();
        requestForm.setIdentity(client);
        requestForm.setProjectDetails(project);
        project.setReferences(List.of(reference1, reference2));
        when(tattooArtistService.retrieveTattooArtistFromUsername(ARTIST_USERNAME)).thenReturn(artist);
        when(tattooClientService.saveClient(client)).thenReturn(client);
        when(tattooProjectService.bindEntitiesAndSaveProject(project, artist, client)).thenReturn(project);

        // When
        requestFormService.handleRequestForm(ARTIST_USERNAME, requestForm);

        // Then
        verify(tattooArtistService).retrieveTattooArtistFromUsername(ARTIST_USERNAME);
        verify(tattooClientService).saveClient(client);
        verify(tattooProjectService).bindEntitiesAndSaveProject(project, artist, client);
        verify(tattooReferenceService).bindReferencesToProjectAndSaveThem(List.of(reference1, reference2), project);
    }

}