package com.sgz.server.repos;

import com.google.common.collect.Sets;
import com.sgz.server.entities.Comment;
import com.sgz.server.entities.Role;
import com.sgz.server.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentRepoTest {

    @Mock
    private CommentRepo toTest;

    private final UUID id = new UUID(36, 36);

    private final Set<Role> testRoles = Sets.newHashSet(Sets.newHashSet(new Role(this.id, "USER")));

    private final User testUser = new User(this.id, "@amBam20", "Sam", this.testRoles);

    private final Comment expectedComment = new Comment(id, this.testUser, "test comment", LocalDate.of(2020, 8, 15));

    @Test
    void save() {
        given(toTest.save(any(Comment.class))).willReturn(expectedComment);

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);

        Comment fromRepo = toTest.save(expectedComment);

        verify(toTest).save(captor.capture());

        Comment expectedParam = captor.getValue();
        assertEquals(expectedParam.getId(), id);
        assertEquals(expectedParam.getUser(), testUser);
        assertEquals(expectedParam.getText(), "test comment");
        assertEquals(expectedParam.getCommentDate(), LocalDate.of(2020, 8, 15));

        assertEquals(expectedComment, fromRepo);
    }

    @Test
    void findById() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.of(expectedComment));

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Comment> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedComment, fromRepo.get());
    }

    @Test
    void existsById() {
        given(toTest.existsById(any(UUID.class))).willReturn(true);

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        boolean fromRepo = toTest.existsById(id);

        verify(toTest).existsById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo);
    }


    @Test
    void findByIdEmpty() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.empty());

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Comment> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isEmpty());
    }

    @Test
    void deleteById() {
        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        toTest.deleteById(id);

        verify(toTest).deleteById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
    }

    @Test
    void findAll() {
        final List<Comment> expectedComments = Arrays.asList(expectedComment);

        given(toTest.findAll()).willReturn(expectedComments);

        List<Comment> fromRepo = toTest.findAll();

        verify(toTest).findAll();

        assertEquals(expectedComments, fromRepo);
    }

}