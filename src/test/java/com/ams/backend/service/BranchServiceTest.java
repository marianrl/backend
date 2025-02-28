package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.Branch;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    private BranchServiceImpl branchService;

    private Branch branch;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        branchService = new BranchServiceImpl(branchRepository);

        // Crear una sucursal de ejemplo
        branch = new Branch();
        branch.setId(1);
        branch.setBranch("Test Branch");
    }

    @Test
    public void testGetAllBranches() {
        List<Branch> branches = Arrays.asList(branch);

        when(branchRepository.findAll()).thenReturn(branches);

        List<Branch> result = branchService.getAllBranches();

        assertEquals(1, result.size());
        assertEquals(branch.getBranch(), result.get(0).getBranch());
        verify(branchRepository, times(1)).findAll();
    }

    @Test
    public void testGetBranchById_BranchFound() throws ResourceNotFoundException {
        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));

        Branch result = branchService.getBranchById(1);

        assertNotNull(result);
        assertEquals(branch.getBranch(), result.getBranch());
        verify(branchRepository, times(1)).findById(1);
    }

    @Test
    public void testGetBranchById_BranchNotFound() {
        when(branchRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> branchService.getBranchById(999));

        verify(branchRepository, times(1)).findById(999);
    }

    @Test
    public void testCreateBranch() {
        Branch newBranch = new Branch();
        newBranch.setBranch("New Branch");

        when(branchRepository.save(newBranch)).thenReturn(newBranch);

        Branch result = branchService.createBranch(newBranch);

        assertNotNull(result);
        assertEquals("New Branch", result.getBranch());
        verify(branchRepository, times(1)).save(newBranch);
    }

    @Test
    public void testUpdateBranch_BranchFound() throws ResourceNotFoundException {
        Branch updatedBranch = new Branch();
        updatedBranch.setBranch("Updated Branch");

        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        when(branchRepository.save(any(Branch.class))).thenReturn(updatedBranch);

        Branch result = branchService.updateBranch(1, updatedBranch);

        assertNotNull(result);
        assertEquals("Updated Branch", result.getBranch());
        verify(branchRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateBranch_BranchNotFound() {
        Branch updatedBranch = new Branch();
        updatedBranch.setBranch("Updated Branch");

        when(branchRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> branchService.updateBranch(999, updatedBranch));

        verify(branchRepository, times(1)).findById(999);
        verify(branchRepository, times(0)).save(updatedBranch);  // No debe llamar a save
    }

    @Test
    public void testDeleteBranch_BranchFound() throws ResourceNotFoundException {
        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        doNothing().when(branchRepository).deleteById(1);

        branchService.deleteBranch(1);

        verify(branchRepository, times(1)).findById(1);
        verify(branchRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteBranch_BranchNotFound() {
        when(branchRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> branchService.deleteBranch(999));

        verify(branchRepository, times(1)).findById(999);
        verify(branchRepository, times(0)).deleteById(999);  // No debe eliminarse
    }
}
