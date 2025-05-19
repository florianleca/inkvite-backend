package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistService;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientService;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectService;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceEntity;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RequestFormService {

    private TattooArtistService tattooArtistService;
    private TattooClientService tattooClientService;
    private TattooProjectService tattooProjectService;
    private TattooReferenceService tattooReferenceService;

    /**
     * Saving a new tattoo project (and associated references) after receiving a request form.
     *
     * @param tattooArtistId The ID of the artist with whom the client wants to connect for his project
     * @param requestForm The request form that the client filled and sent
     */
    @Transactional
    public void handleNewRequestForm(UUID tattooArtistId, RequestFormDto requestForm) {
        TattooArtistEntity artist = tattooArtistService.retrieveTattooArtist(tattooArtistId);

        RequestFormDto.IdentityDto identity = requestForm.getIdentity();
        TattooClientEntity client = tattooClientService.saveClientFromIdentity(identity);

        RequestFormDto.ProjectDetailsDto projectDetails = requestForm.getProjectDetails();
        TattooProjectEntity project = tattooProjectService.saveProjectFromProjectDetails(projectDetails, artist, client);

        List<TattooReferenceEntity> referenceDtos = requestForm.getProjectDetails().getReferences();
        tattooReferenceService.saveReferencesFromFormReferences(referenceDtos, project);
    }

}
