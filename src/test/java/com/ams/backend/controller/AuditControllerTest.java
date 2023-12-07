package com.ams.backend.controller;

import com.ams.backend.entity.*;
import com.ams.backend.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(AuditController.class)
public class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService auditService;

    final private AuditType auditType = new AuditType(1,"Test");
    final private Audited audited = new Audited(1,"TEST");
    final private Audit audit = new Audit(
            1,
            22,
            LocalDate.now(),
            auditType,
            audited
    );

    @Test
    public void getAllAuditTest() throws Exception {

        List<Audit> audits = new ArrayList<>();
        Mockito.when(auditService.getAllAudit()).thenReturn(audits);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/audit"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAuditByIdTest() throws Exception {
        Mockito.when(auditService.getAuditById(1)).thenReturn(audit);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/audit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auditNumber").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auditDate").value(LocalDate.now()));
    }

    @Test
    public void createAuditTest() throws Exception {
        Mockito.when(auditService.createAudit(audit)).thenReturn(audit);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/audit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(audit)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(1, audit.getId());
        assertEquals(22, audit.getAuditNumber());
        assertEquals(LocalDate.now(), audit.getAuditDate());
        assertEquals(auditType, audit.getIdTipoAuditoria());
        assertEquals(audited, audit.getIdAuditado());
    }

    @Test
    public void updateAuditTest() throws Exception {

        Mockito.when(auditService.updateAudit(1, audit)).thenReturn(audit);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/audit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(audit)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(auditService, Mockito.times(1))
                .updateAudit(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Audit.class));

        assertEquals(1, audit.getId());
        assertEquals(22, audit.getAuditNumber());
        assertEquals(LocalDate.now(), audit.getAuditDate());
        assertEquals(auditType, audit.getIdTipoAuditoria());
        assertEquals(audited, audit.getIdAuditado());
    }

    @Test
    public void deleteAuditTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/audit/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(auditService, Mockito.times(1)).deleteAudit(id);
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
