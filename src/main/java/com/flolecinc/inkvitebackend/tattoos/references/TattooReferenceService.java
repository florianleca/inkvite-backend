package com.flolecinc.inkvitebackend.tattoos.references;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TattooReferenceService {

    private TattooReferenceRepository tattooReferenceRepository;

    @Transactional
    public void bindReferencesToProjectAndSaveThem(List<TattooReference> references,
                                                   TattooProject project) {
        references.forEach(reference -> reference.setTattooProject(project));
        tattooReferenceRepository.saveAll(references);
    }

}
