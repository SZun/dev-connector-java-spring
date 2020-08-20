package com.sgz.server.services;

import com.sgz.server.entities.Role;
import com.sgz.server.exceptions.InvalidAuthorityException;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.NoItemsException;
import com.sgz.server.repos.RoleRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService toTest;

    @Mock
    private RoleRepo roleRepo;

    private final UUID id = new UUID(36, 36);

    private final Role testRole = new Role(id, "USER");

    private final Role expectedRole = new Role(id, "USER");

    private final String testLongString = "1ZvBWFVdBu62e6yT87rdELXaLP6KfY2wJ9ZRpw9KmZqzNFICvlNKgkCU28aKRpQb2I85EqAxr6Xb4A1Ct4yNEjTOAXgNyyIBEyTnjOYyN4piLPot1OYtnNftyVXZg6DSxlAGgYzBa5ATYzkSHo2EmIpNyc0NCXvFtPdwP1N30s1Fn63sBaQGdX8sZffYO29yTVtg4LLYRdrrP8aPmL2Pm3c3XySoA7KLLNIi8417yXnjzgdDQErkKiAuoR5REsdL";

    @Test
    void getAllItems() throws NoItemsException {
        final Role expected2 = new Role(id, "ADMIN");
        final Role expected3 = new Role(id, "GUEST");

        when(roleRepo.findAll()).thenReturn(Arrays.asList(expectedRole, expected2, expected3));

        List<Role> fromService = toTest.getAllItems();

        assertEquals(3, fromService.size());
        assertTrue(fromService.contains(expectedRole));
        assertTrue(fromService.contains(expected2));
        assertTrue(fromService.contains(expected3));
    }

    @Test
    void getAllItemsNoItems() {
        assertThrows(NoItemsException.class, () -> toTest.getAllItems());
    }

    @Test
    void getItemById() throws InvalidIdException {
        when(roleRepo.findById(any(UUID.class))).thenReturn(Optional.of(expectedRole));

        Role fromService = toTest.getItemById(id);

        assertEquals(expectedRole, fromService);
    }

    @Test
    void getItemByIdInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.getItemById(id));
    }

    @Test
    void getRoleByAuthority() throws InvalidAuthorityException, InvalidEntityException {
        when(roleRepo.findByAuthority(anyString())).thenReturn(Optional.of(expectedRole));

        Role fromService = toTest.getRoleByAuthority("USER");

        assertEquals(expectedRole, fromService);
    }

    @Test
    void getRoleByAuthorityNullAuthority() {
        assertThrows(InvalidEntityException.class, () -> toTest.getRoleByAuthority(null));
    }

    @Test
    void getRoleByAuthorityBlankAuthority() {
        assertThrows(InvalidEntityException.class, () -> toTest.getRoleByAuthority("  "));
    }

    @Test
    void getRoleByAuthorityEmptyAuthority() {
        assertThrows(InvalidEntityException.class, () -> toTest.getRoleByAuthority(""));
    }

    @Test
    void getRoleByAuthorityTooLongAuthority() {
        assertThrows(InvalidEntityException.class, () -> toTest.getRoleByAuthority(testLongString));
    }

    @Test
    void getRoleByAuthorityInvalidAuthority() {
        assertThrows(InvalidAuthorityException.class, () -> toTest.getRoleByAuthority("USER"));
    }

    @Test
    void createItem() throws InvalidEntityException, InvalidAuthorityException {
        final Role toCreate = new Role("USER");

        when(roleRepo.save(any())).thenReturn(testRole);

        Role fromService = toTest.createItem(toCreate);

        assertEquals(testRole, fromService);
    }

    @Test
    void createItemNullRole() {
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(null));
    }

    @Test
    void createItemEmptyAuthority() {
        final Role toCreate = new Role("");
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void createItemBlankAuthority() {
        final Role toCreate = new Role("  ");
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void createItemTooLongAuthority() {
        final Role toCreate = new Role(testLongString);
        assertThrows(InvalidEntityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void createItemInvalidAuthority() throws InvalidEntityException {
        final Role toCreate = new Role("USER");

        when(roleRepo.existsByAuthority(anyString())).thenReturn(true);

        assertThrows(InvalidAuthorityException.class, () -> toTest.createItem(toCreate));
    }

    @Test
    void editItem() throws InvalidEntityException, InvalidIdException {
        when(roleRepo.existsById(any(UUID.class))).thenReturn(true);
        when(roleRepo.save(testRole)).thenReturn(testRole);

        Role fromService = toTest.editItem(testRole);

        assertEquals(testRole, fromService);
    }

    @Test
    void editItemNullRole() {
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(null));
    }

    @Test
    void editItemEmptyAuthority() {
        final Role toEdit = new Role(id, "");
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemBlankAuthority() {
        final Role toEdit = new Role(id, "  ");
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemTooLongAuthority() {
        final Role toEdit = new Role(id, testLongString);
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemInvalidId() {
        final Role toEdit = new Role(id, "USER");
        assertThrows(InvalidIdException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void deleteItemById() throws InvalidIdException {
        when(roleRepo.existsById(any(UUID.class))).thenReturn(true);
        toTest.deleteItemById(id);
    }

    @Test
    void deleteItemByIdInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.deleteItemById(id));
    }
}