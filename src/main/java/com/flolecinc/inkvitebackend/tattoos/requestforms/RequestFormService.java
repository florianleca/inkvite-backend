package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientService;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectService;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestFormService {


    private TattooClientService tattooClientService;
    private TattooProjectService tattooProjectService;
    private TattooReferenceService tattooReferenceService;

    /**
     * Saving a new tattoo project (and associated references) after receiving a request form.
     *
     * @param artist The artist the client want to connect with for his project
     * @param requestForm The request form that the client filled and sent
     */
    public void handleNewRequestForm(TattooArtistEntity artist, RequestFormDto requestForm) {
        RequestFormDto.IdentityDto identity = requestForm.getIdentity();
        TattooClientEntity client = tattooClientService.saveClientFromIdentity(identity);

        RequestFormDto.ProjectDetailsDto projectDetails = requestForm.getProjectDetails();
        TattooProjectEntity project = tattooProjectService.saveProjectFromProjectDetails(projectDetails, artist, client);

        List<RequestFormDto.ReferenceDto> referenceDtos = requestForm.getProjectDetails().getReferences();
        tattooReferenceService.saveReferencesFromFormReferences(referenceDtos, project);
    }

}
