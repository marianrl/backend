package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Branch;
import com.ams.backend.repository.BranchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    private BranchService branchService;

    @BeforeEach
    public void setup() {
        branchService = new BranchService(branchRepository);
    }

    @Test
    public void testGetAllBranches() {
        List<Branch> expectedBranches = new ArrayList<>();
        Mockito.when(branchRepository.findAll()).thenReturn(expectedBranches);
        List<Branch> actualBranches = branchService.getAllBranches();

        assertEquals(expectedBranches, actualBranches);
    }

    @Test
    public void testGetBranchById() throws ResourceNotFoundException {
        int branchId = 1;
        Branch expectedBranch = new Branch(branchId, "CABA");

        Mockito.when(branchRepository.findById(branchId)).thenReturn(Optional.of(expectedBranch));
        Branch actualBranch = branchService.getBranchById(branchId);

        assertEquals(expectedBranch, actualBranch);
    }

    @Test
    public void testCreateBranch() {
        int branchId = 1;
        Branch branch = new Branch(branchId, "CABA");

        Mockito.when(branchRepository.save(branch)).thenReturn(branch);
        Branch actualBranch = branchService.createBranch(branch);

        assertEquals(actualBranch, branch);
    }

    @Test
    public void testUpdateBranch() throws ResourceNotFoundException {
        int branchId = 1;
        Branch branch = new Branch(branchId, "CABA");
        Branch updatedBranch = new Branch(branchId, "GBA");

        Mockito.when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        Branch actualBranch = branchService.updateBranch(branchId, updatedBranch);

        assertEquals(updatedBranch.getId(), actualBranch.getId());
        assertEquals(updatedBranch.getBranch(), actualBranch.getBranch());
    }

    @Test
    public void testDeleteBranch() throws ResourceNotFoundException {
        int branchId = 1;
        Branch branch = new Branch(branchId, "CABA");

        Mockito.when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        branchService.deleteBranch(branchId);

        verify(branchRepository).deleteById(1);
    }
}

