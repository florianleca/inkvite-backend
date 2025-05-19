package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TattooProjectService {

    private TattooProjectRepository tattooProjectRepository;

    @Transactional
    public TattooProjectEntity saveProjectFromProjectDetails(TattooProjectEntity tattooProject,
                                                             TattooArtistEntity tattooArtist,
                                                             TattooClientEntity tattooClient) {
        tattooProject.setTattooArtist(tattooArtist);
        tattooProject.setTattooClient(tattooClient);
        return tattooProjectRepository.save(tattooProject);
    }

}
