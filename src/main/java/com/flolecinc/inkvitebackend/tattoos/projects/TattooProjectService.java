package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtist;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TattooProjectService {

    private TattooProjectRepository tattooProjectRepository;

    @Transactional
    public TattooProject bindEntitiesAndSaveProject(TattooProject tattooProject,
                                                    TattooArtist tattooArtist,
                                                    TattooClient tattooClient) {
        tattooProject.setTattooArtist(tattooArtist);
        tattooProject.setTattooClient(tattooClient);
        return tattooProjectRepository.save(tattooProject);
    }

}
