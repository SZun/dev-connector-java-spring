package com.sgz.server.repos;

import com.google.common.collect.Sets;
import com.sgz.server.entities.*;
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
class PostRepoTest {

    @Mock
    private PostRepo toTest;

    private final UUID id = new UUID(36,36);

    private final Set<Role> testRoles = Sets.newHashSet(Sets.newHashSet(new Role(this.id, "USER")));

    private final User testUser = new User(this.id, "@amBam20", "Sam", this.testRoles);

    private final Comment testComment = new Comment(id, this.testUser, "test comment", LocalDate.of(2020, 8, 15));

    private final Post expectedPost = new Post(id, this.testUser, "Test Post", "Test Title", LocalDate.of(2020, 8, 15), Arrays.asList(this.testComment), Arrays.asList(this.testUser));


    @Test
    void save() {
        given(toTest.save(any(Post.class))).willReturn(expectedPost);

        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);

        Post fromRepo = toTest.save(expectedPost);

        verify(toTest).save(captor.capture());

        Post expectedParam = captor.getValue();
        assertEquals(expectedParam.getId(), id);
        assertEquals(expectedParam.getUser(), testUser);
        assertEquals(expectedParam.getText(), "Test Post");
        assertEquals(expectedParam.getTitle(), "Test Title");
        assertEquals(expectedParam.getPostDate(), LocalDate.of(2020, 8, 15));
        assertEquals(expectedParam.getComments(),Arrays.asList(this.testComment));
        assertEquals(expectedParam.getLikes(), Arrays.asList(this.testUser));

        assertEquals(expectedPost, fromRepo);
    }

    @Test
    void findById() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.of(expectedPost));

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Post> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedPost, fromRepo.get());
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

        Optional<Post> fromRepo = toTest.findById(id);

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
        final List<Post> expectedPosts = Arrays.asList(expectedPost);

        given(toTest.findAll()).willReturn(expectedPosts);

        List<Post> fromRepo = toTest.findAll();

        verify(toTest).findAll();

        assertEquals(expectedPosts, fromRepo);
    }

    @Test
    void findAllByUser_Id() {
        final List<Post> expectedPosts = Arrays.asList(expectedPost);

        given(toTest.findAllByUser_Id(any(UUID.class))).willReturn(expectedPosts);

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        List<Post> fromRepo = toTest.findAllByUser_Id(id);

        verify(toTest).findAllByUser_Id(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertEquals(expectedPosts, fromRepo);
    }

}