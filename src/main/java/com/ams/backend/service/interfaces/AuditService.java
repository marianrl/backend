package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.Audit;
import com.ams.backend.exception.ResourceNotFoundException;

public interface AuditService {
  List<Audit> getAllAudit();
  Audit getAuditById(int id) throws ResourceNotFoundException;
  Audit createAudit(int auditTypeId) throws ResourceNotFoundException;
  Audit updateAudit(int id, Audit providedAudit) throws ResourceNotFoundException;
  void deleteAudit(int id) throws ResourceNotFoundException;
}