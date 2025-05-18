package com.flolecinc.inkvitebackend.tattoos.requestforms;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistRepository;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientRepository;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectRepository;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceEntity;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceRepository;
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

    @Test
    void handleNewRequestForm_nominal_tattooProjectSaved() {
        // Given
        TattooArtistEntity artist = new TattooArtistEntity();
        artist.setUsername("andrea_g");
        artist.setDisplayName("Andrea G.");
        artist = tattooArtistRepository.save(artist);

        RequestFormDto.ReferenceDto referenceDto1 = new RequestFormDto.ReferenceDto(
                "www.example.com/image1.jpg",
                "My 1st comment");
        RequestFormDto.ReferenceDto referenceDto2 = new RequestFormDto.ReferenceDto(
                "wwww.example.com/image2.jpg",
                "My 2nd comment");
        LocalDate desiredDate = LocalDate.now();
        RequestFormDto.ProjectDetailsDto projectDetails = new RequestFormDto.ProjectDetailsDto(
                desiredDate,
                "My tattoo description",
                "My body part",
                List.of(referenceDto1, referenceDto2));
        RequestFormDto.IdentityDto identity = new RequestFormDto.IdentityDto(
                "John",
                "Doe",
                "john.doe@aol.com");
        RequestFormDto requestForm = new RequestFormDto(identity, projectDetails);

        // When
        requestFormService.handleNewRequestForm(artist.getId(), requestForm);

        assertEquals(1, tattooClientRepository.count());
        TattooClientEntity client = tattooClientRepository.findAll().get(0);
        assertEquals("John", client.getFirstName());
        assertEquals("Doe", client.getLastName());
        assertEquals("john.doe@aol.com", client.getEmail());

        assertEquals(1, tattooProjectRepository.count());
        TattooProjectEntity project = tattooProjectRepository.findAll().get(0);
        assertEquals("My tattoo description", project.getProjectDescription());
        assertEquals("My body part", project.getBodyPart());
        assertEquals(desiredDate, project.getDesiredDate());
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

}