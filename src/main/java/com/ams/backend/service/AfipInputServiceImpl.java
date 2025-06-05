package com.ams.backend.service;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.entity.Answer;
import com.ams.backend.entity.Audit;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Branch;
import com.ams.backend.entity.Client;
import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.AfipInputMapper;
import com.ams.backend.repository.AfipInputRepository;
import com.ams.backend.repository.AnswerRepository;
import com.ams.backend.repository.AuditRepository;
import com.ams.backend.repository.AuditTypeRepository;
import com.ams.backend.repository.FeaturesRepository;
import com.ams.backend.request.AfipInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.response.AfipInputResponse;
import com.ams.backend.service.interfaces.AfipInputService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AfipInputServiceImpl implements AfipInputService {

        @Autowired
        private AfipInputRepository afipInputRepository;

        @Autowired
        private FeaturesRepository featuresRepository;

        @Autowired
        private AnswerRepository answerRepository;

        @Autowired
        private AuditTypeRepository auditTypeRepository;

        @Autowired
        private AuditRepository auditRepository;

        @Autowired
        private AfipInputMapper afipInputMapper;

        public List<AfipInputResponse> getAllAfipInputs() {
                return afipInputRepository.findAll().stream()
                                .map(afipInputMapper::toResponse)
                                .collect(Collectors.toList());
        }

        public Optional<AfipInputResponse> getAfipInputById(int id) {
                return afipInputRepository.findById(id)
                                .map(afipInputMapper::toResponse);
        }

        public List<AfipInputResponse> getAfipInputByAuditNumber(int id) {
                return afipInputRepository.findByAudit_Id(id).stream()
                                .map(afipInputMapper::toResponse)
                                .collect(Collectors.toList());
        }

        public AfipInputResponse createAfipInput(InputRequest inputRequest) throws ResourceNotFoundException {
                AfipInput afipInput = new AfipInput();

                afipInput.setLastName(inputRequest.getLastName());
                afipInput.setName(inputRequest.getName());
                afipInput.setCuil(inputRequest.getCuil());
                afipInput.setFile(inputRequest.getFile());
                afipInput.setAllocation(inputRequest.getAllocation());
                afipInput.setUoc(inputRequest.getUoc());
                afipInput.setAdmissionDate(inputRequest.getAdmissionDate());

                // Set client
                Client client = new Client();
                client.setId(inputRequest.getClient());
                afipInput.setClient(client);

                // Set branch
                Branch branch = new Branch();
                branch.setId(inputRequest.getBranch());
                afipInput.setBranch(branch);

                // Set audit
                Audit audit = auditRepository.findById(inputRequest.getAuditId())
                                .orElseThrow(
                                                () -> new ResourceNotFoundException("Audit not found for id :: "
                                                                + inputRequest.getAuditId()));
                afipInput.setAudit(audit);

                // seteamos la caracteristica 49 por defecto, recien creado y sin respuesta
                Features features = featuresRepository.findById(49)
                                .orElseThrow(() -> new ResourceNotFoundException("Features not found for id :: 49"));
                afipInput.setFeatures(features);

                return afipInputMapper.toResponse(afipInputRepository.save(afipInput));
        }

        public AfipInputResponse updateAfipInput(int id, AfipInputUpdateRequest afipInputUpdateRequest)
                        throws ResourceNotFoundException {

                AfipInput afipInputToUpdate = afipInputRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "AfipInput not found for this id :: " + id));

                // Asignar los valores simples que no son FK
                afipInputToUpdate.setLastName(afipInputUpdateRequest.getLastName());
                afipInputToUpdate.setName(afipInputUpdateRequest.getName());
                afipInputToUpdate.setCuil(afipInputUpdateRequest.getCuil());
                afipInputToUpdate.setFile(afipInputUpdateRequest.getFile());
                afipInputToUpdate.setAllocation(afipInputUpdateRequest.getAllocation());
                afipInputToUpdate.setUoc(afipInputUpdateRequest.getUoc());
                afipInputToUpdate.setAdmissionDate(afipInputUpdateRequest.getAdmissionDate());
                // Otros campos que deseas actualizar

                // Actualizar la relación de Answer en Features según el request
                Answer existingNewAnswer = answerRepository.findById(afipInputUpdateRequest.getAnswerId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Answer not found with id :: " + afipInputUpdateRequest.getAnswerId()));

                AuditType existingNewAuditType = auditTypeRepository.findById(afipInputUpdateRequest.getAuditTypeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "AuditType not found with id :: "
                                                                + afipInputUpdateRequest.getAuditTypeId()));

                Features updatedFeature = featuresRepository.findByAuditTypeAndAnswer(existingNewAuditType,
                                existingNewAnswer);

                afipInputToUpdate.setFeatures(updatedFeature);

                // Guardar CommonInput actualizado en la base de datos
                return afipInputMapper.toResponse(afipInputRepository.save(afipInputToUpdate));
        }

        public void deleteAfipInput(int id) throws ResourceNotFoundException {
                afipInputRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "AfipAudit not found for this id :: " + id));

                afipInputRepository.deleteById(id);
        }

        @Override
        public List<AfipInputResponse> createAfipInputs(List<InputRequest> inputRequests)
                        throws ResourceNotFoundException {
                List<AfipInputResponse> createdInputs = new ArrayList<>();

                for (InputRequest inputRequest : inputRequests) {
                        AfipInput afipInput = new AfipInput();

                        afipInput.setLastName(inputRequest.getLastName());
                        afipInput.setName(inputRequest.getName());
                        afipInput.setCuil(inputRequest.getCuil());
                        afipInput.setFile(inputRequest.getFile());
                        afipInput.setAllocation(inputRequest.getAllocation());
                        afipInput.setUoc(inputRequest.getUoc());
                        afipInput.setAdmissionDate(inputRequest.getAdmissionDate());

                        // Set client
                        Client client = new Client();
                        client.setId(inputRequest.getClient());
                        afipInput.setClient(client);

                        // Set branch
                        Branch branch = new Branch();
                        branch.setId(inputRequest.getBranch());
                        afipInput.setBranch(branch);

                        // Set audit
                        Audit audit = auditRepository.findById(inputRequest.getAuditId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Audit not found for id :: " + inputRequest.getAuditId()));
                        afipInput.setAudit(audit);

                        // Set features (default 49)
                        Features features = featuresRepository.findById(49)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Features not found for id :: 49"));
                        afipInput.setFeatures(features);

                        createdInputs.add(afipInputMapper.toResponse(afipInputRepository.save(afipInput)));
                }

                return createdInputs;
        }
}
