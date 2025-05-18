package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flolecinc.inkvitebackend.exceptions.TattooArtistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestFormController.class)
class RequestFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RequestFormService requestFormService;

    private RequestFormDto requestFormDto;

    @BeforeEach
    void setUp() {
        RequestFormDto.IdentityDto identity = new RequestFormDto.IdentityDto("John", "Doe", "john.doe@aol.com");
        RequestFormDto.ReferenceDto reference1 = new RequestFormDto.ReferenceDto("https://example.com/image1.jpg", "Description 1");
        RequestFormDto.ReferenceDto reference2 = new RequestFormDto.ReferenceDto("https://example.com/image2.jpg", "Description 2");
        RequestFormDto.ProjectDetailsDto projectDetails = new RequestFormDto.ProjectDetailsDto(LocalDate.of(2025, 5, 18), "Japonese dragon", "Left shoulder", List.of(reference1, reference2));
        requestFormDto = new RequestFormDto(identity, projectDetails);
    }

    @Test
    void postNewTattooRequest_nominal_serviceCalledAndStatusOk() throws Exception {
        // Given
        UUID artistId = UUID.randomUUID();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/tattoos/requests/{tattooArtistId}", artistId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestFormDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Tattoo project successfully created and saved"));
        verify(requestFormService).handleNewRequestForm(eq(artistId), any());
    }

    @Test
    void postNewTattooRequest_unknownArtist_exceptionHandledAndStatusNotFound() throws Exception {
        // Given
        UUID artistId = UUID.randomUUID();
        // Mocking a thrown exception for trying to retrieve an unknown tattoo artist
        doThrow(new TattooArtistNotFoundException(artistId)).when(requestFormService).handleNewRequestForm(eq(artistId), any());

        // When & Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/tattoos/requests/{tattooArtistId}", artistId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestFormDto)))
                .andExpect(status().isNotFound())
                .andReturn();
        assertEquals("{\"error\":\"Tattoo artist with ID " + artistId + " not found\"}", result.getResponse().getContentAsString());
        verify(requestFormService).handleNewRequestForm(eq(artistId), any());
    }

    @Test
    void postNewTattooRequest_invalidEmail_exceptionHandledAndStatusBadRequest() throws Exception {
        // Given
        requestFormDto.getIdentity().setEmail("invalid-email");
        // When & Then
        invalidRequestHelper("{\"identity.email\":\"must be a well-formed email address\"}");
    }

    @Test
    void postNewTattooRequest_nullRootFields_exceptionHandledAndStatusBadRequest() throws Exception {
        // Given
        requestFormDto = new RequestFormDto();
        // When & Then
        invalidRequestHelper("\"identity\":\"must not be null\"", "\"projectDetails\":\"must not be null\"");
    }

    @Test
    void postNewTattooRequest_blankIdentityFields_exceptionHandledAndStatusBadRequest() throws Exception {
        // Given
        requestFormDto.setIdentity(new RequestFormDto.IdentityDto());
        // When & Then
        invalidRequestHelper("\"identity.lastName\":\"must not be blank\"",
                "\"identity.email\":\"must not be blank\"",
                "\"identity.firstName\":\"must not be blank\"");
    }

    @Test
    void postNewTattooRequest_nullProjectDetailsFields_exceptionHandledAndStatusBadRequest() throws Exception {
        // Given
        requestFormDto.setProjectDetails(new RequestFormDto.ProjectDetailsDto());
        // When & Then
        invalidRequestHelper("\"projectDetails.projectDescription\":\"must not be blank\"",
                "\"projectDetails.references\":\"must not be empty\"",
                "\"projectDetails.desiredDate\":\"must not be null\"",
                "\"projectDetails.bodyPart\":\"must not be blank\"");
    }

    @Test
    void postNewTattooRequest_blankReferenceFields_exceptionHandledAndStatusBadRequest() throws Exception {
        // Given
        requestFormDto.getProjectDetails().setReferences(List.of(new RequestFormDto.ReferenceDto()));
        // When & Then
        invalidRequestHelper("{\"projectDetails.references[0].imagePath\":\"must not be blank\"");
    }

    private void invalidRequestHelper(String... messages) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/tattoos/requests/{tattooArtistId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestFormDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String body = result.getResponse().getContentAsString();
        for (String message : messages) {
            assertTrue(body.contains(message));
        }
    }

    @Test
    void postNewTattooRequest_impossibleDate_exceptionHandledAndStatusBadRequest() throws Exception {
        // Given
        String jsonString = objectMapper.writeValueAsString(requestFormDto);
        jsonString = jsonString.replace("2025-05-18", "2025-05-32");
        // When & Then
        invalidDateHelper(jsonString, "{\"error\":\"Text '2025-05-32' could not be parsed: Invalid value for DayOfMonth (valid values 1 - 28/31): 32\"}");
    }
    @Test
    void postNewTattooRequest_invalidDateFormat_exceptionHandledAndStatusBadRequest() throws Exception {
        // Given
        String jsonString = objectMapper.writeValueAsString(requestFormDto);
        jsonString = jsonString.replace("2025-05-18", "invalid-date");
        // When & Then
        invalidDateHelper(jsonString, "{\"error\":\"Text 'invalid-date' could not be parsed at index 0\"}");
    }

    private void invalidDateHelper(String jsonString, String message) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/tattoos/requests/{tattooArtistId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isBadRequest())
                .andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(message, body);
    }

}