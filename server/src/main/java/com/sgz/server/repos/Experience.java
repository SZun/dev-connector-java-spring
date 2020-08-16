package com.sgz.server.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Experience extends JpaRepository<Experience, UUID> {

}
