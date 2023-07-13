package com.ams.backend.controller;

import com.ams.backend.entity.Audited;
import com.ams.backend.service.AuditedService;
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
@WebMvcTest(AuditedController.class)
public class AuditedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditedService auditedService;

    @Test
    public void getAllAuditedTest() throws Exception {

        List<Audited> audited = new ArrayList<>();
        Mockito.when(auditedService.getAllAudited()).thenReturn(audited);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/audited"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAuditedByIdTest() throws Exception {
        Audited audited = new Audited(1, "CABA");
        Mockito.when(auditedService.getAuditedById(1)).thenReturn(audited);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/audited/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.audited").value("CABA"));
    }

    @Test
    public void createAuditedTest() throws Exception {
        Audited savedAudited = new Audited(1, "CABA");
        Mockito.when(auditedService.createAudited(savedAudited)).thenReturn(savedAudited);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/audited")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"audited\":\"CABA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateAuditedTest() throws Exception {
        Audited updatedAudited = new Audited(1, "GBA");

        Mockito.when(auditedService.updateAudited(1, updatedAudited)).thenReturn(updatedAudited);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/audited/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"audited\":\"GBA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(auditedService, Mockito.times(1))
                .updateAudited(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Audited.class));
    }

    @Test
    public void deleteAuditedTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/audited/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(auditedService, Mockito.times(1)).deleteAudited(id);
    }

}
