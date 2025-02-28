package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.interfaces.RoleService;
import com.ams.backend.entity.Role;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role")
    public List<Role> getAllRoles() {

        return roleService.getAllRoles();
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") int RoleId)
            throws ResourceNotFoundException{
        Role Role = roleService.getRoleById(RoleId);

        return ResponseEntity.ok().body(Role);
    }

    @PostMapping("/role")
    public Role createRole (@Valid @RequestBody Role Role)
    {
        return roleService.createRole(Role);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<Role> updateRole(
            @PathVariable(value = "id") int RoleId,
            @Valid @RequestBody Role RoleDetails) throws ResourceNotFoundException {
        final Role updatedRole = roleService.updateRole(RoleId, RoleDetails);

        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable(value = "id") int RoleId)
            throws ResourceNotFoundException {
                roleService.deleteRole(RoleId);

        return ResponseEntity.noContent().build();
    }
}
