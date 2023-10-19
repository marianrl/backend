package com.ams.backend.controller;

import com.ams.backend.entity.*;
import com.ams.backend.service.CommonInputService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(CommonInputController.class)
public class CommonInputControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommonInputService commonInputService;

    final private Client client = new Client(1, "hola");
    final private Branch branch = new Branch(1, "hola");
    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer);
    final private Audit audit = new Audit(1,1,"11/10/2023",auditType,audited);

    final private CommonInput commonInput = new CommonInput(
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
    public void getAllCommonInputTest() throws Exception {

        List<CommonInput> commonInputs = new ArrayList<>();
        Mockito.when(commonInputService.getAllCommonInputs()).thenReturn(commonInputs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/commonInput"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getCommonAuditByAuditNumberTest() throws Exception {

        List<CommonInput> commonInputsList = new ArrayList<>();
        commonInputsList.add(commonInput);
        Mockito.when(commonInputService.getCommonInputByAuditNumber(1)).thenReturn(commonInputsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/commonInput/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    public void createCommonInputTest() throws Exception {
        Mockito.when(commonInputService.createCommonInput(commonInput)).thenReturn(commonInput);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/commonInput")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commonInput)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("Perez", commonInput.getLastName());
        assertEquals("Juan", commonInput.getName());
        assertEquals("20-45125484-7", commonInput.getCuil());
        assertEquals("4568", commonInput.getFile());
        assertEquals("1248", commonInput.getAllocation());
        assertEquals("hola", commonInput.getClient().getClient());
        assertEquals("Capital Federal", commonInput.getUoc());
        assertEquals("hola", commonInput.getBranch().getBranch());
        assertEquals("17/06/2023", commonInput.getAdmissionDate());
        assertEquals("AFIP", commonInput.getFeatures().getAuditType().getAuditType());
        assertEquals("SE AJUSTA", commonInput.getFeatures().getAnswer().getAnswer());
        assertEquals(1, commonInput.getAudit().getAuditNumber());
    }

    @Test
    public void updateCommonAuditTest() throws Exception {

        Mockito.when(commonInputService.updateCommonInput(1, commonInput)).thenReturn(commonInput);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/commonInput/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commonInput)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(commonInputService, Mockito.times(1))
                .updateCommonInput(ArgumentMatchers.anyInt(), ArgumentMatchers.any(CommonInput.class));

        assertEquals("Perez", commonInput.getLastName());
        assertEquals("Juan", commonInput.getName());
        assertEquals("20-45125484-7", commonInput.getCuil());
        assertEquals("4568", commonInput.getFile());
        assertEquals("1248", commonInput.getAllocation());
        assertEquals("hola", commonInput.getClient().getClient());
        assertEquals("Capital Federal", commonInput.getUoc());
        assertEquals("hola", commonInput.getBranch().getBranch());
        assertEquals("17/06/2023", commonInput.getAdmissionDate());
        assertEquals("AFIP", commonInput.getFeatures().getAuditType().getAuditType());
        assertEquals("SE AJUSTA", commonInput.getFeatures().getAnswer().getAnswer());
        assertEquals(1, commonInput.getAudit().getAuditNumber());

    }

    @Test
    public void deleteCommonAuditTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/commonInput/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(commonInputService, Mockito.times(1)).deleteCommonInput(id);
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
