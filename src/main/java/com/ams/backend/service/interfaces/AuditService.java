package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AuditRequest;
import com.ams.backend.response.AuditResponse;

public interface AuditService {
  List<AuditResponse> getAllAudit();

  AuditResponse getAuditById(int auditId) throws ResourceNotFoundException;

  AuditResponse createAudit(AuditRequest auditRequest) throws ResourceNotFoundException;

  AuditResponse updateAudit(int auditId, AuditRequest auditRequest) throws ResourceNotFoundException;

  void deleteAudit(int auditId) throws ResourceNotFoundException;
}