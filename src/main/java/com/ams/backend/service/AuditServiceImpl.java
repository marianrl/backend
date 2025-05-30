package com.ams.backend.service;

import com.ams.backend.entity.Audit;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.AuditMapper;
import com.ams.backend.repository.AuditRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.repository.AuditedRepository;
import com.ams.backend.request.AuditRequest;
import com.ams.backend.response.AuditResponse;
import com.ams.backend.service.interfaces.AuditService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    @Autowired
    private AuditedRepository auditedRepository;

    @Autowired
    private AuditMapper auditMapper;

    public List<AuditResponse> getAllAudit() {
        return auditRepository.findAll().stream()
                .map(auditMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AuditResponse getAuditById(int id) throws ResourceNotFoundException {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));
        return auditMapper.toResponse(audit);
    }

    public AuditResponse createAudit(AuditRequest auditRequest) throws ResourceNotFoundException {
        AuditType auditType = auditTypeRepository.findById(auditRequest.getAuditTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "AuditType not found for this id :: " + auditRequest.getAuditTypeId()));

        Audit audit = auditMapper.toEntity(auditRequest);
        audit.setAuditType(auditType);
        audit.setAudited(new Audited(2, "No"));

        Audit savedAudit = auditRepository.save(audit);
        return auditMapper.toResponse(savedAudit);
    }

    public AuditResponse updateAudit(int id, AuditRequest auditRequest) throws ResourceNotFoundException {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));

        Audited audited = auditedRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("Audited not found for this id :: 1"));

        audit.setAudited(audited);
        Audit updatedAudit = auditRepository.save(audit);

        return auditMapper.toResponse(updatedAudit);
    }

    public void deleteAudit(int id) throws ResourceNotFoundException {
        auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));

        auditRepository.deleteById(id);
    }
}
