package com.sgz.server.services;

import com.sgz.server.entities.Skill;
import com.sgz.server.exceptions.InvalidAuthorityException;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidNameException;
import com.sgz.server.repos.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillService extends BaseService<Skill, SkillRepo> {

    @Autowired
    public SkillService(SkillRepo skillRepo) {
        super(skillRepo);
    }

    public Skill getSkillByName(String name) throws InvalidEntityException, InvalidNameException {
        if(name == null || name.trim().length() == 0
                || name.trim().length() > 50){
            throw new InvalidEntityException("Invalid Entity");
        }

        Optional<Skill> toGet = repo.findByName(name);

        if(toGet.isEmpty()){
            throw new InvalidNameException("Invalid Name");
        }

        return toGet.get();
    }

    public Skill createSkill(Skill toAdd) throws InvalidEntityException, InvalidNameException {
        validateItem(toAdd);
        checkExistsByName(toAdd.getName());

        return repo.save(toAdd);
    }

    void validateItem(Skill toUpsert) throws InvalidEntityException {
        if(toUpsert == null || toUpsert.getName().trim().length() == 0
                || toUpsert.getName().trim().length() > 50){
            throw new InvalidEntityException("Invalid Entity");
        }
    }

    private void checkExistsByName(String name) throws InvalidNameException {
        if(repo.existsByName(name)){
            throw new InvalidNameException("Invalid Name");
        }
    }

}
