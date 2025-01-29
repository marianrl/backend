package com.ams.backend.controller;

import java.util.List;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.interfaces.BranchService;
import com.ams.backend.entity.Branch;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/v1")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping("/branch")
    public List<Branch> getAllBranches() {

        return branchService.getAllBranches();
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable(value = "id") int branchId)
            throws ResourceNotFoundException{
        Branch branch = branchService.getBranchById(branchId);

        return ResponseEntity.ok().body(branch);
    }

    @PostMapping("/branch")
    public Branch createBranch(@Valid @RequestBody Branch branch) {
        return branchService.createBranch(branch);
    }

    @PutMapping("/branch/{id}")
    public ResponseEntity<Branch> updateBranch(
            @PathVariable(value = "id") int branchId,
            @Valid @RequestBody Branch branchDetails) throws ResourceNotFoundException {
        final Branch updatedBranch = branchService.updateBranch(branchId, branchDetails);

        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/branch/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable(value = "id") int branchId)
            throws ResourceNotFoundException {
        branchService.deleteBranch(branchId);

        return ResponseEntity.noContent().build();
    }
}
