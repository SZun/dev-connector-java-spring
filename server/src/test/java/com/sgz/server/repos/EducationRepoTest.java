package com.sgz.server.repos;

import com.sgz.server.entities.Education;
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
class EducationRepoTest {

    @Mock
    private EducationRepo toTest;

    private final UUID id = new UUID(36, 36);

    private final Education expectedEducation = new Education(id, LocalDate.of(2020, 8, 15), null, true, "Studied Comp Sci at Northwestern", "Northwestern University", "Bachelors", "Computer Science");


    @Test
    void save() {
        given(toTest.save(any(Education.class))).willReturn(expectedEducation);

        ArgumentCaptor<Education> captor = ArgumentCaptor.forClass(Education.class);

        Education fromRepo = toTest.save(expectedEducation);

        verify(toTest).save(captor.capture());

        Education expectedParam = captor.getValue();
        assertEquals(expectedParam.getId(), id);
        assertEquals(expectedParam.getSchool(), "Northwestern University");
        assertEquals(expectedParam.getDegree(), "Bachelors");
        assertEquals(expectedParam.getFieldOfStudy(), "Computer Science");
        assertEquals(expectedParam.getFromDate(), LocalDate.of(2020, 8, 15));
        assertNull(expectedParam.getToDate());
        assertTrue(expectedParam.isCurrent());
        assertEquals(expectedParam.getDescription(), "Studied Comp Sci at Northwestern");

        assertEquals(expectedEducation, fromRepo);
    }

    @Test
    void findById() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.of(expectedEducation));

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Education> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedEducation, fromRepo.get());
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

        Optional<Education> fromRepo = toTest.findById(id);

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
        final List<Education> expectedEducations = Arrays.asList(expectedEducation);

        given(toTest.findAll()).willReturn(expectedEducations);

        List<Education> fromRepo = toTest.findAll();

        verify(toTest).findAll();

        assertEquals(expectedEducations, fromRepo);
    }

}