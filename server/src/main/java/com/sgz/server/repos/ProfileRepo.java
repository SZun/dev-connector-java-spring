package com.sgz.server.repos;

import com.sgz.server.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, UUID> {

    Optional<Profile> getByHandle(String handle);

    boolean existsByHandle(String handle);

}
