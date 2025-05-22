package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.Audit;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Branch;
import com.ams.backend.entity.Client;
import com.ams.backend.entity.CommonInput;
import com.ams.backend.entity.CommonInputSpecification;
import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.CommonInputMapper;
import com.ams.backend.repository.AnswerRepository;
import com.ams.backend.repository.AuditRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.repository.CommonInputRepository;
import com.ams.backend.repository.FeaturesRepository;
import com.ams.backend.request.CommonInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.service.interfaces.CommonInputService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private CommonInputMapper mapper;

    @Override
    public CommonInputMapper getMapper() {
        return mapper;
    }

    public List<CommonInput> getAllCommonInputs() {
        return commonInputRepository.findAll();
    }

    public List<CommonInput> getCommonInputByAuditNumber(int id) {
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
                apellido, nombre, cuil, legajo, asignacion, idCliente, uoc, idSucursal, fechaIngreso,
                idCaracteristicas);

        return commonInputRepository.findAll(specification);
    }

    public CommonInput createCommonInput(InputRequest inputRequest) throws ResourceNotFoundException {
        CommonInput commonInput = new CommonInput();

        // Set basic fields from request
        commonInput.setLastName(inputRequest.getLastName());
        commonInput.setName(inputRequest.getName());
        commonInput.setCuil(inputRequest.getCuil());
        commonInput.setFile(inputRequest.getFile());
        commonInput.setAllocation(inputRequest.getAllocation());
        commonInput.setUoc(inputRequest.getUoc());
        commonInput.setAdmissionDate(inputRequest.getAdmissionDate());

        // Set client
        Client client = new Client();
        client.setId(inputRequest.getClient());
        commonInput.setClient(client);

        // Set branch
        Branch branch = new Branch();
        branch.setId(inputRequest.getBranch());
        commonInput.setBranch(branch);

        // Set audit
        Audit audit = auditRepository.findById(inputRequest.getAuditId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Audit not found for id :: " + inputRequest.getAuditId()));
        commonInput.setAudit(audit);

        // seteamos la caracteristica 49 por defecto, recien creado y sin respuesta
        Features features = featuresRepository.findById(49)
                .orElseThrow(() -> new ResourceNotFoundException("Features not found for id :: 49"));
        commonInput.setFeatures(features);

        return commonInputRepository.save(commonInput);
    }

    public CommonInput updateCommonInput(int id, CommonInputUpdateRequest commonInputUpdateRequest)
            throws ResourceNotFoundException {

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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Answer not found with id :: " + commonInputUpdateRequest.getAnswerId()));

        AuditType existingNewAuditType = auditTypeRepository.findById(commonInputUpdateRequest.getAuditTypeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "AuditType not found with id :: " + commonInputUpdateRequest.getAuditTypeId()));

        Features updatedFeature = featuresRepository.findByAuditTypeAndAnswer(existingNewAuditType, existingAnswer);

        commonInputToUpdate.setFeatures(updatedFeature);

        // Guardar CommonInput actualizado en la base de datos
        return commonInputRepository.save(commonInputToUpdate);
    }

    public void deleteCommonInput(int id) throws ResourceNotFoundException {
        commonInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CommonInput not found for this id :: " + id));

        commonInputRepository.deleteById(id);
    }

    @Override
    public List<CommonInput> createCommonInputs(List<InputRequest> inputRequests) throws ResourceNotFoundException {
        List<CommonInput> createdInputs = new ArrayList<>();

        for (InputRequest inputRequest : inputRequests) {
            CommonInput commonInput = new CommonInput();

            commonInput.setLastName(inputRequest.getLastName());
            commonInput.setName(inputRequest.getName());
            commonInput.setCuil(inputRequest.getCuil());
            commonInput.setFile(inputRequest.getFile());
            commonInput.setAllocation(inputRequest.getAllocation());
            commonInput.setUoc(inputRequest.getUoc());
            commonInput.setAdmissionDate(inputRequest.getAdmissionDate());

            // Set client
            Client client = new Client();
            client.setId(inputRequest.getClient());
            commonInput.setClient(client);

            // Set branch
            Branch branch = new Branch();
            branch.setId(inputRequest.getBranch());
            commonInput.setBranch(branch);

            // Set audit
            Audit audit = auditRepository.findById(inputRequest.getAuditId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Audit not found for id :: " + inputRequest.getAuditId()));
            commonInput.setAudit(audit);

            // Set features (default 49)
            Features features = featuresRepository.findById(49)
                    .orElseThrow(() -> new ResourceNotFoundException("Features not found for id :: 49"));
            commonInput.setFeatures(features);

            createdInputs.add(commonInputRepository.save(commonInput));
        }

        return createdInputs;
    }
}
