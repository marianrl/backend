package com.ams.backend.service;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.entity.AfipInputSpecification;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AfipInputRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AfipInputService {

    @Autowired
    private AfipInputRepository afipInputRepository;

    public List<AfipInput> getAllAfipInputs() {
        return afipInputRepository.findAll();
    }

    public Optional<AfipInput> getAfipInputById(int id) {
        return afipInputRepository.findById(id);
    }


    public List<AfipInput> getAfipInputByAuditNumber(int id) {
        return afipInputRepository.findByAudit_AuditNumber(id);
    }

    public List<AfipInput> getFilteredAfipInputs(
            String apellido,
            String nombre,
            String cuil,
            String legajo,
            String asignacion,
            Long idCliente,
            String uoc,
            Long idSucursal,
            LocalDate fechaIngreso,
            Long idCaracteristicas) {

        Specification<AfipInput> specification = AfipInputSpecification.getFilteredAfipInputs(
                apellido, nombre, cuil, legajo, asignacion, idCliente, uoc, idSucursal, fechaIngreso, idCaracteristicas
        );

        return afipInputRepository.findAll(specification);
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
