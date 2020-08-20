package com.sgz.server.services;

import com.sgz.server.entities.SocialMedia;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.InvalidLinkException;
import com.sgz.server.repos.SocialMediaRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocialMediaServiceTest {

    @InjectMocks
    private SocialMediaService toTest;

    @Mock
    private SocialMediaRepo socialMediaRepo;

    private final UUID id = new UUID(36,36);

    private final SocialMedia expectedSocialMedia = new SocialMedia(id, "https://github.com/SZun/");

    private final String testLongString = "1ZvBWFVdBu62e6yT87rdELXaLP6KfY2wJ9ZRpw9KmZqzNFICvlNKgkCU28aKRpQb2I85EqAxr6Xb4A1Ct4yNEjTOAXgNyyIBEyTnjOYyN4piLPot1OYtnNftyVXZg6DSxlAGgYzBa5ATYzkSHo2EmIpNyc0NCXvFtPdwP1N30s1Fn63sBaQGdX8sZffYO29yTVtg4LLYRdrrP8aPmL2Pm3c3XySoA7KLLNIi8417yXnjzgdDQErkKiAuoR5REsdL";

    @Test
    void getSocialMediaById() throws InvalidIdException {
        when(socialMediaRepo.findById(any(UUID.class))).thenReturn(Optional.of(expectedSocialMedia));

        SocialMedia fromService = toTest.getSocialMediaById(id);

        assertEquals(expectedSocialMedia, fromService);
    }

    @Test
    void getSocialMediaByIdInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.getSocialMediaById(id));
    }

    @Test
    void getSocialMediaByLink() throws InvalidEntityException, InvalidLinkException {
        when(socialMediaRepo.findByLink(anyString())).thenReturn(Optional.of(expectedSocialMedia));

        SocialMedia fromService = toTest.getSocialMediaByLink(expectedSocialMedia.getLink());

        assertEquals(expectedSocialMedia, fromService);
    }

    @Test
    void getSocialMediaByLinkNullLink() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSocialMediaByLink(null));
    }

    @Test
    void getSocialMediaByLinkBlankLink() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSocialMediaByLink("   "));
    }

    @Test
    void getSocialMediaByLinkEmptyLink() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSocialMediaByLink(""));
    }

    @Test
    void getSocialMediaByLinkTooLongLink() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSocialMediaByLink(testLongString));
    }

    @Test
    void getSocialMediaByLinkInvalidPattern() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSocialMediaByLink("this is a link"));
    }

    @Test
    void getSocialMediaByLinkInvalidLink() {
        assertThrows(InvalidLinkException.class, () -> toTest.getSocialMediaByLink(expectedSocialMedia.getLink()));
    }

    @Test
    void createSocialMedia() throws InvalidEntityException, InvalidLinkException {
        when(socialMediaRepo.save(any(SocialMedia.class))).thenReturn(expectedSocialMedia);

        SocialMedia fromService = toTest.createSocialMedia(expectedSocialMedia);

        assertEquals(expectedSocialMedia, fromService);
    }

    @Test
    void createSocialMediaNullSocialMedia() {
        assertThrows(InvalidEntityException.class, () -> toTest.createSocialMedia(null));
    }

    @Test
    void createSocialMediaNullLink() {
        final SocialMedia toCreate = new SocialMedia(id, null);
        assertThrows(InvalidEntityException.class, () -> toTest.createSocialMedia(toCreate));
    }

    @Test
    void createSocialMediaBlankLink() {
        final SocialMedia toCreate = new SocialMedia(id, "  ");
        assertThrows(InvalidEntityException.class, () -> toTest.createSocialMedia(toCreate));
    }

    @Test
    void createSocialMediaEmptyLink() {
        final SocialMedia toCreate = new SocialMedia(id, "");
        assertThrows(InvalidEntityException.class, () -> toTest.createSocialMedia(toCreate));
    }

    @Test
    void createSocialMediaTooLongLink() {
        final SocialMedia toCreate = new SocialMedia(id, testLongString);
        assertThrows(InvalidEntityException.class, () -> toTest.createSocialMedia(toCreate));
    }

    @Test
    void createSocialMediaInvalidLinkPattern() {
        final SocialMedia toCreate = new SocialMedia(id, "this is a link");
        assertThrows(InvalidEntityException.class, () -> toTest.createSocialMedia(toCreate));
    }

    @Test
    void createSocialMediaInvalidLink() {
        when(socialMediaRepo.existsByLink(anyString())).thenReturn(true);
        assertThrows(InvalidLinkException.class, () -> toTest.createSocialMedia(expectedSocialMedia));
    }

    @Test
    void editItem() throws InvalidEntityException, InvalidIdException {
        when(socialMediaRepo.save(any(SocialMedia.class))).thenReturn(expectedSocialMedia);
        when(socialMediaRepo.existsById(any(UUID.class))).thenReturn(true);

        SocialMedia fromService = toTest.editItem(expectedSocialMedia);

        assertEquals(expectedSocialMedia, fromService);
    }

    @Test
    void editItemNullSocialMedia() {
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(null));
    }

    @Test
    void editItemNullLink() {
        final SocialMedia toEdit = new SocialMedia(id, null);
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemBlankLink() {
        final SocialMedia toEdit = new SocialMedia(id, "  ");
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemEmptyLink() {
        final SocialMedia toEdit = new SocialMedia(id, "");
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemTooLongLink() {
        final SocialMedia toEdit = new SocialMedia(id, testLongString);
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemInvalidLinkPattern() {
        final SocialMedia toEdit = new SocialMedia(id, "this is a link");
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.editItem(expectedSocialMedia));
    }

    @Test
    void deleteItemById() throws InvalidIdException {
        when(socialMediaRepo.existsById(any(UUID.class))).thenReturn(true);

        toTest.deleteItemById(id);
    }

    @Test
    void deleteItemByIdInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.deleteItemById(id));
    }
}