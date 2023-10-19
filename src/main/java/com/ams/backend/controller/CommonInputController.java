package com.ams.backend.controller;

import com.ams.backend.entity.CommonInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.CommonInputService;
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
public class CommonInputController {

    @Autowired
    private CommonInputService commonInputService;

    @GetMapping("/commonInput")
    public List<CommonInput> getAllCommonInputs() {

        return commonInputService.getAllCommonInputs();
    }

    @GetMapping("/commonInput/{id}")
    public ResponseEntity<List<CommonInput>> getCommonInputById(@PathVariable(value = "id") int commonInputId)
    {
        List<CommonInput> commonInput = commonInputService.getCommonInputByAuditNumber(commonInputId);

        return ResponseEntity.ok().body(commonInput);
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
