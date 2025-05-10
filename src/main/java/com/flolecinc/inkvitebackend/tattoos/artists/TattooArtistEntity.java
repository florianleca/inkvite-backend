package com.flolecinc.inkvitebackend.tattoos.artists;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tattoo_artists", schema = "public")
@Getter
@Setter
public class TattooArtistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "display_name")
    private String displayName;

    @OneToMany(mappedBy = "tattooArtist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TattooProjectEntity> projects;

}
