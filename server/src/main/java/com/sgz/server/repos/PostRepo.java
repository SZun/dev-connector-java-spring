package com.sgz.server.repos;

import com.sgz.server.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {

    List<Post> findAllByUser_Id(UUID id);

    boolean existsByIdAndUser_Id(UUID id, UUID userId);

}
