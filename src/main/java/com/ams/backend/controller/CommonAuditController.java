package com.ams.backend.controller;

import com.ams.backend.entity.CommonAudit;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.CommonAuditService;
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
public class CommonAuditController {

    @Autowired
    private CommonAuditService commonAuditService;

    @GetMapping("/commonAudit")
    public List<CommonAudit> getAllCommonAudits() {

        return commonAuditService.getAllCommonAudits();
    }

    @GetMapping("/commonAudit/{id}")
    public ResponseEntity<CommonAudit> getCommonAuditById(@PathVariable(value = "id") int commonAuditId)
            throws ResourceNotFoundException{
        CommonAudit commonAudit = commonAuditService.getCommonAuditById(commonAuditId);

        return ResponseEntity.ok().body(commonAudit);
    }

    @PostMapping("/commonAudit")
    public CommonAudit createCommonAudit(@Valid @RequestBody CommonAudit commonAudit) {
        return commonAuditService.createCommonAudit(commonAudit);
    }

    @PutMapping("/commonAudit/{id}")
    public ResponseEntity<CommonAudit> updateCommonAudit(
            @PathVariable(value = "id") int commonAuditId,
            @Valid @RequestBody CommonAudit commonAuditDetails) throws ResourceNotFoundException {
        final CommonAudit updatedCommonAudit = commonAuditService.updateCommonAudit(commonAuditId, commonAuditDetails);

        return ResponseEntity.ok(updatedCommonAudit);
    }

    @DeleteMapping("/commonAudit/{id}")
    public ResponseEntity<Void> deleteCommonAudit(@PathVariable(value = "id") int commonAuditId)
            throws ResourceNotFoundException {
        commonAuditService.deleteCommonAudit(commonAuditId);

        return ResponseEntity.noContent().build();
    }
}
