package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AuditRequest;
import com.ams.backend.response.AuditResponse;
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
    public List<AuditResponse> getAllAudits() {
        return auditService.getAllAudit();
    }

    @GetMapping("/audit/{id}")
    public ResponseEntity<AuditResponse> getAuditById(@PathVariable(value = "id") int auditId)
            throws ResourceNotFoundException {
        AuditResponse audit = auditService.getAuditById(auditId);
        return ResponseEntity.ok().body(audit);
    }

    @PostMapping("/audit")
    public AuditResponse createAudit(@Valid @RequestBody AuditRequest auditRequest) throws ResourceNotFoundException {
        return auditService.createAudit(auditRequest);
    }

    @PutMapping("/audit/{id}")
    public ResponseEntity<AuditResponse> updateAudit(
            @PathVariable(value = "id") int auditId,
            @Valid @RequestBody AuditRequest auditRequest) throws ResourceNotFoundException {
        AuditResponse updatedAudit = auditService.updateAudit(auditId, auditRequest);
        return ResponseEntity.ok(updatedAudit);
    }

    @DeleteMapping("/audit/{id}")
    public ResponseEntity<Void> deleteAudit(@PathVariable(value = "id") int auditId)
            throws ResourceNotFoundException {
        auditService.deleteAudit(auditId);
        return ResponseEntity.noContent().build();
    }
}
