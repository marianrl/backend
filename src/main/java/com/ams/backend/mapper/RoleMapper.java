package com.ams.backend.mapper;

import com.ams.backend.entity.Role;
import com.ams.backend.request.RoleRequest;
import com.ams.backend.response.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

  public RoleResponse toResponse(Role role) {
    if (role == null) {
      return null;
    }

    RoleResponse response = new RoleResponse();
    response.setId(role.getId());
    response.setRole(role.getRole());
    return response;
  }

  public Role toEntity(RoleRequest request) {
    if (request == null) {
      return null;
    }

    Role role = new Role();
    role.setRole(request.getRole());
    return role;
  }
}