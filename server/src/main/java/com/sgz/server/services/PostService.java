package com.sgz.server.services;

import com.sgz.server.entities.Post;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.NoItemsException;
import com.sgz.server.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Service
public class PostService extends BaseService<Post, PostRepo> {

    @Autowired
    public PostService(PostRepo repo) {
        super(repo);
    }

    public List<Post> getAllPostsByUserId(UUID id) throws NoItemsException {
        List<Post> allPosts = repo.findAllByUser_Id(id);

        if(allPosts.isEmpty()){
            throw new NoItemsException("No Items");
        }

        return allPosts;
    }

    public Post editPost(Post toEdit, UUID authId) throws InvalidEntityException, InvalidIdException, AccessDeniedException {
        checkExistsAndAuth(toEdit.getId(), authId);
        validateItem(toEdit);

        return repo.save(toEdit);
    }

    public void deletePostById(UUID id, UUID authId) throws InvalidIdException, AccessDeniedException {
        checkExistsAndAuth(id, authId);

        repo.deleteById(id);
    }

    void validateItem(Post toUpsert) throws InvalidEntityException {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    private void checkExistsAndAuth(UUID id, UUID authId) throws InvalidIdException, AccessDeniedException {
        checkItemExists(id);

        if(!repo.existsByIdAndUser_Id(id, authId)) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
