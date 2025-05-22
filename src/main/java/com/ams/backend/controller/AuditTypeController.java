package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AuditTypeRequest;
import com.ams.backend.response.AuditTypeResponse;
import com.ams.backend.service.interfaces.AuditTypeService;

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

@RestController
@RequestMapping("/api/v1")
public class AuditTypeController {

    @Autowired
    private AuditTypeService auditTypeService;

    @GetMapping("/auditType")
    public List<AuditTypeResponse> getAllAuditType() {
        return auditTypeService.getAllAuditType();
    }

    @GetMapping("/auditType/{id}")
    public ResponseEntity<AuditTypeResponse> getAuditTypeById(@PathVariable(value = "id") int auditTypeId)
            throws ResourceNotFoundException {
        AuditTypeResponse auditType = auditTypeService.getAuditTypeById(auditTypeId);
        return ResponseEntity.ok().body(auditType);
    }

    @PostMapping("/auditType")
    public AuditTypeResponse createAuditType(@Valid @RequestBody AuditTypeRequest auditTypeRequest) {
        return auditTypeService.createAuditType(auditTypeRequest);
    }

    @PutMapping("/auditType/{id}")
    public ResponseEntity<AuditTypeResponse> updateAuditType(
            @PathVariable(value = "id") int auditTypeId,
            @Valid @RequestBody AuditTypeRequest auditTypeRequest) throws ResourceNotFoundException {
        AuditTypeResponse updatedAuditType = auditTypeService.updateAuditType(auditTypeId, auditTypeRequest);
        return ResponseEntity.ok(updatedAuditType);
    }

    @DeleteMapping("/auditType/{id}")
    public ResponseEntity<Void> deleteAuditType(@PathVariable(value = "id") int auditTypeId)
            throws ResourceNotFoundException {
        auditTypeService.deleteAuditType(auditTypeId);
        return ResponseEntity.noContent().build();
    }
}
