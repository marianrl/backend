package com.ams.backend.service;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AfipInputRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AfipInputService {

    @Autowired
    private AfipInputRepository afipInputRepository;

    public List<AfipInput> getAllAfipInputs() {
        return afipInputRepository.findAll();
    }

    public List<AfipInput> getAfipInputByAuditNumber(int id) {
        return afipInputRepository.findByAuditNumber(id);
    }

    public AfipInput createAfipInput(AfipInput afipInput) {
        return afipInputRepository.save(afipInput);
    }

    public AfipInput updateAfipInput(int id, AfipInput afipInput) throws ResourceNotFoundException {
        AfipInput afipInput1 = afipInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AfipAudit not found for this id :: " + id));

        afipInput1.setLastName(afipInput.getLastName());
        afipInput1.setName(afipInput.getName());
        afipInput1.setCuil(afipInput.getCuil());
        afipInput1.setFile(afipInput.getFile());
        afipInput1.setAllocation(afipInput.getAllocation());
        afipInput1.setClient(afipInput.getClient());
        afipInput1.setUoc(afipInput.getUoc());
        afipInput1.setBranch(afipInput.getBranch());
        afipInput1.setAdmissionDate(afipInput.getAdmissionDate());
        afipInput1.setFeatures(afipInput.getFeatures());
        afipInput1.setAudit(afipInput.getAudit());

        afipInputRepository.save(afipInput1);

        return afipInput1;
    }

    public void deleteAfipInput(int id) throws ResourceNotFoundException{
        afipInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AfipAudit not found for this id :: " + id));

        afipInputRepository.deleteById(id);
    }
}
