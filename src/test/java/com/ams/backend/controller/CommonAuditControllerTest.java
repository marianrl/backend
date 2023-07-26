package com.ams.backend.controller;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.entity.Branch;
import com.ams.backend.entity.Client;
import com.ams.backend.entity.CommonAudit;
import com.ams.backend.entity.Features;
import com.ams.backend.service.CommonAuditService;
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
@WebMvcTest(CommonAuditController.class)
public class CommonAuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommonAuditService commonAuditService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer ,audited);
    final private CommonAudit commonAudit = new CommonAudit(
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
    public void getAllCommonAuditTest() throws Exception {

        List<CommonAudit> commonAudits = new ArrayList<>();
        Mockito.when(commonAuditService.getAllCommonAudits()).thenReturn(commonAudits);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/commonAudit"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getCommonAuditByIdTest() throws Exception {
        Mockito.when(commonAuditService.getCommonAuditById(1)).thenReturn(commonAudit);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/commonAudit/1"))
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
    public void createCommonAuditTest() throws Exception {
        Mockito.when(commonAuditService.createCommonAudit(commonAudit)).thenReturn(commonAudit);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/commonAudit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commonAudit)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("17/07/2023", commonAudit.getAuditDate());
        assertEquals("Perez", commonAudit.getLastName());
        assertEquals("Juan", commonAudit.getName());
        assertEquals("20-45125484-7", commonAudit.getCuil());
        assertEquals("4568", commonAudit.getFile());
        assertEquals("1248", commonAudit.getAllocation());
        assertEquals("hola", commonAudit.getClient().getClient());
        assertEquals("Capital Federal", commonAudit.getUoc());
        assertEquals("hola", commonAudit.getBranch().getBranch());
        assertEquals("17/06/2023", commonAudit.getAdmissionDate());
        assertEquals("AFIP", commonAudit.getFeatures().getAuditType().getAuditType());
        assertEquals("SE AJUSTA", commonAudit.getFeatures().getAnswer().getAnswer());
        assertEquals("NO", commonAudit.getFeatures().getAudited().getAudited());

    }

    @Test
    public void updateCommonAuditTest() throws Exception {

        Mockito.when(commonAuditService.updateCommonAudit(1, commonAudit)).thenReturn(commonAudit);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/commonAudit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commonAudit)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(commonAuditService, Mockito.times(1))
                .updateCommonAudit(ArgumentMatchers.anyInt(), ArgumentMatchers.any(CommonAudit.class));

        assertEquals("17/07/2023", commonAudit.getAuditDate());
        assertEquals("Perez", commonAudit.getLastName());
        assertEquals("Juan", commonAudit.getName());
        assertEquals("20-45125484-7", commonAudit.getCuil());
        assertEquals("4568", commonAudit.getFile());
        assertEquals("1248", commonAudit.getAllocation());
        assertEquals("hola", commonAudit.getClient().getClient());
        assertEquals("Capital Federal", commonAudit.getUoc());
        assertEquals("hola", commonAudit.getBranch().getBranch());
        assertEquals("17/06/2023", commonAudit.getAdmissionDate());
        assertEquals("AFIP", commonAudit.getFeatures().getAuditType().getAuditType());
        assertEquals("SE AJUSTA", commonAudit.getFeatures().getAnswer().getAnswer());
        assertEquals("NO", commonAudit.getFeatures().getAudited().getAudited());
    }

    @Test
    public void deleteCommonAuditTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/commonAudit/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(commonAuditService, Mockito.times(1)).deleteCommonAudit(id);
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
