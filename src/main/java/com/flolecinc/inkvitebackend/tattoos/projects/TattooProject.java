package com.flolecinc.inkvitebackend.tattoos.projects;

import com.flolecinc.inkvitebackend.tattoos.artists.TattooArtist;
import com.flolecinc.inkvitebackend.tattoos.clients.TattooClient;
import com.flolecinc.inkvitebackend.tattoos.references.TattooReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tattoo_projects", schema = "public")
@Data
@NoArgsConstructor
public class TattooProject {

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
    private List<TattooReference> references;

    @ManyToOne
    @JoinColumn(name = "tattoo_client_id", nullable = false)
    private TattooClient tattooClient;

    @ManyToOne
    @JoinColumn(name = "tattoo_artist_id", nullable = false)
    private TattooArtist tattooArtist;

    public TattooProject(@Valid @NotNull TattooProjectDTO projectDetails) {
        this.desiredDate = projectDetails.getDesiredDate();
        this.projectDescription = projectDetails.getProjectDescription();
        this.bodyPart = projectDetails.getBodyPart();
    }

}
