package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.request.AuditTypeRequest;
import com.ams.backend.response.AuditTypeResponse;
import com.ams.backend.exception.ResourceNotFoundException;

public interface AuditTypeService {
  List<AuditTypeResponse> getAllAuditType();

  AuditTypeResponse getAuditTypeById(int id) throws ResourceNotFoundException;

  AuditTypeResponse createAuditType(AuditTypeRequest auditTypeRequest);

  AuditTypeResponse updateAuditType(int id, AuditTypeRequest auditTypeRequest) throws ResourceNotFoundException;

  void deleteAuditType(int id) throws ResourceNotFoundException;
}