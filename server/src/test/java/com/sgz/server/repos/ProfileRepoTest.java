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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProfileRepoTest {

    @Mock
    private ProfileRepo toTest;

    private final UUID id = new UUID(36, 36);

    private final Set<Role> testRoles = Sets.newHashSet(Sets.newHashSet(new Role(this.id, "USER")));

    private final User testUser = new User(this.id, "@amBam20", "Sam", this.testRoles);

    private final Skill testSkill = new Skill(this.id, "Java 11");

    private final SocialMedia testSocialMedia = new SocialMedia(this.id, "https://www.facebook.com/zuck");

    private final Experience testExperience = new Experience(id, "CEO", "Google", "California", LocalDate.of(2020, 8, 15), null, true, "CEO of Google");

    private final Education testEducation = new Education(id, "Northwestern University", "Bachelors", "Computer Science", LocalDate.of(2020, 8, 15), null, true, "Studied Comp Sci at Northwestern");

    private final Profile expectedProfile = new Profile(id, this.testUser, "Zun", "Apple", null, null, null, "SZun", Sets.newHashSet(this.testSkill), Sets.newHashSet(this.testEducation), Sets.newHashSet(this.testExperience), Sets.newHashSet(this.testSocialMedia));

    @Test
    void save() {
        given(toTest.save(any(Profile.class))).willReturn(expectedProfile);

        ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);

        Profile fromRepo = toTest.save(expectedProfile);

        verify(toTest).save(captor.capture());

        Profile expectedParam = captor.getValue();
        assertEquals(expectedParam.getId(), id);
        assertEquals(expectedParam.getUser(), testUser);
        assertEquals(expectedParam.getCompany(), "Apple");
        assertNull(expectedParam.getWebsite());
        assertNull(expectedParam.getLocation());
        assertNull(expectedParam.getDescription());
        assertEquals(expectedParam.getGithubUsername(), "SZun");
        assertEquals(expectedParam.getSkills(), Sets.newHashSet(this.testSkill));
        assertEquals(expectedParam.getEducations(), Sets.newHashSet(this.testEducation));
        assertEquals(expectedParam.getExperiences(), Sets.newHashSet(this.testExperience));
        assertEquals(expectedParam.getSocialMedias(), Sets.newHashSet(this.testSocialMedia));

        assertEquals(expectedProfile, fromRepo);
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
    void existsByHandle() {
        given(toTest.existsByHandle(anyString())).willReturn(true);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        boolean fromRepo = toTest.existsByHandle("SZun");

        verify(toTest).existsByHandle(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals(expectedParam, "SZun");
        assertTrue(fromRepo);
    }

    @Test
    void findById() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.of(expectedProfile));

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Profile> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedProfile, fromRepo.get());
    }

    @Test
    void findByIdEmpty() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.empty());

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<Profile> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isEmpty());
    }

    @Test
    void findByHandle() {
        given(toTest.findByHandle(anyString())).willReturn(Optional.of(expectedProfile));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Optional<Profile> fromRepo = toTest.findByHandle("SZun");

        verify(toTest).findByHandle(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals(expectedParam, "SZun");
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedProfile, fromRepo.get());
    }

    @Test
    void findByIdHandleEmpty() {
        given(toTest.findByHandle(anyString())).willReturn(Optional.empty());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Optional<Profile> fromRepo = toTest.findByHandle("SZun");

        verify(toTest).findByHandle(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals(expectedParam, "SZun");
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
        final List<Profile> expectedProfiles = Arrays.asList(this.expectedProfile);

        given(toTest.findAll()).willReturn(expectedProfiles);

        List<Profile> fromRepo = toTest.findAll();

        verify(toTest).findAll();

        assertEquals(expectedProfiles, fromRepo);
    }

}