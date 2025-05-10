package com.flolecinc.inkvitebackend.tattoos.references;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import com.flolecinc.inkvitebackend.tattoos.requestforms.RequestFormDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TattooReferenceServiceTest {

    @Mock
    private TattooReferenceRepository tattooReferenceRepository;

    @InjectMocks
    private TattooReferenceService tattooReferenceService;

    @Test
    void saveReferencesFromFormReferences_nominal_repositoryCalled() {
        // Given
        RequestFormDto.ReferenceDto referenceDto = new RequestFormDto.ReferenceDto();
        TattooProjectEntity project = new TattooProjectEntity();

        // When
        tattooReferenceService.saveReferencesFromFormReferences(List.of(referenceDto), project);

        // Then
        verify(tattooReferenceRepository).saveAll(any());
    }

}