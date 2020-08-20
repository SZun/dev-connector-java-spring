package com.sgz.server.services;

import com.sgz.server.entities.User;
import com.sgz.server.exceptions.InvalidAuthorityException;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.InvalidNameException;
import com.sgz.server.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseService<User, UserRepo> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo repo, PasswordEncoder passwordEncoder) {
        super(repo);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createItem(User toAdd) throws InvalidEntityException, InvalidAuthorityException {
        throw new UnsupportedOperationException("Unsupported");
    }

    public User createUser(User toAdd) throws InvalidEntityException, InvalidNameException {
        validateItem(toAdd);
        checkExistsByUsername(toAdd.getUsername());

        toAdd.setPassword(passwordEncoder.encode(toAdd.getPassword()));

        return repo.save(toAdd);
    }

    public User getUserByName(String username) throws InvalidEntityException, InvalidNameException {
        if (username == null
                || username.trim().isEmpty()
                || username.trim().length() > 50) {
            throw new InvalidEntityException("Name is invalid");
        }

        Optional<User> toGet = repo.findByUsername(username);
        if (toGet.isEmpty()) {
            throw new InvalidNameException("Name not found");
        }

        return toGet.get();
    }

    public User editUser(User toEdit, UUID authId) throws InvalidEntityException, InvalidIdException, AccessDeniedException {
        validateItem(toEdit);
        checkAuthorization(toEdit.getId(), authId);
        checkItemExists(toEdit.getId());

        toEdit.setPassword(passwordEncoder.encode(toEdit.getPassword()));

        return repo.save(toEdit);
    }

    public void deleteUserById(UUID writeId, UUID authId) throws InvalidIdException, AccessDeniedException {
        checkAuthorization(writeId, authId);
        checkItemExists(writeId);
        repo.deleteById(writeId);
    }

    private void checkExistsByUsername(String username) throws InvalidNameException {
        if (repo.existsByUsername(username)) throw new InvalidNameException("Name already exists");
    }

    private void checkAuthorization(UUID writeId, UUID authId) throws AccessDeniedException {
        if (!writeId.equals(authId)) throw new AccessDeniedException("Access Denied");
    }

    @Override
    void validateItem(User toUpsert) throws InvalidEntityException {
        if (toUpsert == null
                || toUpsert.getUsername().trim().isEmpty()
                || toUpsert.getUsername().trim().length() > 50
                || toUpsert.getPassword().trim().isEmpty()
                || toUpsert.getPassword().trim().length() > 20
                || !toUpsert.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
                || toUpsert.getRoles() == null
                || toUpsert.getRoles().isEmpty()
        ) {
            throw new InvalidEntityException("Invalid Entity");
        }
    }

}
