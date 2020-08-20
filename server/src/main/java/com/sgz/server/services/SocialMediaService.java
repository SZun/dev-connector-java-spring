package com.sgz.server.services;

import com.sgz.server.entities.SocialMedia;
import com.sgz.server.exceptions.InvalidAuthorityException;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.InvalidLinkException;
import com.sgz.server.repos.SocialMediaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SocialMediaService extends BaseService<SocialMedia, SocialMediaRepo> {

    private final String linkRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Autowired
    public SocialMediaService(SocialMediaRepo socialMediaRepo) {
        super(socialMediaRepo);
    }

    public SocialMedia getSocialMediaById(UUID id) throws InvalidIdException {
        Optional<SocialMedia> toGet = repo.findById(id);

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

        Optional<SocialMedia> toGet = repo.findByLink(link);

        if(toGet.isEmpty()) {
            throw new InvalidLinkException("Invalid Link");
        }

        return toGet.get();
    }

    public SocialMedia createSocialMedia(SocialMedia toCreate) throws InvalidEntityException, InvalidLinkException {
        validateItem(toCreate);
        checkExistsByLink(toCreate.getLink());

        return repo.save(toCreate);
    }

    void validateItem(SocialMedia toUpsert) throws InvalidEntityException {
        if(toUpsert == null || toUpsert.getLink() == null
                || toUpsert.getLink().trim().length() == 0
                || toUpsert.getLink().trim().length() > 255
                || !toUpsert.getLink().matches(linkRegex)){
            throw new InvalidEntityException("Invalid Entity");
        }
    }

    private void checkExistsByLink(String link) throws InvalidLinkException {
        if(repo.existsByLink(link)){
            throw new InvalidLinkException("Invalid Link");
        }
    }

}
