package com.sgz.server.controllers;

import com.sgz.server.entities.Role;
import com.sgz.server.exceptions.InvalidAuthorityException;
import com.sgz.server.exceptions.InvalidEntityException;
import com.sgz.server.exceptions.InvalidIdException;
import com.sgz.server.exceptions.NoItemsException;
import com.sgz.server.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Role>> getAllRoles() throws NoItemsException {
        return ResponseEntity.ok(roleService.getAllItems());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Role> getRoleById(@PathVariable UUID id) throws InvalidIdException {
        return ResponseEntity.ok(roleService.getItemById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role toAdd) throws InvalidEntityException, InvalidAuthorityException {
        return new ResponseEntity(roleService.createItem(toAdd), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Role> editRole(@PathVariable UUID id, @Valid @RequestBody Role toEdit) throws InvalidEntityException, InvalidIdException {
        try {
            Role toCheck = roleService.getRoleByAuthority(toEdit.getAuthority());
            if (!toCheck.getId().equals(id) || !toEdit.getId().equals(id)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidAuthorityException | InvalidEntityException ex) {
        }

        toEdit.setId(id);
        return ResponseEntity.ok(roleService.editItem(toEdit));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UUID> deleteRoleById(@PathVariable UUID id) throws InvalidIdException {
        roleService.deleteItemById(id);
        return ResponseEntity.ok(id);
    }

}
