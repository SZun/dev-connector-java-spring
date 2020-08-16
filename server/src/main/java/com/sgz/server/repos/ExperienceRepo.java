package com.sgz.server.repos;

import com.sgz.server.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExperienceRepo extends JpaRepository<Experience, UUID> {

}
