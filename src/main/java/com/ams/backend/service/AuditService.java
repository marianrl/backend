package com.ams.backend.service;

import com.ams.backend.entity.Audit;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public List<Audit> getAllAudit() {
        return auditRepository.findAll();
    }

    public Audit getAuditById(int id) throws ResourceNotFoundException {
        return auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));
    }

    public Audit createAudit(Audit audit) {
        return auditRepository.save(audit);
    }

    public Audit updateAudit(int id, Audit providedAudit) throws ResourceNotFoundException {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));

        audit.setAuditDate(providedAudit.getAuditDate());
        audit.setAuditNumber(providedAudit.getAuditNumber());
        auditRepository.save(audit);

        return audit;
    }

    public void deleteAudit(int id) throws ResourceNotFoundException{
        auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));

        auditRepository.deleteById(id);
    }
}
