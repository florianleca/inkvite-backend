package com.flolecinc.inkvitebackend.tattoos.references;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tattoo_references", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class TattooReference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "tattoo_project_id", nullable = false)
    private TattooProject tattooProject;

}
