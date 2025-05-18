package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistRepository;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientRepository;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectRepository;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceEntity;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class RequestFormIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private RequestFormService requestFormService;

    @Autowired
    private TattooArtistRepository tattooArtistRepository;

    @Autowired
    private TattooClientRepository tattooClientRepository;

    @Autowired
    private TattooProjectRepository tattooProjectRepository;

    @Autowired
    private TattooReferenceRepository tattooReferenceRepository;

    private TattooArtistEntity artist;

    @BeforeEach
    void setUp() {
        // Clean up
        tattooClientRepository.deleteAll();
        tattooProjectRepository.deleteAll();
        tattooReferenceRepository.deleteAll();
        tattooArtistRepository.deleteAll();
        // Save an artist
        TattooArtistEntity newArtist = new TattooArtistEntity("andrea_g", "Andrea G.");
        artist = tattooArtistRepository.save(newArtist);
        // Handle new request form
        RequestFormDto.ReferenceDto referenceDto1 = new RequestFormDto.ReferenceDto(
                "www.example.com/image1.jpg",
                "My 1st comment");
        RequestFormDto.ReferenceDto referenceDto2 = new RequestFormDto.ReferenceDto(
                "wwww.example.com/image2.jpg",
                "My 2nd comment");
        RequestFormDto.ProjectDetailsDto projectDetails = new RequestFormDto.ProjectDetailsDto(
                LocalDate.of(2025, 5, 18),
                "My tattoo description",
                "My body part",
                List.of(referenceDto1, referenceDto2));
        RequestFormDto.IdentityDto identity = new RequestFormDto.IdentityDto(
                "John",
                "Doe",
                "john.doe@aol.com");
        RequestFormDto requestForm = new RequestFormDto(identity, projectDetails);
        requestFormService.handleNewRequestForm(artist.getId(), requestForm);
    }

    @Test
    void handleNewRequestForm_nominal_tattooProjectSaved() {
        assertEquals(1, tattooClientRepository.count());
        TattooClientEntity client = tattooClientRepository.findAll().get(0);
        assertEquals("John", client.getFirstName());
        assertEquals("Doe", client.getLastName());
        assertEquals("john.doe@aol.com", client.getEmail());

        assertEquals(1, tattooProjectRepository.count());
        TattooProjectEntity project = tattooProjectRepository.findAll().get(0);
        assertEquals("My tattoo description", project.getProjectDescription());
        assertEquals("My body part", project.getBodyPart());
        assertEquals(LocalDate.of(2025, 5, 18), project.getDesiredDate());
        assertEquals(artist.getId(), project.getTattooArtist().getId());
        assertEquals(client.getId(), project.getTattooClient().getId());

        assertEquals(2, tattooReferenceRepository.count());
        TattooReferenceEntity reference1 = tattooReferenceRepository.findAll().get(0);
        assertEquals("www.example.com/image1.jpg", reference1.getImagePath());
        assertEquals("My 1st comment", reference1.getComment());
        assertEquals(project.getId(), reference1.getTattooProject().getId());
        TattooReferenceEntity reference2 = tattooReferenceRepository.findAll().get(1);
        assertEquals("wwww.example.com/image2.jpg", reference2.getImagePath());
        assertEquals("My 2nd comment", reference2.getComment());
        assertEquals(project.getId(), reference2.getTattooProject().getId());
    }

    @Test
    void deletingArtistShouldDeleteProjectAndReferences() {
        // When
        tattooArtistRepository.deleteAll();

        // Then
        assertEquals(0, tattooArtistRepository.count());
        assertEquals(1, tattooClientRepository.count());
        assertEquals(0, tattooProjectRepository.count());
        assertEquals(0, tattooReferenceRepository.count());
    }

    @Test
    void deletingClientShouldDeleteProjectAndReferences() {
        // When
        tattooClientRepository.deleteAll();

        // Then
        assertEquals(1, tattooArtistRepository.count());
        assertEquals(0, tattooClientRepository.count());
        assertEquals(0, tattooProjectRepository.count());
        assertEquals(0, tattooReferenceRepository.count());
    }

    @Test
    void deletingProjectShouldDeleteReferences() {
        // When
        tattooProjectRepository.deleteAll();

        // Then
        assertEquals(1, tattooArtistRepository.count());
        assertEquals(1, tattooClientRepository.count());
        assertEquals(0, tattooProjectRepository.count());
        assertEquals(0, tattooReferenceRepository.count());
    }

    // Deleting the tattoo reference should not delete the project
    @Test
    void deletingReferencesShouldNotDeleteProject() {
        // When
        tattooReferenceRepository.deleteAll();

        // Then
        assertEquals(1, tattooArtistRepository.count());
        assertEquals(1, tattooClientRepository.count());
        assertEquals(1, tattooProjectRepository.count());
        assertEquals(0, tattooReferenceRepository.count());
    }

}