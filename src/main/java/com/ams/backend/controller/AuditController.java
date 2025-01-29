package com.ams.backend.controller;

import com.ams.backend.entity.Audit;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.interfaces.AuditService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/audit")
    public List<Audit> getAllAudits() {

        return auditService.getAllAudit();
    }

    @GetMapping("/audit/{id}")
    public ResponseEntity<Audit> getAuditById(@PathVariable(value = "id") int auditId)
            throws ResourceNotFoundException{
        Audit audit = auditService.getAuditById(auditId);

        return ResponseEntity.ok().body(audit);
    }
    @PostMapping("/audit")
    public Audit createAudit(@Valid @RequestBody int auditTypeId) throws ResourceNotFoundException {
        return auditService.createAudit(auditTypeId);
    }

    @PutMapping("/audit/{id}")
    public ResponseEntity<Audit> updateAudit(
            @PathVariable(value = "id") int auditId,
            @Valid @RequestBody Audit auditDetails) throws ResourceNotFoundException {
        final Audit updatedAudit = auditService.updateAudit(auditId, auditDetails);

        return ResponseEntity.ok(updatedAudit);
    }

    @DeleteMapping("/audit/{id}")
    public ResponseEntity<Void> deleteAudit(@PathVariable(value = "id") int auditId)
            throws ResourceNotFoundException {
        auditService.deleteAudit(auditId);

        return ResponseEntity.noContent().build();
    }
}
