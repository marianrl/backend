package com.ams.backend.service;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.entity.AfipInputSpecification;
import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AfipInputRepository;
import com.ams.backend.repository.FeaturesRepository;
import com.ams.backend.request.AfipInputUpdateRequest;
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

    @Autowired
    private FeaturesRepository featuresRepository;

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

    public AfipInput updateAfipInput(int id, AfipInputUpdateRequest afipInputUpdateRequest) throws ResourceNotFoundException {
        AfipInput afipInputToUpdate = afipInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AfipInput not found for this id :: " + id));

        // Asignar los valores simples que no son FK
        afipInputToUpdate.setLastName(afipInputUpdateRequest.getLastName());
        afipInputToUpdate.setName(afipInputUpdateRequest.getName());
        afipInputToUpdate.setCuil(afipInputUpdateRequest.getCuil());
        afipInputToUpdate.setFile(afipInputUpdateRequest.getFile());
        afipInputToUpdate.setAllocation(afipInputUpdateRequest.getAllocation());
        afipInputToUpdate.setUoc(afipInputUpdateRequest.getUoc());
        afipInputToUpdate.setAdmissionDate(afipInputUpdateRequest.getAdmissionDate());
        // Otros campos que deseas actualizar

        // Cargar el objeto Features existente desde la base de datos utilizando su ID
        Features existingFeatures = featuresRepository.findById(afipInputUpdateRequest.getFeaturesId())
                .orElseThrow(() -> new ResourceNotFoundException("Features not found with id :: " + afipInputUpdateRequest.getFeaturesId()));

        // Asignar el objeto Features existente al AfipInput actualizado
        afipInputToUpdate.setFeatures(existingFeatures);

        // Guardar el AfipInput actualizado en la base de datos
        return afipInputRepository.save(afipInputToUpdate);
    }

    public void deleteAfipInput(int id) throws ResourceNotFoundException{
        afipInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AfipAudit not found for this id :: " + id));

        afipInputRepository.deleteById(id);
    }
}
