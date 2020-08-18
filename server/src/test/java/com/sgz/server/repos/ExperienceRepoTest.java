package com.sgz.server.repos;

import com.sgz.server.entities.Experience;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExperienceRepoTest {

    @Mock
    private ExperienceRepo toTest;

    private final UUID id = new UUID(36, 36);

    private final Experience expectedExperience = new Experience(id, "CEO", "Google", "California", LocalDate.of(2020,8,15), null, true, "CEO of Google");

    @Test
    void save() {
        given(toTest.save(any(Experience.class))).willReturn(expectedExperience);

        ArgumentCaptor<Experience> captor = ArgumentCaptor.forClass(Experience.class);

        Experience fromRepo = toTest.save(expectedExperience);

        verify(toTest).save(captor.capture());

        Experience expectedParam = captor.getValue();
        assertEquals(expectedParam.getId(), id);
        assertEquals(expectedParam.getTitle(), "CEO");
        assertEquals(expectedParam.getCompany(), "Google");
        assertEquals(expectedParam.getLocation(), "California");
        assertEquals(expectedParam.getFromDate(), LocalDate.of(2020, 8, 15));
        assertNull(expectedParam.getToDate());
        assertTrue(expectedParam.isCurrent());
        assertEquals(expectedParam.getDescription(), "CEO of Google");

        assertEquals(expectedExperience, fromRepo);
    }

    @Test
    void findById() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.of(expectedExperience));

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Experience> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedExperience, fromRepo.get());
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

        Optional<Experience> fromRepo = toTest.findById(id);

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
        final List<Experience> expectedExperiences = Arrays.asList(this.expectedExperience);

        given(toTest.findAll()).willReturn(expectedExperiences);

        List<Experience> fromRepo = toTest.findAll();

        verify(toTest).findAll();

        assertEquals(expectedExperiences, fromRepo);
    }

}