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
     * @param tattooArtistUsername The username of the desired artist
     * @param requestForm The request form that the client filled and sent
     */
    @Transactional
    public void handleRequestForm(String tattooArtistUsername, RequestFormDto requestForm) {
        TattooArtistEntity artist = tattooArtistService.retrieveTattooArtistFromUsername(tattooArtistUsername);

        TattooClientEntity client = requestForm.getIdentity();
        client = tattooClientService.saveClient(client);

        TattooProjectEntity project = requestForm.getProjectDetails();
        project = tattooProjectService.bindEntitiesAndSaveProject(project, artist, client);

        List<TattooReferenceEntity> referenceDtos = requestForm.getProjectDetails().getReferences();
        tattooReferenceService.bindReferencesToProjectAndSaveThem(referenceDtos, project);
    }

}
