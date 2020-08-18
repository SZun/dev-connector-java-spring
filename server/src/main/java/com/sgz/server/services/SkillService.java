package com.sgz.server.services;

import com.sgz.server.entities.Skill;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.InvalidNameException;
import com.sgz.server.exceptions.NoItemsException;
import com.sgz.server.repos.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SkillService {

    private final SkillRepo skillRepo;

    @Autowired
    public SkillService(SkillRepo skillRepo) {
        this.skillRepo = skillRepo;
    }

    public List<Skill> getAllSkills() throws NoItemsException {
        List<Skill> allSkills = skillRepo.findAll();

        if(allSkills.isEmpty()){
            throw new NoItemsException("No Items");
        }

        return allSkills;
    }

    public Skill getSkillById(UUID id) throws InvalidIdException {
        Optional<Skill> toGet = skillRepo.findById(id);

        if(!toGet.isPresent()){
            throw new InvalidIdException("Invalid Id");
        }

        return toGet.get();
    }

    public Skill getSkillByName(String name) throws InvalidEntityException, InvalidNameException {
        if(name.trim().length() == 0 || name.trim().length() > 50){
            throw new InvalidEntityException("Invalid Entity");
        }

        Optional<Skill> toGet = skillRepo.findByName(name);

        if(!toGet.isPresent()){
            throw new InvalidNameException("Invalid Name");
        }

        return toGet.get();
    }

    public Skill createSkill(Skill toAdd) throws InvalidEntityException, InvalidNameException {
        validateSkill(toAdd);
        checkExistsByName(toAdd.getName());

        return skillRepo.save(toAdd);
    }

    public Skill updateSkill(Skill toEdit) throws InvalidEntityException, InvalidIdException {
        validateSkill(toEdit);
        checkExistsById(toEdit.getId());

        return skillRepo.save(toEdit);
    }

    public void deleteById(UUID id) throws InvalidIdException {
        checkExistsById(id);

        skillRepo.deleteById(id);
    }

    private void validateSkill(Skill toUpsert) throws InvalidEntityException {
        if(toUpsert == null || toUpsert.getName().trim().length() == 0
                || toUpsert.getName().trim().length() > 50){
            throw new InvalidEntityException("Invalid Entity");
        }
    }

    private void checkExistsByName(String name) throws InvalidNameException {
        if(skillRepo.existsByName(name)){
            throw new InvalidNameException("Invalid Name");
        }
    }

    private void checkExistsById(UUID id) throws InvalidIdException {
        if(!skillRepo.existsById(id)){
            throw new InvalidIdException("Invalid Id");
        }
    }

}
