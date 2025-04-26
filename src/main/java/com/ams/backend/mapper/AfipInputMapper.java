package com.ams.backend.mapper;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.response.AfipInputResponse;
import com.ams.backend.response.ClientResponse;
import com.ams.backend.response.BranchResponse;
import com.ams.backend.response.FeaturesResponse;
import com.ams.backend.response.AuditTypeResponse;
import com.ams.backend.response.AnswerResponse;
import com.ams.backend.response.AuditResponse;
import com.ams.backend.response.AuditedResponse;
import org.springframework.stereotype.Component;

@Component
public class AfipInputMapper {
  public AfipInputResponse toResponse(AfipInput entity) {
    if (entity == null) {
      return null;
    }

    AfipInputResponse response = new AfipInputResponse();
    response.setId(entity.getId());
    response.setLastName(entity.getLastName());
    response.setName(entity.getName());
    response.setCuil(entity.getCuil());
    response.setFile(entity.getFile());
    response.setAllocation(entity.getAllocation());
    response.setUoc(entity.getUoc());
    response.setAdmissionDate(entity.getAdmissionDate());

    // Map related entities to their DTOs
    if (entity.getClient() != null) {
      response.setClient(new ClientResponse(entity.getClient().getId(), entity.getClient().getClient()));
    }

    if (entity.getBranch() != null) {
      response.setBranch(new BranchResponse(entity.getBranch().getId(), entity.getBranch().getBranch()));
    }

    if (entity.getFeatures() != null) {
      response.setFeatures(new FeaturesResponse(
          entity.getFeatures().getId(),
          new AuditTypeResponse(entity.getFeatures().getAuditType().getId(),
              entity.getFeatures().getAuditType().getAuditType()),
          new AnswerResponse(entity.getFeatures().getAnswer().getId(), entity.getFeatures().getAnswer().getAnswer())));
    }

    if (entity.getAudit() != null) {
      response.setAudit(new AuditResponse(
          entity.getAudit().getId(),
          entity.getAudit().getAuditDate(),
          new AuditTypeResponse(entity.getAudit().getIdTipoAuditoria().getId(),
              entity.getAudit().getIdTipoAuditoria().getAuditType()),
          new AuditedResponse(entity.getAudit().getIdAuditado().getId(),
              entity.getAudit().getIdAuditado().getAudited())));
    }

    return response;
  }
}