package com.ams.backend.controller;

import com.ams.backend.entity.Branch;
import com.ams.backend.service.BranchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(BranchController.class)
public class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchService branchService;

    @Test
    public void getAllBranchesTest() throws Exception {

        List<Branch> branches = new ArrayList<>();
        Mockito.when(branchService.getAllBranches()).thenReturn(branches);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/branch"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getBranchByIdTest() throws Exception {
        Branch branch = new Branch(1, "CABA");
        Mockito.when(branchService.getBranchById(1)).thenReturn(branch);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/branch/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.branch").value("CABA"));
    }

    @Test
    public void createBranchTest() throws Exception {
        Branch savedBranch = new Branch(1, "CABA");
        Mockito.when(branchService.createBranch(savedBranch)).thenReturn(savedBranch);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/branch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"branch\":\"CABA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateBranchTest() throws Exception {
        Branch updatedBranch = new Branch(1, "GBA");

        Mockito.when(branchService.updateBranch(1, updatedBranch)).thenReturn(updatedBranch);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/branch/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"branch\":\"GBA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(branchService, Mockito.times(1))
                .updateBranch(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Branch.class));
    }

    @Test
    public void deleteBranchTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/branch/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(branchService, Mockito.times(1)).deleteBranch(id);
    }

}
