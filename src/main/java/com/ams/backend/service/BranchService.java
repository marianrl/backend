package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Branch;
import com.ams.backend.repository.BranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch getBranchById(int id) throws ResourceNotFoundException {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found for this id :: " + id));
    }

    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch updateBranch(int id, Branch providedBranch) throws ResourceNotFoundException {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found for this id :: " + id));

        branch.setBranch(providedBranch.getBranch());
        branchRepository.save(branch);

        return branch;
    }

    public void deleteBranch(int id) throws ResourceNotFoundException{
        branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found for this id :: " + id));

        branchRepository.deleteById(id);
    }
}
