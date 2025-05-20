package com.flolecinc.inkvitebackend.tattoos.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TattooClientServiceTest {

    @Mock
    private TattooClientRepository tattooClientRepository;

    @InjectMocks
    private TattooClientService tattooClientService;

    @Test
    void saveClientFromIdentity_nominal_repositoryCalled() {
        // Given
        TattooClientEntity client = new TattooClientEntity();

        // When
        tattooClientService.saveClient(client);

        // Then
        verify(tattooClientRepository).save(client);
    }

}