package com.sgz.server.services;

import com.google.common.collect.Sets;
import com.sgz.server.entities.Comment;
import com.sgz.server.entities.Role;
import com.sgz.server.entities.User;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.repos.CommentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService toTest;

    @Mock
    private CommentRepo commentRepo;

    private final UUID id = new UUID(36,36);

    private final Set<Role> testRoles = Sets.newHashSet(Sets.newHashSet(new Role(this.id, "USER")));

    private final User testUser = new User(this.id, "@amBam20", "Sam", this.testRoles);

    private final String testLongString = "1ZvBWFVdBu62e6yT87rdELXaLP6KfY2wJ9ZRpw9KmZqzNFICvlNKgkCU28aKRpQb2I85EqAxr6Xb4A1Ct4yNEjTOAXgNyyIBEyTnjOYyN4piLPot1OYtnNftyVXZg6DSxlAGgYzBa5ATYzkSHo2EmIpNyc0NCXvFtPdwP1N30s1Fn63sBaQGdX8sZffYO29yTVtg4LLYRdrrP8aPmL2Pm3c3XySoA7KLLNIi8417yXnjzgdDQErkKiAuoR5REsdL";

    private final Comment expectedComment = new Comment(id, this.testUser, "test comment", LocalDate.of(2020, 8, 15));

    @Test
    void createComment() throws InvalidEntityException {
        when(commentRepo.save(any(Comment.class))).thenReturn(expectedComment);

        Comment fromService = toTest.createComment(expectedComment);

        assertEquals(expectedComment, fromService);
    }

    @Test
    void createCommentNullComment(){
        assertThrows(InvalidEntityException.class, () -> toTest.createComment(null));
    }

    @Test
    void createCommentNullUser(){
        final Comment toCreate = new Comment(id, null, "test comment", LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createComment(toCreate));
    }

    @Test
    void createCommentNullDate(){
        final Comment toCreate = new Comment(id, this.testUser, "test comment", null);
        assertThrows(InvalidEntityException.class, () -> toTest.createComment(toCreate));
    }

    @Test
    void createCommentNullText(){
        final Comment toCreate = new Comment(id, this.testUser, null, LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createComment(toCreate));
    }

    @Test
    void createCommentBlankText(){
        final Comment toCreate = new Comment(id, this.testUser, "   ", LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createComment(toCreate));
    }

    @Test
    void createCommentEmptyText(){
        final Comment toCreate = new Comment(id, this.testUser, "", LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createComment(toCreate));
    }

    @Test
    void createCommentTooLongText(){
        final Comment toCreate = new Comment(id, this.testUser, testLongString, LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createComment(toCreate));
    }

    @Test
    void updateComment() throws InvalidEntityException, InvalidIdException {
        when(commentRepo.save(any(Comment.class))).thenReturn(expectedComment);
        when(commentRepo.existsById(any(UUID.class))).thenReturn(true);

        Comment fromService = toTest.updateComment(expectedComment);

        assertEquals(expectedComment, fromService);
    }

    @Test
    void updateCommentNullComment(){
        assertThrows(InvalidEntityException.class, () -> toTest.updateComment(null));
    }

    @Test
    void updateCommentNullUser(){
        final Comment toEdit = new Comment(id, null, "test comment", LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.updateComment(toEdit));
    }

    @Test
    void updateCommentNullDate(){
        final Comment toEdit = new Comment(id, this.testUser, "test comment", null);;
        assertThrows(InvalidEntityException.class, () -> toTest.updateComment(toEdit));
    }

    @Test
    void updateCommentNullText(){
        final Comment toEdit = new Comment(id, this.testUser, null, LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.updateComment(toEdit));
    }

    @Test
    void updateCommentBlankText(){
        final Comment toEdit = new Comment(id, this.testUser, "  ", LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.updateComment(toEdit));
    }

    @Test
    void updateCommentEmptyText(){
        final Comment toEdit = new Comment(id, this.testUser, "", LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.updateComment(toEdit));
    }

    @Test
    void updateCommentTooLongText(){
        final Comment toEdit = new Comment(id, this.testUser, testLongString, LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.updateComment(toEdit));
    }

    @Test
    void updateCommentInvalidId(){
        assertThrows(InvalidIdException.class, () -> toTest.updateComment(expectedComment));
    }

    @Test
    void deleteById() throws InvalidIdException {
        when(commentRepo.existsById(any(UUID.class))).thenReturn(true);

        toTest.deleteById(id);
    }

    @Test
    void deleteByIdInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.deleteById(id));
    }
}