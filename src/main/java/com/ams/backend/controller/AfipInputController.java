package com.ams.backend.controller;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AfipInputUpdateRequest;
import com.ams.backend.service.interfaces.AfipInputService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AfipInputController {

    @Autowired
    private AfipInputService afipInputService;

    @GetMapping("/afipInput")
    public List<AfipInput> getAllAfipAudits() {
        return afipInputService.getAllAfipInputs();
    }

    @GetMapping("/afipInputById/{id}")
    public Optional<AfipInput> getAfipInputById(@PathVariable(value = "id") int afipInputId) {

        return afipInputService.getAfipInputById(afipInputId);
    }

    @GetMapping("/afipInput/{id}")
    public ResponseEntity<List<AfipInput>> getAfipAuditByAuditNumber(@PathVariable(value = "id") int auditNumber)
    {
        List<AfipInput> afipInput = afipInputService.getAfipInputByAuditNumber(auditNumber);

        return ResponseEntity.ok().body(afipInput);
    }

    @GetMapping("/afipInput/filtered")
    public List<AfipInput> getFilteredAfipInputs(
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String cuil,
            @RequestParam(required = false) String legajo,
            @RequestParam(required = false) String asignacion,
            @RequestParam(required = false) Long idCliente,
            @RequestParam(required = false) String uoc,
            @RequestParam(required = false) Long idSucursal,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaIngreso,
            @RequestParam(required = false) Long idCaracteristicas
    ) {

        return afipInputService.getFilteredAfipInputs(
                apellido,
                nombre,
                cuil,
                legajo,
                asignacion,
                idCliente,
                uoc,
                idSucursal,
                fechaIngreso,
                idCaracteristicas
        );
    }

    @PostMapping("/afipInput")
    public AfipInput createAfipAudit(@Valid @RequestBody AfipInput afipInput) {
        return afipInputService.createAfipInput(afipInput);
    }

    @PutMapping("/afipInput/{id}")
    public ResponseEntity<AfipInput> updateAfipAudit(
            @PathVariable(value = "id") int afipInputId,
            @Valid @RequestBody AfipInputUpdateRequest afipInputUpdateRequest) throws ResourceNotFoundException {
        final AfipInput updatedAfipInput = afipInputService.updateAfipInput(afipInputId, afipInputUpdateRequest);

        return ResponseEntity.ok(updatedAfipInput);
    }

    @DeleteMapping("/afipInput/{id}")
    public ResponseEntity<Void> deleteAfipAudit(@PathVariable(value = "id") int afipAuditId)
            throws ResourceNotFoundException {
        afipInputService.deleteAfipInput(afipAuditId);

        return ResponseEntity.noContent().build();
    }
}
