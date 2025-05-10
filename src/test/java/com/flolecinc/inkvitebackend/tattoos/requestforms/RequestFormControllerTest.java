package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RequestFormController.class)
class RequestFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RequestFormService requestFormService;

    @MockitoBean
    private TattooArtistService tattooArtistService;

    @Test
    void postNewTattooRequest_nominal_serviceCalledAndStatusOk() throws Exception {
        // Given
        UUID artistId = UUID.randomUUID();
        TattooArtistEntity mockArtist = new TattooArtistEntity();
        mockArtist.setId(artistId);
        // Mocking the tattoo artist retrieval
        when(tattooArtistService.retrieveTattooArtist(artistId)).thenReturn(Optional.of(mockArtist));
        ObjectMapper objectMapper = new ObjectMapper();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/tattoos/requests/{tattooArtistId}", artistId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RequestFormDto())))
                .andExpect(status().isCreated())
                .andExpect(content().string("Tattoo project successfully created and saved"));
        verify(requestFormService).handleNewRequestForm(eq(mockArtist), any());
    }

    @Test
    void postNewTattooRequest_unknownArtist_exceptionHandledAndStatusNotFound() throws Exception {
        // Given
        UUID artistId = UUID.randomUUID();
        // Mocking an empty result for an unknown tattoo artist retrieval attempt
        when(tattooArtistService.retrieveTattooArtist(artistId)).thenReturn(Optional.empty());
        ObjectMapper objectMapper = new ObjectMapper();

        // When & Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/tattoos/requests/{tattooArtistId}", artistId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RequestFormDto())))
                .andExpect(status().isNotFound())
                .andReturn();
        assertEquals("Tattoo artist not found", result.getResponse().getErrorMessage());
        verify(requestFormService, never()).handleNewRequestForm(any(), any());
    }

}