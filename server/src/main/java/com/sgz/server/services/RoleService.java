package com.sgz.server.services;

import com.sgz.server.entities.Role;
import com.sgz.server.exceptions.InvalidAuthorityException;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService extends BaseService<Role, RoleRepo> {

    @Autowired
    public RoleService(RoleRepo repo) {
        super(repo);
    }

    @Override
    public Role createItem(Role toAdd) throws InvalidEntityException, InvalidAuthorityException {
        validateItem(toAdd);
        checkExistsByAuthority(toAdd.getAuthority());

        return repo.save(toAdd);
    }

    public Role getRoleByAuthority(String authority) throws InvalidAuthorityException, InvalidEntityException {
        if (authority == null
                || authority.trim().isEmpty()
                || authority.trim().length() > 50) {
            throw new InvalidEntityException("Name is invalid");
        }

        Optional<Role> toGet = repo.findByAuthority(authority);

        if (toGet.isEmpty()) throw new InvalidAuthorityException("Authority not found");

        return toGet.get();
    }

    @Override
    void validateItem(Role toUpsert) throws InvalidEntityException {
        if (toUpsert == null
                || toUpsert.getAuthority().trim().isEmpty()
                || toUpsert.getAuthority().trim().length() > 50) {
            throw new InvalidEntityException("Invalid entity");
        }
    }


    private void checkExistsByAuthority(String authority) throws InvalidAuthorityException {
        if (repo.existsByAuthority(authority)) throw new InvalidAuthorityException("Authority already in use");
    }

}
