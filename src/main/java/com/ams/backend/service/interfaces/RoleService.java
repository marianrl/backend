package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.Role;
import com.ams.backend.exception.ResourceNotFoundException;

public interface RoleService {
  List<Role> getAllRoles();
  Role getRoleById(int id) throws ResourceNotFoundException;
  Role createRole(Role role);
  Role updateRole(int id, Role providedRole) throws ResourceNotFoundException;
  void deleteRole(int id) throws ResourceNotFoundException;
}
