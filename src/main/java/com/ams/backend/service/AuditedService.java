package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Audited;
import com.ams.backend.repository.AuditedRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class AuditedService {

    @Autowired
    private AuditedRepository auditedRepository;

    public List<Audited> getAllAudited() {
        return auditedRepository.findAll();
    }

    public Audited getAuditedById(Long id) throws ResourceNotFoundException {
        return auditedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audited not found for this id :: " + id));
    }

    public Audited createAudited(Audited audited) {
        return auditedRepository.save(audited);
    }

    public Audited updateAudited(Long id, Audited providedAudited) throws ResourceNotFoundException {
        Audited audited = auditedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audited not found for this id :: " + id));

        audited.setAudited(providedAudited.getAudited());
        auditedRepository.save(audited);

        return audited;
    }

    public void deleteAudited(Long id) throws ResourceNotFoundException{
        auditedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audited not found for this id :: " + id));

        auditedRepository.deleteById(id);
    }
}
