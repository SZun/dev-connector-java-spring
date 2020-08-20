package com.sgz.server.services;

import com.sgz.server.entities.Skill;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.InvalidNameException;
import com.sgz.server.exceptions.NoItemsException;
import com.sgz.server.repos.SkillRepo;
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
class SkillServiceTest {

    @InjectMocks
    private SkillService toTest;

    @Mock
    private SkillRepo skillRepo;

    private final String testLongString = "1ZvBWFVdBu62e6yT87rdELXaLP6KfY2wJ9ZRpw9KmZqzNFICvlNKgkCU28aKRpQb2I85EqAxr6Xb4A1Ct4yNEjTOAXgNyyIBEyTnjOYyN4piLPot1OYtnNftyVXZg6DSxlAGgYzBa5ATYzkSHo2EmIpNyc0NCXvFtPdwP1N30s1Fn63sBaQGdX8sZffYO29yTVtg4LLYRdrrP8aPmL2Pm3c3XySoA7KLLNIi8417yXnjzgdDQErkKiAuoR5REsdL";

    private final UUID id = new UUID(36,36);

    private final Skill expectedSkill = new Skill(id, "Java 11");

    @Test
    void getAllItems() throws NoItemsException {
        final Skill expectedSkill2 = new Skill(id, "Java 8");
        final Skill expectedSkill3 = new Skill(id, "The Spring Framework");

        when(skillRepo.findAll()).thenReturn(Arrays.asList(expectedSkill,expectedSkill2, expectedSkill3));

        List<Skill> fromService = toTest.getAllItems();

        assertEquals(3, fromService.size());
        assertTrue(fromService.contains(expectedSkill));
        assertTrue(fromService.contains(expectedSkill2));
        assertTrue(fromService.contains(expectedSkill3));
    }

    @Test
    void getAllItemsNoItems() {
        assertThrows(NoItemsException.class, () -> toTest.getAllItems());
    }

    @Test
    void getItemById() throws InvalidIdException {
        when(skillRepo.findById(any(UUID.class))).thenReturn(Optional.of(expectedSkill));

        Skill fromService = toTest.getItemById(id);

        assertEquals(expectedSkill, fromService);
    }

    @Test
    void getItemByIdInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.getItemById(id));
    }

    @Test
    void getSkillByName() throws InvalidEntityException, InvalidNameException {
        when(skillRepo.findByName(anyString())).thenReturn(Optional.of(expectedSkill));

        Skill fromService = toTest.getSkillByName("Java 11");

        assertEquals(expectedSkill, fromService);
    }

    @Test
    void getSkillByNameNullName() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSkillByName(null));
    }

    @Test
    void getSkillByNameBlankName() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSkillByName("  "));
    }

    @Test
    void getSkillByNameEmptyName() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSkillByName(""));
    }

    @Test
    void getSkillByNameTooLongName() {
        assertThrows(InvalidEntityException.class, () -> toTest.getSkillByName(testLongString));
    }

    @Test
    void getSkillByNameInvalidName() {
        assertThrows(InvalidNameException.class, () -> toTest.getSkillByName("Java 11"));
    }

    @Test
    void createSkill() throws InvalidEntityException, InvalidNameException {
        when(skillRepo.save(any(Skill.class))).thenReturn(expectedSkill);

        Skill fromService = toTest.createSkill(expectedSkill);

        assertEquals(expectedSkill, fromService);
    }

    @Test
    void createSkillNullSkill() {
        assertThrows(InvalidEntityException.class, () -> toTest.createSkill(null));
    }

    @Test
    void createSkillBlankName() {
        final Skill toCreate = new Skill(id, "  ");
        assertThrows(InvalidEntityException.class, () -> toTest.createSkill(toCreate));
    }

    @Test
    void createSkillEmptyName() {
        final Skill toCreate = new Skill(id, "");
        assertThrows(InvalidEntityException.class, () -> toTest.createSkill(toCreate));
    }

    @Test
    void createSkillTooLongName() {
        final Skill toCreate = new Skill(id, testLongString);
        assertThrows(InvalidEntityException.class, () -> toTest.createSkill(toCreate));
    }

    @Test
    void createSkillInvalidName() {
        final Skill toCreate = new Skill(id, "Java 11");

        when(skillRepo.existsByName(anyString())).thenReturn(true);

        assertThrows(InvalidNameException.class, () -> toTest.createSkill(toCreate));
    }

    @Test
    void editItem() throws InvalidEntityException, InvalidIdException {
        when(skillRepo.save(any(Skill.class))).thenReturn(expectedSkill);
        when(skillRepo.existsById(any(UUID.class))).thenReturn(true);

        Skill fromService = toTest.editItem(expectedSkill);

        assertEquals(expectedSkill, fromService);
    }

    @Test
    void editItemNullSkill() {
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(null));
    }

    @Test
    void editItemBlankName() {
        final Skill toEdit = new Skill(id, "  ");
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemEmptyName() {
        final Skill toEdit = new Skill(id, "");
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemTooLongName() {
        final Skill toEdit = new Skill(id, testLongString);
        assertThrows(InvalidEntityException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void editItemInvalidId() {
        final Skill toEdit = new Skill(id, "Java 11");
        assertThrows(InvalidIdException.class, () -> toTest.editItem(toEdit));
    }

    @Test
    void deleteItemById() throws InvalidIdException {
        when(skillRepo.existsById(any(UUID.class))).thenReturn(true);

        toTest.deleteItemById(id);
    }

    @Test
    void deleteItemByIdInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.deleteItemById(id));
    }
}