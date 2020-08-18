package com.sgz.server.repos;

import com.sgz.server.entities.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SocialMediaRepo extends JpaRepository<SocialMedia, UUID> {

    boolean existsByLink(String link);

    Optional<SocialMedia> findByLink(String link);

}
