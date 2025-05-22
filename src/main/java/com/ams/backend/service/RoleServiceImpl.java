package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Role;
import com.ams.backend.mapper.RoleMapper;
import com.ams.backend.repository.RoleRepository;
import com.ams.backend.service.interfaces.RoleService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper mapper;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(int id) throws ResourceNotFoundException {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(int id, Role providedRole) throws ResourceNotFoundException {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));

        role.setRole(providedRole.getRole());

        roleRepository.save(role);
        return role;
    }

    @Override
    public void deleteRole(int id) throws ResourceNotFoundException {
        roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));

        roleRepository.deleteById(id);
    }

    @Override
    public RoleMapper getMapper() {
        return mapper;
    }
}
