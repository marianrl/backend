package com.ams.backend.controller;

import com.ams.backend.entity.AfipAudit;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.AfipAuditService;
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
public class AfipAuditController {

    @Autowired
    private AfipAuditService afipAuditService;

    @GetMapping("/afipAudit")
    public List<AfipAudit> getAllAfipAudits() {

        return afipAuditService.getAllAfipAudits();
    }

    @GetMapping("/afipAudit/{id}")
    public ResponseEntity<AfipAudit> getAfipAuditById(@PathVariable(value = "id") int afipAuditId)
            throws ResourceNotFoundException{
        AfipAudit afipAudit = afipAuditService.getAfipAuditById(afipAuditId);

        return ResponseEntity.ok().body(afipAudit);
    }

    @PostMapping("/afipAudit")
    public AfipAudit createAfipAudit(@Valid @RequestBody AfipAudit afipAudit) {
        return afipAuditService.createAfipAudit(afipAudit);
    }

    @PutMapping("/afipAudit/{id}")
    public ResponseEntity<AfipAudit> updateAfipAudit(
            @PathVariable(value = "id") int afipAuditId,
            @Valid @RequestBody AfipAudit afipAuditDetails) throws ResourceNotFoundException {
        final AfipAudit updatedAfipAudit = afipAuditService.updateAfipAudit(afipAuditId, afipAuditDetails);

        return ResponseEntity.ok(updatedAfipAudit);
    }

    @DeleteMapping("/afipAudit/{id}")
    public ResponseEntity<Void> deleteAfipAudit(@PathVariable(value = "id") int afipAuditId)
            throws ResourceNotFoundException {
        afipAuditService.deleteAfipAudit(afipAuditId);

        return ResponseEntity.noContent().build();
    }
}
