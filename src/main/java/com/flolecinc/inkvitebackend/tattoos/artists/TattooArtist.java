package com.flolecinc.inkvitebackend.tattoos.artists;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tattoo_artists", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class TattooArtist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "display_name")
    private String displayName;

    @OneToMany(mappedBy = "tattooArtist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TattooProject> projects;

    public TattooArtist(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

}
