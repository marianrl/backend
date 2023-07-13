package com.ams.backend.service;

import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditTypeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class AuditTypeService {

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    public List<AuditType> getAllAuditType() {
        return auditTypeRepository.findAll();
    }

    public AuditType getAuditTypeById(int id) throws ResourceNotFoundException {
        return auditTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit type not found for this id :: " + id));
    }

    public AuditType createAuditType(AuditType auditType) {
        return auditTypeRepository.save(auditType);
    }

    public AuditType updateAuditType(int id, AuditType providedAuditType) throws ResourceNotFoundException {
        AuditType auditType = auditTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit type not found for this id :: " + id));

        auditType.setAuditType(providedAuditType.getAuditType());
        auditTypeRepository.save(auditType);

        return auditType;
    }

    public void deleteAuditType(int id) throws ResourceNotFoundException{
        auditTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit type not found for this id :: " + id));

        auditTypeRepository.deleteById(id);
    }
}
