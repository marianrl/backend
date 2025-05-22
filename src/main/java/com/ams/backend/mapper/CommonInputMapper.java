package com.ams.backend.mapper;

import com.ams.backend.entity.*;
import com.ams.backend.request.CommonInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.response.*;
import org.springframework.stereotype.Component;

@Component
public class CommonInputMapper {

  public CommonInputResponse toResponse(CommonInput commonInput) {
    if (commonInput == null) {
      return null;
    }

    CommonInputResponse response = new CommonInputResponse();
    response.setId(commonInput.getId());
    response.setLastName(commonInput.getLastName());
    response.setName(commonInput.getName());
    response.setCuil(commonInput.getCuil());
    response.setFile(commonInput.getFile());
    response.setAllocation(commonInput.getAllocation());

    // Map Client
    if (commonInput.getClient() != null) {
      ClientResponse clientResponse = new ClientResponse();
      clientResponse.setId(commonInput.getClient().getId());
      clientResponse.setClient(commonInput.getClient().getClient());
      response.setClient(clientResponse);
    }

    response.setUoc(commonInput.getUoc());

    // Map Branch
    if (commonInput.getBranch() != null) {
      BranchResponse branchResponse = new BranchResponse();
      branchResponse.setId(commonInput.getBranch().getId());
      branchResponse.setBranch(commonInput.getBranch().getBranch());
      response.setBranch(branchResponse);
    }

    response.setAdmissionDate(commonInput.getAdmissionDate());

    // Map Features
    if (commonInput.getFeatures() != null) {
      Features features = commonInput.getFeatures();
      AuditTypeResponse auditTypeResponse = new AuditTypeResponse();
      auditTypeResponse.setId(features.getAuditType().getId());
      auditTypeResponse.setAuditType(features.getAuditType().getAuditType());

      AnswerResponse answerResponse = new AnswerResponse();
      answerResponse.setId(features.getAnswer().getId());
      answerResponse.setAnswer(features.getAnswer().getAnswer());

      FeaturesResponse featuresResponse = new FeaturesResponse();
      featuresResponse.setId(features.getId());
      featuresResponse.setAuditType(auditTypeResponse);
      featuresResponse.setAnswer(answerResponse);
      response.setFeatures(featuresResponse);
    }

    // Map Audit
    if (commonInput.getAudit() != null) {
      Audit audit = commonInput.getAudit();
      AuditTypeResponse auditTypeResponse = new AuditTypeResponse();
      auditTypeResponse.setId(audit.getIdTipoAuditoria().getId());
      auditTypeResponse.setAuditType(audit.getIdTipoAuditoria().getAuditType());

      AuditedResponse auditedResponse = new AuditedResponse();
      auditedResponse.setId(audit.getIdAuditado().getId());
      auditedResponse.setAudited(audit.getIdAuditado().getAudited());

      AuditResponse auditResponse = new AuditResponse();
      auditResponse.setId(audit.getId());
      auditResponse.setAuditDate(audit.getAuditDate());
      auditResponse.setIdTipoAuditoria(auditTypeResponse);
      auditResponse.setIdAuditado(auditedResponse);
      response.setAudit(auditResponse);
    }

    return response;
  }

  public CommonInput toEntity(InputRequest request) {
    if (request == null) {
      return null;
    }

    CommonInput commonInput = new CommonInput();
    commonInput.setLastName(request.getLastName());
    commonInput.setName(request.getName());
    commonInput.setCuil(request.getCuil());
    commonInput.setFile(request.getFile());
    commonInput.setAllocation(request.getAllocation());
    commonInput.setUoc(request.getUoc());
    commonInput.setAdmissionDate(request.getAdmissionDate());
    return commonInput;
  }

  public void updateEntity(CommonInput entity, CommonInputUpdateRequest request) {
    if (entity == null || request == null) {
      return;
    }

    if (request.getLastName() != null)
      entity.setLastName(request.getLastName());
    if (request.getName() != null)
      entity.setName(request.getName());
    if (request.getCuil() != null)
      entity.setCuil(request.getCuil());
    if (request.getFile() != null)
      entity.setFile(request.getFile());
    if (request.getAllocation() != null)
      entity.setAllocation(request.getAllocation());
    if (request.getUoc() != null)
      entity.setUoc(request.getUoc());
    if (request.getAdmissionDate() != null)
      entity.setAdmissionDate(request.getAdmissionDate());
  }
}