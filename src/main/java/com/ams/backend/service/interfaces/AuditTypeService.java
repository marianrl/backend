package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;

public interface AuditTypeService {
  List<AuditType> getAllAuditType();
  AuditType getAuditTypeById(int id) throws ResourceNotFoundException;
  AuditType createAuditType(AuditType auditType);
  AuditType updateAuditType(int id, AuditType providedAuditType) throws ResourceNotFoundException;
  void deleteAuditType(int id) throws ResourceNotFoundException;
}