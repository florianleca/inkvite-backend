package com.flolecinc.inkvitebackend.tattoos.references;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        TattooReference reference = new TattooReference();
        TattooProject project = new TattooProject();

        // When
        tattooReferenceService.bindReferencesToProjectAndSaveThem(List.of(reference), project);

        // Then
        verify(tattooReferenceRepository).saveAll(List.of(reference));
        assertEquals(project, reference.getTattooProject());
    }

}