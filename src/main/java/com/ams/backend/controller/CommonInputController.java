package com.ams.backend.controller;

import com.ams.backend.entity.CommonInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.CommonInputService;
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

@RestController
@RequestMapping("/api/v1")
public class CommonInputController {

    @Autowired
    private CommonInputService commonInputService;

    @GetMapping("/commonInput")
    public List<CommonInput> getAllCommonInputs() {

        return commonInputService.getAllCommonInputs();
    }

    @GetMapping("/commonInput/{id}")
    public ResponseEntity<List<CommonInput>> getCommonAuditByAuditNumber(@PathVariable(value = "id") int auditNumber)
    {
        List<CommonInput> commonInputs = commonInputService.getCommonInputByAuditNumber(auditNumber);

        return ResponseEntity.ok().body(commonInputs);
    }

    @GetMapping("/commonInput/filtered")
    public List<CommonInput> getFilteredCommonInputs(
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

        return commonInputService.getFilteredCommonInputs(
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

    @PostMapping("/commonInput")
    public CommonInput createCommonInput(@Valid @RequestBody CommonInput commonInput) {
        return commonInputService.createCommonInput(commonInput);
    }

    @PutMapping("/commonInput/{id}")
    public ResponseEntity<CommonInput> updateCommonInput(
            @PathVariable(value = "id") int commonInputId,
            @Valid @RequestBody CommonInput commonInputDetails) throws ResourceNotFoundException {
        final CommonInput updatedCommonInput = commonInputService.updateCommonInput(commonInputId, commonInputDetails);

        return ResponseEntity.ok(updatedCommonInput);
    }

    @DeleteMapping("/commonInput/{id}")
    public ResponseEntity<Void> deleteCommonInput(@PathVariable(value = "id") int commonInputId)
            throws ResourceNotFoundException {
        commonInputService.deleteCommonInput(commonInputId);

        return ResponseEntity.noContent().build();
    }
}
