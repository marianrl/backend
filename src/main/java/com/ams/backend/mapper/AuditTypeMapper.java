package com.ams.backend.mapper;

import com.ams.backend.entity.AuditType;
import com.ams.backend.request.AuditTypeRequest;
import com.ams.backend.response.AuditTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class AuditTypeMapper {

  public AuditTypeResponse toResponse(AuditType auditType) {
    if (auditType == null) {
      return null;
    }

    return new AuditTypeResponse(
        auditType.getId(),
        auditType.getAuditType());
  }

  public AuditType toEntity(AuditTypeRequest request) {
    if (request == null) {
      return null;
    }

    AuditType auditType = new AuditType();
    auditType.setId(request.getId());
    auditType.setAuditType(request.getAuditType());
    return auditType;
  }
}