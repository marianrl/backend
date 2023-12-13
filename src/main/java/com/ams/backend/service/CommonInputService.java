package com.ams.backend.service;

import com.ams.backend.entity.CommonInput;
import com.ams.backend.entity.CommonInputSpecification;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.CommonInputRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class CommonInputService {

    @Autowired
    private CommonInputRepository commonInputRepository;

    public List<CommonInput> getAllCommonInputs() {
        return commonInputRepository.findAll();
    }

    public List<CommonInput> getCommonInputByAuditNumber(int id){
        return commonInputRepository.findByAudit_AuditNumber(id);
    }

    public List<CommonInput> getFilteredCommonInputs(
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

        Specification<CommonInput> specification = CommonInputSpecification.getFilteredCommonInputs(
                apellido, nombre, cuil, legajo, asignacion, idCliente, uoc, idSucursal, fechaIngreso, idCaracteristicas
        );

        return commonInputRepository.findAll(specification);
    }

    public CommonInput createCommonInput(CommonInput commonInput) {
        return commonInputRepository.save(commonInput);
    }

    public CommonInput updateCommonInput(int id, CommonInput commonInput) throws ResourceNotFoundException {
        CommonInput commonInput1 = commonInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommonInput not found for this id :: " + id));

        commonInput1.setLastName(commonInput.getLastName());
        commonInput1.setName(commonInput.getName());
        commonInput1.setCuil(commonInput.getCuil());
        commonInput1.setFile(commonInput.getFile());
        commonInput1.setAllocation(commonInput.getAllocation());
        commonInput1.setClient(commonInput.getClient());
        commonInput1.setUoc(commonInput.getUoc());
        commonInput1.setBranch(commonInput.getBranch());
        commonInput1.setAdmissionDate(commonInput.getAdmissionDate());
        commonInput1.setFeatures(commonInput.getFeatures());
        commonInput1.setAudit(commonInput.getAudit());

        commonInputRepository.save(commonInput1);

        return commonInput1;
    }

    public void deleteCommonInput(int id) throws ResourceNotFoundException{
        commonInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommonInput not found for this id :: " + id));

        commonInputRepository.deleteById(id);
    }
}
