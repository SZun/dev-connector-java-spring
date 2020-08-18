package com.sgz.server.repos;

import com.sgz.server.entities.SocialMedia;
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
class SocialMediaRepoTest {

    @Mock
    private SocialMediaRepo toTest;

    private final UUID id = new UUID(36,36);

    private final SocialMedia expectedSocialMedia = new SocialMedia(id, "https://github.com/SZun/");


    @Test
    void save() {
        given(toTest.save(any(SocialMedia.class))).willReturn(expectedSocialMedia);

        ArgumentCaptor<SocialMedia> captor = ArgumentCaptor.forClass(SocialMedia.class);

        SocialMedia fromRepo = toTest.save(expectedSocialMedia);

        verify(toTest).save(captor.capture());

        SocialMedia expectedParam = captor.getValue();
        assertEquals(id, expectedParam.getId());
        assertEquals("https://github.com/SZun/", expectedParam.getLink());

        assertEquals(expectedSocialMedia, fromRepo);
    }

    @Test
    void findByLink() {
        given(toTest.findByLink(anyString())).willReturn(Optional.of(expectedSocialMedia));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Optional<SocialMedia> fromRepo = toTest.findByLink("https://github.com/SZun/");

        verify(toTest).findByLink(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals("https://github.com/SZun/", expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedSocialMedia, fromRepo.get());
    }

    @Test
    void findByLinkEmpty() {
        given(toTest.findByLink(anyString())).willReturn(Optional.empty());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Optional<SocialMedia> fromRepo = toTest.findByLink("https://github.com/SZun/");

        verify(toTest).findByLink(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals("https://github.com/SZun/", expectedParam);
        assertTrue(fromRepo.isEmpty());
    }

    @Test
    void findById() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.of(expectedSocialMedia));

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<SocialMedia> fromRepo = toTest.findById(id);

        verify(toTest).findById(captor.capture());

        UUID expectedParam = captor.getValue();

        assertEquals(id, expectedParam);
        assertTrue(fromRepo.isPresent());
        assertEquals(expectedSocialMedia, fromRepo.get());
    }

    @Test
    void findByIdEmpty() {
        given(toTest.findById(any(UUID.class))).willReturn(Optional.empty());

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

        Optional<SocialMedia> fromRepo = toTest.findById(id);

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
        final List<SocialMedia> expectedSocialMedias = Arrays.asList(expectedSocialMedia);

        given(toTest.findAll()).willReturn(expectedSocialMedias);

        List<SocialMedia> fromRepo = toTest.findAll();

        verify(toTest).findAll();

        assertEquals(expectedSocialMedias, fromRepo);
    }

    @Test
    void existsByUsername() {
        given(toTest.existsByLink(anyString())).willReturn(true);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        boolean fromRepo = toTest.existsByLink("https://github.com/SZun/");

        verify(toTest).existsByLink(captor.capture());

        String expectedParam = captor.getValue();

        assertEquals("https://github.com/SZun/", expectedParam);
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