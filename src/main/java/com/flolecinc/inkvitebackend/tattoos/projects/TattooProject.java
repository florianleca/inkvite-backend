package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtist;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClient;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tattoo_projects", schema = "public")
@Getter
@Setter
public class TattooProject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "desired_date", nullable = false)
    private LocalDate desiredDate;

    @NotBlank
    @Column(name = "project_description", nullable = false)
    private String projectDescription;

    @NotBlank
    @Column(name = "body_part", nullable = false)
    private String bodyPart;

    @Valid
    @NotEmpty
    @OneToMany(mappedBy = "tattooProject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TattooReference> references;

    @ManyToOne
    @JoinColumn(name = "tattoo_client_id", nullable = false)
    private TattooClient tattooClient;

    @ManyToOne
    @JoinColumn(name = "tattoo_artist_id", nullable = false)
    private TattooArtist tattooArtist;

}
