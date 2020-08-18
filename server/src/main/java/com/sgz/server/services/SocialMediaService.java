package com.sgz.server.services;

import com.sgz.server.entities.SocialMedia;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.repos.SocialMediaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SocialMediaService {

    private final SocialMediaRepo socialMediaRepo;

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
}
