package com.sgz.server.services;


import com.sgz.server.entities.BaseEntity;
import com.sgz.server.exceptions.InvalidAuthorityException;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.NoItemsException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseService<T extends BaseEntity, E extends JpaRepository<T, UUID>> {

    final E repo;

    BaseService(E repo) {
        this.repo = repo;
    }

    public List<T> getAllItems() throws NoItemsException {
        List<T> allItems = repo.findAll();

        if (allItems.isEmpty()) throw new NoItemsException("No Items");

        return allItems;
    }

    public T getItemById(UUID id) throws InvalidIdException {
        Optional<T> toGet = repo.findById(id);

        if (toGet.isEmpty()) throw new InvalidIdException("Invalid Id");

        return toGet.get();
    }

    public abstract T createItem(T toAdd) throws InvalidEntityException, InvalidAuthorityException;

    public T editItem(T toEdit) throws InvalidEntityException, InvalidIdException {
        validateItem(toEdit);
        checkItemExists(toEdit.getId());

        return repo.save(toEdit);
    }

    public void deleteItemById(UUID id) throws InvalidIdException {
        checkItemExists(id);
        repo.deleteById(id);
    }

    abstract void validateItem(T toUpsert) throws InvalidEntityException;

    void checkItemExists(UUID id) throws InvalidIdException {
        if (!repo.existsById(id)) throw new InvalidIdException("Invalid Id");
    }

}
