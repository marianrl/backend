package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AfipInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.response.AfipInputResponse;
import com.ams.backend.service.interfaces.AfipInputService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AfipInputController {

    @Autowired
    private AfipInputService afipInputService;

    @GetMapping("/afipInput")
    public List<AfipInputResponse> getAllAfipInputs() {
        return afipInputService.getAllAfipInputs();
    }

    @GetMapping("/afipInputById/{id}")
    public Optional<AfipInputResponse> getAfipInputById(@PathVariable(value = "id") int afipInputId) {
        return afipInputService.getAfipInputById(afipInputId);
    }

    @GetMapping("/afipInput/{id}")
    public ResponseEntity<List<AfipInputResponse>> getAfipInputByAuditNumber(
            @PathVariable(value = "id") int auditNumber) {
        List<AfipInputResponse> afipInput = afipInputService.getAfipInputByAuditNumber(auditNumber);
        return ResponseEntity.ok().body(afipInput);
    }

    @GetMapping("/afipInput/filtered")
    public List<AfipInputResponse> getFilteredAfipInputs(
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String cuil,
            @RequestParam(required = false) String legajo,
            @RequestParam(required = false) String asignacion,
            @RequestParam(required = false) Long idCliente,
            @RequestParam(required = false) String uoc,
            @RequestParam(required = false) Long idSucursal,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaIngreso,
            @RequestParam(required = false) Long idCaracteristicas) {

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
                idCaracteristicas);
    }

    @PostMapping("/afipInput")
    public List<AfipInputResponse> createAfipInput(@Valid @RequestBody List<InputRequest> inputRequests)
            throws ResourceNotFoundException {
        return afipInputService.createAfipInputs(inputRequests);
    }

    @PutMapping("/afipInput/{id}")
    public ResponseEntity<AfipInputResponse> updateAfipInput(
            @PathVariable(value = "id") int afipInputId,
            @Valid @RequestBody AfipInputUpdateRequest afipInputUpdateRequest) throws ResourceNotFoundException {
        final AfipInputResponse updatedAfipInput = afipInputService.updateAfipInput(afipInputId,
                afipInputUpdateRequest);
        return ResponseEntity.ok(updatedAfipInput);
    }

    @DeleteMapping("/afipInput/{id}")
    public ResponseEntity<Void> deleteAfipInput(@PathVariable(value = "id") int afipAuditId)
            throws ResourceNotFoundException {
        afipInputService.deleteAfipInput(afipAuditId);
        return ResponseEntity.noContent().build();
    }
}
