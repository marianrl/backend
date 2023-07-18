package com.ams.backend.controller;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.entity.Features;
import com.ams.backend.service.FeaturesService;
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
@WebMvcTest(FeaturesController.class)
public class FeatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeaturesService featuresService;

    final private Answer answer = new Answer(1, "SE AJUSTA");
    final private AuditType auditType = new AuditType(1, "AFIP");
    final private Audited audited = new Audited(1,"NO");
    final private Features features = new Features(1,auditType ,answer ,audited);

    @Test
    public void getAllFeaturesTest() throws Exception {

        List<Features> features = new ArrayList<>();
        Mockito.when(featuresService.getAllFeatures()).thenReturn(features);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/features"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getFeaturesByIdTest() throws Exception {

        Mockito.when(featuresService.getFeaturesById(1)).thenReturn(features);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/features/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auditType.auditType").value("AFIP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.answer.answer").value("SE AJUSTA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.audited.audited").value("NO"));
    }

    @Test
    public void createFeaturesTest() throws Exception {

        Mockito.when(featuresService.createFeatures(features)).thenReturn(features);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/features")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(features)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("SE AJUSTA", features.getAnswer().getAnswer());
        assertEquals("AFIP", features.getAuditType().getAuditType());
        assertEquals("NO", features.getAudited().getAudited());
    }

    @Test
    public void updateFeaturesTest() throws Exception {

        Mockito.when(featuresService.updateFeatures(1, features)).thenReturn(features);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/features/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(features)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(featuresService, Mockito.times(1))
                .updateFeatures(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Features.class));

        assertEquals("SE AJUSTA", features.getAnswer().getAnswer());
        assertEquals("AFIP", features.getAuditType().getAuditType());
        assertEquals("NO", features.getAudited().getAudited());
    }

    @Test
    public void deleteFeaturesTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/features/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(featuresService, Mockito.times(1)).deleteFeatures(id);
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
