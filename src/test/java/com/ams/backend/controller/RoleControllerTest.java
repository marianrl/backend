package com.ams.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ams.backend.entity.Role;
import com.ams.backend.service.interfaces.RoleService;


@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private Role role;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        role = new Role();
        role.setId(1);
    }

    @Test
    public void getAllRolesTest() throws Exception {
        List<Role> roles = Collections.singletonList(role);
        when(roleService.getAllRoles()).thenReturn(roles);

        List<Role> result = roleController.getAllRoles();

        assertEquals(roles, result);
        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    public void getRoleByIdTest() throws Exception {
        when(roleService.getRoleById(1)).thenReturn(role);

        ResponseEntity<Role> result = roleController.getRoleById(1);

        assertEquals(role, result.getBody());
        verify(roleService, times(1)).getRoleById(1);
    }

    @Test
    public void createRoleTest() throws Exception {
        when(roleService.createRole(any(Role.class))).thenReturn(role);

        Role result = roleController.createRole(role);

        assertEquals(role, result);
        verify(roleService, times(1)).createRole(any(Role.class));
    }

    @Test
    public void updateRoleTest() throws Exception {
        Role updatedRole = new Role();
        when(roleService.updateRole(eq(1), any(Role.class))).thenReturn(updatedRole);

        ResponseEntity<Role> result = roleController.updateRole(1, updatedRole);

        assertEquals(updatedRole, result.getBody());
        verify(roleService, times(1)).updateRole(eq(1), any(Role.class));
    }

    @Test
    public void deleteRoleTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(roleService).deleteRole(1);
        ResponseEntity<Void> result = roleController.deleteRole(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(roleService, times(1)).deleteRole(1);
    }
}
