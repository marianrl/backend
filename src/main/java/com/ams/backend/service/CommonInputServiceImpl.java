package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.CommonInput;
import com.ams.backend.entity.CommonInputSpecification;
import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.AnswerRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.repository.CommonInputRepository;
import com.ams.backend.repository.FeaturesRepository;
import com.ams.backend.request.CommonInputUpdateRequest;
import com.ams.backend.service.interfaces.CommonInputService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommonInputServiceImpl implements CommonInputService {

    @Autowired
    private CommonInputRepository commonInputRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private FeaturesRepository featuresRepository;

    @Autowired
    private AuditTypeRepository auditTypeRepository;

    public List<CommonInput> getAllCommonInputs() {
        return commonInputRepository.findAll();
    }

    public List<CommonInput> getCommonInputByAuditNumber(int id){
        return commonInputRepository.findByAudit_Id(id);
    }

    public Optional<CommonInput> getCommonInputById(int id) {
        return commonInputRepository.findById(id);
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

    public CommonInput updateCommonInput(int id, CommonInputUpdateRequest commonInputUpdateRequest) throws ResourceNotFoundException {

        CommonInput commonInputToUpdate = commonInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommonInput not found for this id :: " + id));

        // Asignar los valores simples que no son FK
        commonInputToUpdate.setLastName(commonInputUpdateRequest.getLastName());
        commonInputToUpdate.setName(commonInputUpdateRequest.getName());
        commonInputToUpdate.setCuil(commonInputUpdateRequest.getCuil());
        commonInputToUpdate.setFile(commonInputUpdateRequest.getFile());
        commonInputToUpdate.setAllocation(commonInputUpdateRequest.getAllocation());
        commonInputToUpdate.setUoc(commonInputUpdateRequest.getUoc());
        commonInputToUpdate.setAdmissionDate(commonInputUpdateRequest.getAdmissionDate());
        // Otros campos que deseas actualizar

        // Actualizar la relación de Answer en Features según el request
        Answer existingAnswer = answerRepository.findById(commonInputUpdateRequest.getAnswerId())
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id :: " + commonInputUpdateRequest.getAnswerId()));

        AuditType existingNewAuditType = auditTypeRepository.findById(commonInputUpdateRequest.getAuditTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("AuditType not found with id :: " + commonInputUpdateRequest.getAuditTypeId()));

        Features updatedFeature = featuresRepository.findByAuditTypeAndAnswer(existingNewAuditType, existingAnswer);

        commonInputToUpdate.setFeatures(updatedFeature);

        // Guardar CommonInput actualizado en la base de datos
        return commonInputRepository.save(commonInputToUpdate);
    }

    public void deleteCommonInput(int id) throws ResourceNotFoundException{
        commonInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommonInput not found for this id :: " + id));

        commonInputRepository.deleteById(id);
    }
}
