package com.flolecinc.inkvitebackend.tattoos.references;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import com.flolecinc.inkvitebackend.tattoos.requestforms.RequestFormDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TattooReferenceService {

    private TattooReferenceRepository tattooReferenceRepository;

    @Transactional
    public void saveReferencesFromFormReferences(List<RequestFormDto.ReferenceDto> referenceDtos,
                                                 TattooProjectEntity project) {
        List<TattooReferenceEntity> references = referenceDtos.stream()
                .map(referenceDto -> {
                    TattooReferenceEntity tattooReference = new TattooReferenceEntity();
                    tattooReference.setImageLink(referenceDto.getImagePath());
                    tattooReference.setComment(referenceDto.getComment());
                    tattooReference.setTattooProject(project);
                    return tattooReference;
                })
                .toList();
        tattooReferenceRepository.saveAll(references);
    }

}
