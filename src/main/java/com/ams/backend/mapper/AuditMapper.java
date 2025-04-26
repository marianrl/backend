package com.ams.backend.mapper;

import com.ams.backend.entity.Audit;
import com.ams.backend.request.AuditRequest;
import com.ams.backend.response.AuditResponse;
import com.ams.backend.response.AuditTypeResponse;
import com.ams.backend.response.AuditedResponse;
import org.springframework.stereotype.Component;

@Component
public class AuditMapper {

  public Audit toEntity(AuditRequest request) {
    Audit audit = new Audit();
    audit.setAuditDate(java.time.LocalDate.now());
    return audit;
  }

  public AuditResponse toResponse(Audit entity) {
    return new AuditResponse(
        entity.getId(),
        entity.getAuditDate(),
        new AuditTypeResponse(entity.getIdTipoAuditoria().getId(), entity.getIdTipoAuditoria().getAuditType()),
        new AuditedResponse(entity.getIdAuditado().getId(), entity.getIdAuditado().getAudited()));
  }
}