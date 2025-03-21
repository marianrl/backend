package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Audited;
import com.ams.backend.repository.AuditedRepository;
import com.ams.backend.service.interfaces.AuditedService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuditedServiceImpl implements AuditedService {

    @Autowired
    private AuditedRepository auditedRepository;

    public List<Audited> getAllAudited() {
        return auditedRepository.findAll();
    }

    public Audited getAuditedById(int id) throws ResourceNotFoundException {
        return auditedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audited not found for this id :: " + id));
    }

    public Audited createAudited(Audited audited) {
        return auditedRepository.save(audited);
    }

    public Audited updateAudited(int id, Audited providedAudited) throws ResourceNotFoundException {
        Audited audited = auditedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audited not found for this id :: " + id));

        audited.setAudited(providedAudited.getAudited());
        auditedRepository.save(audited);

        return audited;
    }

    public void deleteAudited(int id) throws ResourceNotFoundException{
        auditedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audited not found for this id :: " + id));

        auditedRepository.deleteById(id);
    }
}
