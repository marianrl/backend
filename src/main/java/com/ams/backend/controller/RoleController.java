package com.ams.backend.controller;

import com.ams.backend.entity.Role;
import com.ams.backend.request.RoleRequest;
import com.ams.backend.response.RoleResponse;
import com.ams.backend.service.interfaces.RoleService;
import com.ams.backend.exception.ResourceNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role")
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles().stream()
                .map(roleService.getMapper()::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable(value = "id") int roleId)
            throws ResourceNotFoundException {
        Role role = roleService.getRoleById(roleId);
        return ResponseEntity.ok().body(roleService.getMapper().toResponse(role));
    }

    @PostMapping("/role")
    public RoleResponse createRole(@Valid @RequestBody RoleRequest request) {
        Role role = roleService.getMapper().toEntity(request);
        Role savedRole = roleService.createRole(role);
        return roleService.getMapper().toResponse(savedRole);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable(value = "id") int roleId,
            @Valid @RequestBody RoleRequest request) throws ResourceNotFoundException {
        Role role = roleService.getMapper().toEntity(request);
        final Role updatedRole = roleService.updateRole(roleId, role);
        return ResponseEntity.ok(roleService.getMapper().toResponse(updatedRole));
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable(value = "id") int roleId)
            throws ResourceNotFoundException {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}
