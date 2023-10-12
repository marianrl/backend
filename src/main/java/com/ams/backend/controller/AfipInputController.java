package com.ams.backend.controller;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.AfipInputService;
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
public class AfipInputController {

    @Autowired
    private AfipInputService afipInputService;

    @GetMapping("/afipAudit")
    public List<AfipInput> getAllAfipAudits() {

        return afipInputService.getAllAfipInputs();
    }

    @GetMapping("/afipAudit/{id}")
    public ResponseEntity<List<AfipInput>> getAfipAuditByAuditNumber(@PathVariable(value = "id") int auditNumber)
            throws ResourceNotFoundException{
        List<AfipInput> afipInput = afipInputService.getAfipInputByAuditNumber(auditNumber);

        return ResponseEntity.ok().body(afipInput);
    }

    @PostMapping("/afipAudit")
    public AfipInput createAfipAudit(@Valid @RequestBody AfipInput afipInput) {
        return afipInputService.createAfipInput(afipInput);
    }

    @PutMapping("/afipAudit/{id}")
    public ResponseEntity<AfipInput> updateAfipAudit(
            @PathVariable(value = "id") int afipAuditId,
            @Valid @RequestBody AfipInput afipInputDetails) throws ResourceNotFoundException {
        final AfipInput updatedAfipInput = afipInputService.updateAfipInput(afipAuditId, afipInputDetails);

        return ResponseEntity.ok(updatedAfipInput);
    }

    @DeleteMapping("/afipAudit/{id}")
    public ResponseEntity<Void> deleteAfipAudit(@PathVariable(value = "id") int afipAuditId)
            throws ResourceNotFoundException {
        afipInputService.deleteAfipInput(afipAuditId);

        return ResponseEntity.noContent().build();
    }
}
