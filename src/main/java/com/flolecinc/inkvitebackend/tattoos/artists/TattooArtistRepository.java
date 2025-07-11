package com.flolecinc.inkvitebackend.tattoos.artists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TattooArtistRepository extends JpaRepository<TattooArtist, UUID> {

    Optional<TattooArtist> findByUsername(String username);

}
