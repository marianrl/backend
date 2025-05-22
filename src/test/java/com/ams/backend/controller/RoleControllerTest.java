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
import static org.mockito.Mockito.*;

import com.ams.backend.entity.Role;
import com.ams.backend.mapper.RoleMapper;
import com.ams.backend.request.RoleRequest;
import com.ams.backend.response.RoleResponse;
import com.ams.backend.service.interfaces.RoleService;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @Mock
    private RoleMapper mapper;

    @InjectMocks
    private RoleController roleController;

    private Role role;
    private RoleResponse roleResponse;
    private RoleRequest roleRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        role = new Role();
        role.setId(1);
        role.setRole("Admin");

        roleResponse = new RoleResponse();
        roleResponse.setId(1);
        roleResponse.setRole("Admin");

        roleRequest = new RoleRequest();
        roleRequest.setId(1);
        roleRequest.setRole("Admin");

        when(roleService.getMapper()).thenReturn(mapper);
        when(mapper.toResponse(any(Role.class))).thenReturn(roleResponse);
        when(mapper.toEntity(any(RoleRequest.class))).thenReturn(role);
    }

    @Test
    public void getAllRolesTest() throws Exception {
        List<Role> roles = Collections.singletonList(role);
        when(roleService.getAllRoles()).thenReturn(roles);

        List<RoleResponse> result = roleController.getAllRoles();

        assertEquals(1, result.size());
        assertEquals(roleResponse, result.get(0));
        verify(roleService, times(1)).getAllRoles();
    }

    @Test
    public void getRoleByIdTest() throws Exception {
        when(roleService.getRoleById(1)).thenReturn(role);

        ResponseEntity<RoleResponse> result = roleController.getRoleById(1);

        assertEquals(roleResponse, result.getBody());
        verify(roleService, times(1)).getRoleById(1);
    }

    @Test
    public void createRoleTest() throws Exception {
        when(roleService.createRole(any(Role.class))).thenReturn(role);

        RoleResponse result = roleController.createRole(roleRequest);

        assertEquals(roleResponse, result);
        verify(roleService, times(1)).createRole(any(Role.class));
    }

    @Test
    public void updateRoleTest() throws Exception {
        Role updatedRole = new Role();
        updatedRole.setId(1);
        updatedRole.setRole("Updated Admin");
        when(roleService.updateRole(eq(1), any(Role.class))).thenReturn(updatedRole);

        ResponseEntity<RoleResponse> result = roleController.updateRole(1, roleRequest);

        assertEquals(roleResponse, result.getBody());
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
