package com.flolecinc.inkvitebackend.tattoos.references;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProject;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tattoo_references", schema = "public")
@Data
@NoArgsConstructor
public class TattooReference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "tattoo_project_id", nullable = false)
    private TattooProject tattooProject;


    public TattooReference(@Valid @NotNull TattooReferenceDTO tattooReferenceDTO) {
        this.imagePath = tattooReferenceDTO.getImagePath();
        this.comment = tattooReferenceDTO.getComment();
    }

}
