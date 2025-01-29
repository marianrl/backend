package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.Audited;
import com.ams.backend.exception.ResourceNotFoundException;

public interface AuditedService {
  List<Audited> getAllAudited();
  Audited getAuditedById(int id) throws ResourceNotFoundException;
  Audited createAudited(Audited audited);
  Audited updateAudited(int id, Audited providedAudited) throws ResourceNotFoundException;
  void deleteAudited(int id) throws ResourceNotFoundException;
}
