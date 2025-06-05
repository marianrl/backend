package com.ams.backend.controller;

import com.ams.backend.entity.CommonInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.CommonInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.response.CommonInputResponse;
import com.ams.backend.service.interfaces.CommonInputService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CommonInputController {

    @Autowired
    private CommonInputService commonInputService;

    @GetMapping("/commonInput")
    public List<CommonInputResponse> getAllCommonInputs() {
        return commonInputService.getAllCommonInputs().stream()
                .map(commonInputService.getMapper()::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/commonInputById/{id}")
    public Optional<CommonInputResponse> getCommonInputById(@PathVariable(value = "id") int commonInputId) {
        return commonInputService.getCommonInputById(commonInputId)
                .map(commonInputService.getMapper()::toResponse);
    }

    @GetMapping("/commonInput/{id}")
    public ResponseEntity<List<CommonInputResponse>> getCommonAuditByAuditNumber(
            @PathVariable(value = "id") int auditNumber) {
        List<CommonInputResponse> commonInputs = commonInputService.getCommonInputByAuditNumber(auditNumber).stream()
                .map(commonInputService.getMapper()::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(commonInputs);
    }

    @PostMapping("/commonInput")
    public List<CommonInput> createCommonInput(@Valid @RequestBody List<InputRequest> inputRequests)
            throws ResourceNotFoundException {
        return commonInputService.createCommonInputs(inputRequests);
    }

    @PutMapping("/commonInput/{id}")
    public ResponseEntity<CommonInput> updateCommonInput(
            @PathVariable(value = "id") int commonInputId,
            @Valid @RequestBody CommonInputUpdateRequest commonInputUpdateRequest) throws ResourceNotFoundException {
        final CommonInput updatedCommonInput = commonInputService.updateCommonInput(commonInputId,
                commonInputUpdateRequest);
        return ResponseEntity.ok(updatedCommonInput);
    }

    @DeleteMapping("/commonInput/{id}")
    public ResponseEntity<Void> deleteCommonInput(@PathVariable(value = "id") int commonInputId)
            throws ResourceNotFoundException {
        commonInputService.deleteCommonInput(commonInputId);
        return ResponseEntity.noContent().build();
    }
}
