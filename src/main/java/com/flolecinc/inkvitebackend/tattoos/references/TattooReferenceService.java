package com.flolecinc.inkvitebackend.tattoos.references;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TattooReferenceService {

    private TattooReferenceRepository tattooReferenceRepository;

    @Transactional
    public void bindReferencesToProjectAndSaveThem(List<TattooReferenceEntity> references,
                                                   TattooProjectEntity project) {
        references.forEach(reference -> reference.setTattooProject(project));
        tattooReferenceRepository.saveAll(references);
    }

}
