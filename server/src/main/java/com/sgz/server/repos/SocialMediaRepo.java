package com.sgz.server.repos;

import com.sgz.server.entities.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SocialMediaRepo extends JpaRepository<SocialMedia, UUID> {

}
