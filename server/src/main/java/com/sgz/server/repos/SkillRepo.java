package com.sgz.server.repos;

import com.sgz.server.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SkillRepo extends JpaRepository<Skill, UUID> {

}
