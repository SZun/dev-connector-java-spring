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
    void createItem() throws InvalidEntityException {
        when(commentRepo.save(any(Comment.class))).thenReturn(expectedComment);

        Comment fromService = toTest.createItem(expectedComment);

        assertEquals(expectedComment, fromService);
    }

    @Test
    void createItemNullComment(){
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(null));
    }

    @Test
    void createItemNullUser(){
        final Comment toCreate = new Comment(id, null, "test comment", LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void createItemNullDate(){
        final Comment toCreate = new Comment(id, this.testUser, "test comment", null);
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void createItemNullText(){
        final Comment toCreate = new Comment(id, this.testUser, null, LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void createItemBlankText(){
        final Comment toCreate = new Comment(id, this.testUser, "   ", LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void createItemEmptyText(){
        final Comment toCreate = new Comment(id, this.testUser, "", LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void createItemTooLongText(){
        final Comment toCreate = new Comment(id, this.testUser, testLongString, LocalDate.of(2020, 8, 15));
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void editItem() throws InvalidEntityException, InvalidIdException {
        when(commentRepo.save(any(Comment.class))).thenReturn(expectedComment);
        when(commentRepo.existsById(any(UUID.class))).thenReturn(true);

        Comment fromService = toTest.editItem(expectedComment);

        assertEquals(expectedComment, fromService);
    }

    @Test
    void editItemNullComment(){
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(null));
    }

    @Test
    void editItemNullUser(){
        final Comment toEdit = new Comment(id, null, "test comment", LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemNullDate(){
        final Comment toEdit = new Comment(id, this.testUser, "test comment", null);;
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemNullText(){
        final Comment toEdit = new Comment(id, this.testUser, null, LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemBlankText(){
        final Comment toEdit = new Comment(id, this.testUser, "  ", LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemEmptyText(){
        final Comment toEdit = new Comment(id, this.testUser, "", LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemTooLongText(){
        final Comment toEdit = new Comment(id, this.testUser, testLongString, LocalDate.of(2020, 8, 15));;
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemInvalidId(){
        assertThrows(InvalidIdException.class, () -> toTest.editItem(expectedComment));
    }

    @Test
    void deleteItemById() throws InvalidIdException {
        when(commentRepo.existsById(any(UUID.class))).thenReturn(true);

        toTest.deleteItemById(id);
    }

    @Test
    void deleteItemByIdInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.deleteItemById(id));
    }
}