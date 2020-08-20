package com.sgz.server.services;

import com.sgz.server.entities.Comment;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseService<Comment, CommentRepo> {

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        super(commentRepo);
    }

    void validateItem(Comment toUpsert) throws InvalidEntityException {
        if(toUpsert == null || toUpsert.getUser() == null
                || toUpsert.getText() == null
                || toUpsert.getCommentDate() == null
                || toUpsert.getText().trim().length() == 0
                || toUpsert.getText().trim().length() > 255){
            throw new InvalidEntityException("Invalid Entity");
        }
    }

}
