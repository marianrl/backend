package com.ams.backend.service;

import com.ams.backend.entity.CommonAudit;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.CommonAuditRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class CommonAuditService {

    @Autowired
    private CommonAuditRepository commonAuditRepository;

    public List<CommonAudit> getAllCommonAudits() {
        return commonAuditRepository.findAll();
    }

    public CommonAudit getCommonAuditById(int id) throws ResourceNotFoundException {
        return commonAuditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommonAudit not found for this id :: " + id));
    }

    public CommonAudit createCommonAudit(CommonAudit commonAudit) {
        return commonAuditRepository.save(commonAudit);
    }

    public CommonAudit updateCommonAudit(int id, CommonAudit commonAudit) throws ResourceNotFoundException {
        CommonAudit commonAudit1 = commonAuditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommonAudit not found for this id :: " + id));

        commonAudit1.setAuditDate(commonAudit.getAuditDate());
        commonAudit1.setLastName(commonAudit.getLastName());
        commonAudit1.setName(commonAudit.getName());
        commonAudit1.setCuil(commonAudit.getCuil());
        commonAudit1.setFile(commonAudit.getFile());
        commonAudit1.setAllocation(commonAudit.getAllocation());
        commonAudit1.setClient(commonAudit.getClient());
        commonAudit1.setUoc(commonAudit.getUoc());
        commonAudit1.setBranch(commonAudit.getBranch());
        commonAudit1.setAdmissionDate(commonAudit.getAdmissionDate());
        commonAudit1.setFeatures(commonAudit.getFeatures());

        commonAuditRepository.save(commonAudit1);

        return commonAudit1;
    }

    public void deleteCommonAudit(int id) throws ResourceNotFoundException{
        commonAuditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommonAudit not found for this id :: " + id));

        commonAuditRepository.deleteById(id);
    }
}
