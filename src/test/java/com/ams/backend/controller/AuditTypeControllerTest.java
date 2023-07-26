package com.ams.backend.controller;

import com.ams.backend.entity.AuditType;
import com.ams.backend.service.AuditTypeService;
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
@WebMvcTest(AuditTypeController.class)
public class AuditTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditTypeService auditTypeService;

    @Test
    public void getAllAuditTypesTest() throws Exception {

        List<AuditType> auditTypes = new ArrayList<>();
        Mockito.when(auditTypeService.getAllAuditType()).thenReturn(auditTypes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auditType"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAuditTypeByIdTest() throws Exception {
        AuditType auditType = new AuditType(1, "CABA");
        Mockito.when(auditTypeService.getAuditTypeById(1)).thenReturn(auditType);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auditType/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auditType").value("CABA"));
    }

    @Test
    public void createAuditTypeTest() throws Exception {
        AuditType savedAuditType = new AuditType(1, "CABA");
        Mockito.when(auditTypeService.createAuditType(savedAuditType)).thenReturn(savedAuditType);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auditType")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"audit_type\":\"CABA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateAuditTypeTest() throws Exception {
        AuditType updatedAuditType = new AuditType(1, "GBA");

        Mockito.when(auditTypeService.updateAuditType(1, updatedAuditType))
                .thenReturn(updatedAuditType);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auditType/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"audit_type\":\"GBA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(auditTypeService, Mockito.times(1))
                .updateAuditType(ArgumentMatchers.anyInt(), ArgumentMatchers.any(AuditType.class));
    }

    @Test
    public void deleteAuditTypeTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/auditType/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(auditTypeService, Mockito.times(1)).deleteAuditType(id);
    }

}
