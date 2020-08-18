package com.sgz.server.services;

import com.sgz.server.entities.SocialMedia;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.InvalidLinkException;
import com.sgz.server.repos.SocialMediaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SocialMediaService {

    private final SocialMediaRepo socialMediaRepo;

    private final String linkRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Autowired
    public SocialMediaService(SocialMediaRepo socialMediaRepo) {
        this.socialMediaRepo = socialMediaRepo;
    }

    public SocialMedia getSocialMediaById(UUID id) throws InvalidIdException {
        Optional<SocialMedia> toGet = socialMediaRepo.findById(id);

        if(toGet.isEmpty()) {
            throw new InvalidIdException("Invalid Id");
        }

        return toGet.get();
    }

    public SocialMedia getSocialMediaByLink(String link) throws InvalidEntityException, InvalidLinkException {
        if(link == null || link.trim().length() == 0
                || link.trim().length() > 255
                || !link.matches(linkRegex)) {
            throw new InvalidEntityException("Invalid Entity");
        }

        Optional<SocialMedia> toGet = socialMediaRepo.findByLink(link);

        if(toGet.isEmpty()) {
            throw new InvalidLinkException("Invalid Link");
        }

        return toGet.get();
    }

    public SocialMedia createSocialMedia(SocialMedia toCreate) throws InvalidEntityException, InvalidLinkException {
        validateSocialMedia(toCreate);
        checkExistsByLink(toCreate.getLink());

        return socialMediaRepo.save(toCreate);
    }

    public SocialMedia updateSocialMedia(SocialMedia toUpdate) throws InvalidEntityException, InvalidIdException {
        validateSocialMedia(toUpdate);
        checkExistsById(toUpdate.getId());

        return socialMediaRepo.save(toUpdate);
    }

    public void deleteSocialMediaById(UUID id) throws InvalidIdException {
        checkExistsById(id);

        socialMediaRepo.deleteById(id);
    }

    private void validateSocialMedia(SocialMedia toUpsert) throws InvalidEntityException {
        if(toUpsert == null || toUpsert.getLink() == null
                || toUpsert.getLink().trim().length() == 0
                || toUpsert.getLink().trim().length() > 255
                || !toUpsert.getLink().matches(linkRegex)){
            throw new InvalidEntityException("Invalid Entity");
        }
    }

    private void checkExistsByLink(String link) throws InvalidLinkException {
        if(socialMediaRepo.existsByLink(link)){
            throw new InvalidLinkException("Invalid Link");
        }
    }

    private void checkExistsById(UUID id) throws InvalidIdException {
        if(!socialMediaRepo.existsById(id)){
            throw new InvalidIdException("Invalid id");
        }
    }
}
