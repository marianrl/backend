package com.ams.backend.service;

import com.ams.backend.entity.Audit;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AuditRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.service.interfaces.AuditService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    public List<Audit> getAllAudit() {
        return auditRepository.findAll();
    }

    public Audit getAuditById(int id) throws ResourceNotFoundException {
        return auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));
    }

    public Audit createAudit(int auditTypeId) throws ResourceNotFoundException {

        AuditType auditType = auditTypeRepository.findById(auditTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("AuditType not found for this id :: " + auditTypeId));

        Audit audit = new Audit();
        audit.setAuditDate(LocalDate.now());
        audit.setIdTipoAuditoria(auditType);
        audit.setIdAuditado(new Audited(2, "No"));

        return auditRepository.save(audit);
    }

    public Audit updateAudit(int id, Audit providedAudit) throws ResourceNotFoundException {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));

        audit.setAuditDate(providedAudit.getAuditDate());
        audit.setIdAuditado(providedAudit.getIdAuditado());
        auditRepository.save(audit);

        return audit;
    }

    public void deleteAudit(int id) throws ResourceNotFoundException {
        auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found for this id :: " + id));

        auditRepository.deleteById(id);
    }
}
