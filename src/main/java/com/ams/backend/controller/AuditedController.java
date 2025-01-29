package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.interfaces.AuditedService;
import com.ams.backend.entity.Audited;
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
public class AuditedController {

    @Autowired
    private AuditedService auditedService;

    @GetMapping("/audited")
    public List<Audited> getAllAudited() {

        return auditedService.getAllAudited();
    }

    @GetMapping("/audited/{id}")
    public ResponseEntity<Audited> getAuditedById(@PathVariable(value = "id") int auditedId)
            throws ResourceNotFoundException{
        Audited audited = auditedService.getAuditedById(auditedId);

        return ResponseEntity.ok().body(audited);
    }

    @PostMapping("/audited")
    public Audited createAudited(@Valid @RequestBody Audited audited) {
        return auditedService.createAudited(audited);
    }

    @PutMapping("/audited/{id}")
    public ResponseEntity<Audited> updateAudited(
            @PathVariable(value = "id") int auditedId,
            @Valid @RequestBody Audited auditedDetails) throws ResourceNotFoundException {
        final Audited updatedAudited = auditedService.updateAudited(auditedId, auditedDetails);

        return ResponseEntity.ok(updatedAudited);
    }

    @DeleteMapping("/audited/{id}")
    public ResponseEntity<Void> deleteAudited(@PathVariable(value = "id") int auditedId)
            throws ResourceNotFoundException {
        auditedService.deleteAudited(auditedId);

        return ResponseEntity.noContent().build();
    }
}
