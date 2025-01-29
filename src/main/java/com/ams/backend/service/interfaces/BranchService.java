package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.Branch;
import com.ams.backend.exception.ResourceNotFoundException;

public interface BranchService {
  List<Branch> getAllBranches();
  Branch getBranchById(int id) throws ResourceNotFoundException;
  Branch createBranch(Branch branch);
  Branch updateBranch(int id, Branch providedBranch) throws ResourceNotFoundException;
  void deleteBranch(int id) throws ResourceNotFoundException;
}