package com.sgz.server.repos;

import com.sgz.server.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EducationRepo extends JpaRepository<Education, UUID> {

}
