package com.ams.backend.service;

import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.AuditTypeMapper;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.request.AuditTypeRequest;
import com.ams.backend.response.AuditTypeResponse;
import com.ams.backend.service.interfaces.AuditTypeService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AuditTypeServiceImpl implements AuditTypeService {

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    @Autowired
    private AuditTypeMapper auditTypeMapper;

    public List<AuditTypeResponse> getAllAuditType() {
        return auditTypeRepository.findAll().stream()
                .map(auditTypeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AuditTypeResponse getAuditTypeById(int id) throws ResourceNotFoundException {
        AuditType auditType = auditTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit type not found for this id :: " + id));
        return auditTypeMapper.toResponse(auditType);
    }

    public AuditTypeResponse createAuditType(AuditTypeRequest auditTypeRequest) {
        AuditType auditType = auditTypeMapper.toEntity(auditTypeRequest);
        AuditType savedAuditType = auditTypeRepository.save(auditType);
        return auditTypeMapper.toResponse(savedAuditType);
    }

    public AuditTypeResponse updateAuditType(int id, AuditTypeRequest auditTypeRequest)
            throws ResourceNotFoundException {
        AuditType auditType = auditTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit type not found for this id :: " + id));

        auditType.setAuditType(auditTypeRequest.getAuditType());
        AuditType updatedAuditType = auditTypeRepository.save(auditType);
        return auditTypeMapper.toResponse(updatedAuditType);
    }

    public void deleteAuditType(int id) throws ResourceNotFoundException {
        auditTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit type not found for this id :: " + id));
        auditTypeRepository.deleteById(id);
    }
}
