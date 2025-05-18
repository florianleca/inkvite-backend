package com.flolecinc.inkvitebackend.tattoos.references;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tattoo_references", schema = "public")
@Getter
@Setter
public class TattooReferenceEntity {

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
    private TattooProjectEntity tattooProject;

}
