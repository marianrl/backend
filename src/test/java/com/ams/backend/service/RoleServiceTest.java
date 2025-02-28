package com.ams.backend.service;

import com.ams.backend.entity.Role;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setId(1);
        role.setRole("Admin");
    }

    @Test
    public void testGetAllRoles() {
        List<Role> expectedRoles = new ArrayList<>();
        expectedRoles.add(role);
        when(roleRepository.findAll()).thenReturn(expectedRoles);

        List<Role> actualRoles = roleService.getAllRoles();

        assertEquals(expectedRoles, actualRoles);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testGetRoleById() throws ResourceNotFoundException {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        Role actualRole = roleService.getRoleById(1);

        assertEquals(role, actualRole);
        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    public void testGetRoleById_NotFound() {
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.getRoleById(1));
    }

    @Test
    public void testCreateRole() {
        when(roleRepository.save(role)).thenReturn(role);

        Role actualRole = roleService.createRole(role);

        assertEquals(role, actualRole);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void testUpdateRole() throws ResourceNotFoundException {
        Role updatedRole = new Role();
        updatedRole.setRole("User");

        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);

        Role actualRole = roleService.updateRole(1, updatedRole);

        assertEquals(updatedRole.getRole(), actualRole.getRole());
        verify(roleRepository, times(1)).findById(1);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void testUpdateRole_NotFound() {
        Role updatedRole = new Role();
        updatedRole.setRole("User");

        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.updateRole(1, updatedRole));
    }

    @Test
    public void testDeleteRole() throws ResourceNotFoundException {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).deleteById(1);

        roleService.deleteRole(1);

        verify(roleRepository, times(1)).findById(1);
        verify(roleRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteRole_NotFound() {
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.deleteRole(1));
    }
}
