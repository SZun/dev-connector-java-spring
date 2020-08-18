package com.sgz.server.repos;

import com.sgz.server.entities.Skill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SkillRepoTest {

    @Mock
    private SkillRepo toTest;

    private final UUID id = new UUID(36,36);

    private final Skill expectedSkill = new Skill(id, "Java 11");


    @Test
    void save() {
        given(toTest.save(any(Skill.class))).willReturn(expectedSkill);

        ArgumentCaptor<Skill> captor = ArgumentCaptor.forClass(Skill.class);

        Skill fromRepo = toTest.save(expectedSkill);

        verify(toTest).save(captor.capture());

        Skill expectedParam = captor.getValue();
        assertEquals(id, expectedParam.getId());
        assertEquals(expectedSkill.getName(), expectedParam.getName());

        assertEquals(expectedSkill, fromRepo);
    }

    @Test
    void findByName() {
        given(toTest.findByName(anyString())).willReturn(Optional.of(expectedSkill));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Optional<Skill> fromRepo = toTest.findByName("Java 11");

        verify(toTest).findByName(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals("Java 11", expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedSkill, fromRepo.get());
    }

    @Test
    void findByNameEmpty() {
        given(toTest.findByName(anyString())).willReturn(Optional.empty());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Optional<Skill> fromRepo = toTest.findByName("Java 11");

        verify(toTest).findByName(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals("Java 11", expectedParam);
        assertTrue(fromRepo.isEmpty());
    }

    @Test
    void findById() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.of(expectedSkill));

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Skill> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedSkill, fromRepo.get());
    }

    @Test
    void findByIdEmpty() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.empty());

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Skill> fromRepo = toTest.findById(id);

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
        final List<Skill> expectedSkills = Arrays.asList(expectedSkill);

        given(toTest.findAll()).willReturn(expectedSkills);

        List<Skill> fromRepo = toTest.findAll();

        verify(toTest).findAll();

        assertEquals(expectedSkills, fromRepo);
    }

    @Test
    void existsByUsername() {
        given(toTest.existsByName(anyString())).willReturn(true);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        boolean fromRepo = toTest.existsByName("Java 11");

        verify(toTest).existsByName(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals("Java 11", expectedParam);
        assertTrue(fromRepo);
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

}