package com.flolecinc.inkvitebackend.tattoos.clients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TattooClientRepository extends JpaRepository<TattooClientEntity, UUID> {
}
