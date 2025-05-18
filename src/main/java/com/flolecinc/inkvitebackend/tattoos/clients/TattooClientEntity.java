package com.flolecinc.inkvitebackend.tattoos.clients;

import com.flolecinc.inkvitebackend.tattoos.projects.TattooProjectEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tattoo_clients", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class TattooClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "tattooClient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TattooProjectEntity> projects;

    public TattooClientEntity(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
