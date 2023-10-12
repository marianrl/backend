package com.ams.backend.controller;

import com.ams.backend.entity.*;
import com.ams.backend.service.AfipInputService;
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
@WebMvcTest(AfipInputController.class)
public class AfipInputControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AfipInputService afipInputService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer);
    final private Audit audit = new Audit(1,1,"11/10/2023",auditType,audited);
    final private AfipInput afipInput = new AfipInput(
            1,
            "Perez",
            "Juan",
            "20-45125484-7",
            "4568",
            "1248",
            client,
            "Capital Federal",
            branch,
            "17/06/2023",
            features,
            audit
    );
    @Test
    public void getAllAfipInputTest() throws Exception {

        List<AfipInput> afipInputs = new ArrayList<>();
        
        Mockito.when(afipInputService.getAllAfipInputs()).thenReturn(afipInputs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/afipAudit"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAfipInputByIdTest() throws Exception {
        List<AfipInput> afipInputsList = new ArrayList<>();
        afipInputsList.add(afipInput);
        Mockito.when(afipInputService.getAfipInputByAuditNumber(1)).thenReturn(afipInputsList);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.audit").value(1));
    }

    @Test
    public void createAfipInputTest() throws Exception {
        Mockito.when(afipInputService.createAfipInput(afipInput)).thenReturn(afipInput);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/afipAudit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(afipInput)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("Perez", afipInput.getLastName());
        assertEquals("Juan", afipInput.getName());
        assertEquals("20-45125484-7", afipInput.getCuil());
        assertEquals("4568", afipInput.getFile());
        assertEquals("1248", afipInput.getAllocation());
        assertEquals("hola", afipInput.getClient().getClient());
        assertEquals("Capital Federal", afipInput.getUoc());
        assertEquals("hola", afipInput.getBranch().getBranch());
        assertEquals("17/06/2023", afipInput.getAdmissionDate());
        assertEquals("AFIP", afipInput.getFeatures().getAuditType().getAuditType());
        assertEquals("SE AJUSTA", afipInput.getFeatures().getAnswer().getAnswer());
        assertEquals(1, afipInput.getAudit().getAuditNumber());

    }

    @Test
    public void updateAfipInputTest() throws Exception {

        Mockito.when(afipInputService.updateAfipInput(1, afipInput)).thenReturn(afipInput);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/afipAudit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(afipInput)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(afipInputService, Mockito.times(1))
                .updateAfipInput(ArgumentMatchers.anyInt(), ArgumentMatchers.any(AfipInput.class));

        assertEquals("Perez", afipInput.getLastName());
        assertEquals("Juan", afipInput.getName());
        assertEquals("20-45125484-7", afipInput.getCuil());
        assertEquals("4568", afipInput.getFile());
        assertEquals("1248", afipInput.getAllocation());
        assertEquals("hola", afipInput.getClient().getClient());
        assertEquals("Capital Federal", afipInput.getUoc());
        assertEquals("hola", afipInput.getBranch().getBranch());
        assertEquals("17/06/2023", afipInput.getAdmissionDate());
        assertEquals("AFIP", afipInput.getFeatures().getAuditType().getAuditType());
        assertEquals("SE AJUSTA", afipInput.getFeatures().getAnswer().getAnswer());
        assertEquals("SE AJUSTA", afipInput.getFeatures().getAnswer().getAnswer());
        assertEquals(1, afipInput.getAudit().getAuditNumber());


    }

    @Test
    public void deleteAfipAuditTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/afipAudit/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(afipInputService, Mockito.times(1)).deleteAfipInput(id);
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
