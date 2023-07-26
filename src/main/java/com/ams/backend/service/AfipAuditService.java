package com.ams.backend.service;

import com.ams.backend.entity.AfipAudit;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AfipAuditRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AfipAuditService {

    @Autowired
    private AfipAuditRepository afipAuditRepository;

    public List<AfipAudit> getAllAfipAudits() {
        return afipAuditRepository.findAll();
    }

    public AfipAudit getAfipAuditById(int id) throws ResourceNotFoundException {
        return afipAuditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AfipAudit not found for this id :: " + id));
    }

    public AfipAudit createAfipAudit(AfipAudit afipAudit) {
        return afipAuditRepository.save(afipAudit);
    }

    public AfipAudit updateAfipAudit(int id, AfipAudit afipAudit) throws ResourceNotFoundException {
        AfipAudit afipAudit1 = afipAuditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AfipAudit not found for this id :: " + id));

        afipAudit1.setAuditDate(afipAudit.getAuditDate());
        afipAudit1.setLastName(afipAudit.getLastName());
        afipAudit1.setName(afipAudit.getName());
        afipAudit1.setCuil(afipAudit.getCuil());
        afipAudit1.setFile(afipAudit.getFile());
        afipAudit1.setAllocation(afipAudit.getAllocation());
        afipAudit1.setClient(afipAudit.getClient());
        afipAudit1.setUoc(afipAudit.getUoc());
        afipAudit1.setBranch(afipAudit.getBranch());
        afipAudit1.setAdmissionDate(afipAudit.getAdmissionDate());
        afipAudit1.setFeatures(afipAudit.getFeatures());

        afipAuditRepository.save(afipAudit1);

        return afipAudit1;
    }

    public void deleteAfipAudit(int id) throws ResourceNotFoundException{
        afipAuditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AfipAudit not found for this id :: " + id));

        afipAuditRepository.deleteById(id);
    }
}
