package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.requestforms.RequestFormDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TattooProjectService {

    private TattooProjectRepository tattooProjectRepository;

    public TattooProjectEntity saveProjectFromProjectDetails(RequestFormDto.ProjectDetailsDto projectDetails,
                                                             TattooArtistEntity tattooArtist,
                                                             TattooClientEntity tattooClient) {
        TattooProjectEntity project = new TattooProjectEntity();
        project.setDesiredDate(projectDetails.getDesiredDate());
        project.setProjectDescription(projectDetails.getProjectDescription());
        project.setBodyPart(projectDetails.getBodyPart());
        project.setTattooArtist(tattooArtist);
        project.setTattooClient(tattooClient);
        return tattooProjectRepository.save(project);
    }

}
