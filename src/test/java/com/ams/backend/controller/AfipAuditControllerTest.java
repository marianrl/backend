package com.ams.backend.controller;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.entity.Branch;
import com.ams.backend.entity.Client;
import com.ams.backend.entity.AfipAudit;
import com.ams.backend.entity.Features;
import com.ams.backend.service.AfipAuditService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(AfipAuditController.class)
public class AfipAuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AfipAuditService afipAuditService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer ,audited);
    final private AfipAudit afipAudit = new AfipAudit(
            1,
            "17/07/2023",
            "Perez",
            "Juan",
            "20-45125484-7",
            "4568",
            "1248",
            client,
            "Capital Federal",
            branch,
            "17/06/2023",
            features
    );

    @Test
    public void getAllAfipAuditTest() throws Exception {

        List<AfipAudit> afipAudits = new ArrayList<>();
        Mockito.when(afipAuditService.getAllAfipAudits()).thenReturn(afipAudits);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/afipAudit"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAfipAuditByIdTest() throws Exception {
        Mockito.when(afipAuditService.getAfipAuditById(1)).thenReturn(afipAudit);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/afipAudit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auditDate").value("17/07/2023"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Perez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cuil").value("20-45125484-7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.file").value("4568"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allocation").value("1248"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.client.client").value("hola"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uoc").value("Capital Federal"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.branch.branch").value("hola"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.admissionDate").value("17/06/2023"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.features.auditType.auditType").value("AFIP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.features.answer.answer").value("SE AJUSTA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.features.audited.audited").value("NO"));
    }

    @Test
    public void createAfipAuditTest() throws Exception {
        Mockito.when(afipAuditService.createAfipAudit(afipAudit)).thenReturn(afipAudit);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/afipAudit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(afipAudit)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("17/07/2023", afipAudit.getAuditDate());
        assertEquals("Perez", afipAudit.getLastName());
        assertEquals("Juan", afipAudit.getName());
        assertEquals("20-45125484-7", afipAudit.getCuil());
        assertEquals("4568", afipAudit.getFile());
        assertEquals("1248", afipAudit.getAllocation());
        assertEquals("hola", afipAudit.getClient().getClient());
        assertEquals("Capital Federal", afipAudit.getUoc());
        assertEquals("hola", afipAudit.getBranch().getBranch());
        assertEquals("17/06/2023", afipAudit.getAdmissionDate());
        assertEquals("AFIP", afipAudit.getFeatures().getAuditType().getAuditType());
        assertEquals("SE AJUSTA", afipAudit.getFeatures().getAnswer().getAnswer());
        assertEquals("NO", afipAudit.getFeatures().getAudited().getAudited());

    }

    @Test
    public void updateAfipAuditTest() throws Exception {

        Mockito.when(afipAuditService.updateAfipAudit(1, afipAudit)).thenReturn(afipAudit);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/afipAudit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(afipAudit)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(afipAuditService, Mockito.times(1))
                .updateAfipAudit(ArgumentMatchers.anyInt(), ArgumentMatchers.any(AfipAudit.class));

        assertEquals("17/07/2023", afipAudit.getAuditDate());
        assertEquals("Perez", afipAudit.getLastName());
        assertEquals("Juan", afipAudit.getName());
        assertEquals("20-45125484-7", afipAudit.getCuil());
        assertEquals("4568", afipAudit.getFile());
        assertEquals("1248", afipAudit.getAllocation());
        assertEquals("hola", afipAudit.getClient().getClient());
        assertEquals("Capital Federal", afipAudit.getUoc());
        assertEquals("hola", afipAudit.getBranch().getBranch());
        assertEquals("17/06/2023", afipAudit.getAdmissionDate());
        assertEquals("AFIP", afipAudit.getFeatures().getAuditType().getAuditType());
        assertEquals("SE AJUSTA", afipAudit.getFeatures().getAnswer().getAnswer());
        assertEquals("NO", afipAudit.getFeatures().getAudited().getAudited());
    }

    @Test
    public void deleteAfipAuditTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/afipAudit/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(afipAuditService, Mockito.times(1)).deleteAfipAudit(id);
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
