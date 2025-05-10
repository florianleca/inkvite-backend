package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtistEntity;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClientEntity;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReferenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tattoo_projects", schema = "public")
@Getter
@Setter
public class TattooProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "desired_date", nullable = false)
    private LocalDate desiredDate;

    @Column(name = "project_description", nullable = false)
    private String projectDescription;

    @Column(name = "body_part", nullable = false)
    private String bodyPart;

    @OneToMany(mappedBy = "tattooProject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TattooReferenceEntity> references;

    @ManyToOne
    @JoinColumn(name = "tattoo_client_id", nullable = false)
    private TattooClientEntity tattooClient;

    @ManyToOne
    @JoinColumn(name = "tattoo_artist_id", nullable = false)
    private TattooArtistEntity tattooArtist;

}
