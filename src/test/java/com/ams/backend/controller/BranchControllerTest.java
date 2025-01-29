package com.ams.backend.controller;

import com.ams.backend.entity.Branch;
import com.ams.backend.service.interfaces.BranchService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

public class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    private Branch branch;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        branch = new Branch();
        branch.setId(1);
    }

    @Test
    public void getAllBranchesTest() throws Exception {
        List<Branch> branches = Collections.singletonList(branch);
        when(branchService.getAllBranches()).thenReturn(branches);

        List<Branch> result = branchController.getAllBranches();

        assertEquals(branches, result);
        verify(branchService, times(1)).getAllBranches();
    }

    @Test
    public void getBranchByIdTest() throws Exception {
        when(branchService.getBranchById(1)).thenReturn(branch);

        ResponseEntity<Branch> result = branchController.getBranchById(1);

        assertEquals(branch, result.getBody());
        verify(branchService, times(1)).getBranchById(1);
    }

    @Test
    public void createBranchTest() throws Exception {
        when(branchService.createBranch(any(Branch.class))).thenReturn(branch);

        Branch result = branchController.createBranch(branch);

        assertEquals(branch, result);
        verify(branchService, times(1)).createBranch(any(Branch.class));
    }

    @Test
    public void updateBranchTest() throws Exception {
        Branch branch = new Branch();
        when(branchService.updateBranch(eq(1), any(Branch.class))).thenReturn(branch);

        ResponseEntity<Branch> result = branchController.updateBranch(1, branch);

        assertEquals(branch, result.getBody());
        verify(branchService, times(1)).updateBranch(eq(1), any(Branch.class));
    }

    @Test
    public void deleteBranchTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(branchService).deleteBranch(1);

        ResponseEntity<Void> result = branchController.deleteBranch(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(branchService, times(1)).deleteBranch(1);
    }

}
