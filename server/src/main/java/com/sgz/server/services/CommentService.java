package com.sgz.server.services;

import com.sgz.server.entities.Comment;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment createComment(Comment toAdd) throws InvalidEntityException {
        validateComment(toAdd);

        return commentRepo.save(toAdd);
    }

    public Comment updateComment(Comment toEdit) throws InvalidEntityException, InvalidIdException {
        validateComment(toEdit);
        checkExistsById(toEdit.getId());

        return commentRepo.save(toEdit);
    }

    public void deleteById(UUID id) throws InvalidIdException {
        checkExistsById(id);

        commentRepo.deleteById(id);
    }

    private void validateComment(Comment toUpsert) throws InvalidEntityException {
        if(toUpsert == null || toUpsert.getUser() == null
                || toUpsert.getText() == null
                || toUpsert.getCommentDate() == null
                || toUpsert.getText().trim().length() == 0
                || toUpsert.getText().trim().length() > 255){
            throw new InvalidEntityException("Invalid Entity");
        }
    }

    private void checkExistsById(UUID id) throws InvalidIdException {
        if(!commentRepo.existsById(id)){
            throw new InvalidIdException("Invalid Id");
        }
    }

}
