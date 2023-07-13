package com.ams.backend.controller;

import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.AuditTypeService;
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
    public List<AuditType> getAllAuditType() {

        return auditTypeService.getAllAuditType();
    }

    @GetMapping("/auditType/{id}")
    public ResponseEntity<AuditType> getAuditTypeById(@PathVariable(value = "id") int auditTypeId)
            throws ResourceNotFoundException{
        AuditType auditType = auditTypeService.getAuditTypeById(auditTypeId);

        return ResponseEntity.ok().body(auditType);
    }

    @PostMapping("/auditType")
    public AuditType createAudit(@Valid @RequestBody AuditType auditType) {
        return auditTypeService.createAuditType(auditType);
    }

    @PutMapping("/auditType/{id}")
    public ResponseEntity<AuditType> updateAuditType(
            @PathVariable(value = "id") int auditTypeId,
            @Valid @RequestBody AuditType auditTypeDetails) throws ResourceNotFoundException {
        final AuditType updatedAuditType = auditTypeService.updateAuditType(auditTypeId, auditTypeDetails);

        return ResponseEntity.ok(updatedAuditType);
    }

    @DeleteMapping("/auditType/{id}")
    public ResponseEntity<Void> deleteAuditType(@PathVariable(value = "id") int auditTypeId)
            throws ResourceNotFoundException {
        auditTypeService.deleteAuditType(auditTypeId);

        return ResponseEntity.noContent().build();
    }
}
