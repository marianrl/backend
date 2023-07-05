package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.model.Branch;
import com.ams.backend.repository.BranchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    private BranchService branchService;

    @Before
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
        Long branchId = 1L;
        Branch expectedBranch = new Branch(branchId, "CABA");

        Mockito.when(branchRepository.findById(branchId)).thenReturn(Optional.of(expectedBranch));
        Branch actualBranch = branchService.getBranchById(branchId);

        assertEquals(expectedBranch, actualBranch);
    }

    @Test
    public void testCreateBranch() {
        Long branchId = 1L;
        Branch branch = new Branch(branchId, "CABA");

        Mockito.when(branchRepository.save(branch)).thenReturn(branch);
        Branch actualBranch = branchService.createBranch(branch);

        assertEquals(actualBranch, branch);
    }

    @Test
    public void testUpdateBranch() throws ResourceNotFoundException {
        Long branchId = 1L;
        Branch branch = new Branch(branchId, "CABA");
        Branch updatedBranch = new Branch(branchId, "GBA");

        Mockito.when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        Branch actualBranch = branchService.updateBranch(branchId, updatedBranch);

        assertEquals(updatedBranch.getId(), actualBranch.getId());
        assertEquals(updatedBranch.getBranch(), actualBranch.getBranch());
    }

    @Test
    public void testDeleteBranch() throws ResourceNotFoundException {
        Long branchId = 1L;
        Branch branch = new Branch(branchId, "CABA");

        Mockito.when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        branchService.deleteBranch(branchId);

        verify(branchRepository).deleteById(1L);
    }
}

