package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Role;
import com.ams.backend.repository.RoleRepository;
import com.ams.backend.service.interfaces.RoleService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(int id) throws ResourceNotFoundException {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(int id, Role providedRole) throws ResourceNotFoundException {
      Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));

        role.setRole(providedRole.getRole());

        roleRepository.save(role);
        return role;
    }

    public void deleteRole(int id) throws ResourceNotFoundException{
        roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));

        roleRepository.deleteById(id);
    }
}
