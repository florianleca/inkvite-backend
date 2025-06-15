package com.flolecinc.inkvitebackend.tattoos.references;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TattooReferenceRepository extends JpaRepository<TattooReference, UUID> {
}
