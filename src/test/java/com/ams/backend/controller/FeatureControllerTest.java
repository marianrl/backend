package com.ams.backend.controller;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.entity.Features;
import com.ams.backend.service.FeaturesService;
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
@WebMvcTest(FeaturesController.class)
public class FeatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeaturesService featuresService;

    @Test
    public void getAllFeaturesTest() throws Exception {

        List<Features> features = new ArrayList<>();
        Mockito.when(featuresService.getAllFeatures()).thenReturn(features);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/features"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getFeaturesByIdTest() throws Exception {
        Answer answer = new Answer(1, "SE AJUSTA");
        AuditType auditType = new AuditType(1, "AFIP");
        Audited audited = new Audited(1,"NO");
        Features features = new Features(1,auditType,answer, audited);
        Mockito.when(featuresService.getFeaturesById(1)).thenReturn(features);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/features/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.features").value("SE AJUSTA"));
    }

    @Test
    public void createFeaturesTest() throws Exception {
        Answer answer = new Answer(1, "SE AJUSTA");
        AuditType auditType = new AuditType(1, "AFIP");
        Audited audited = new Audited(1,"NO");
        Features savedFeatures = new Features(1,auditType,answer, audited);
        Mockito.when(featuresService.createFeatures(savedFeatures)).thenReturn(savedFeatures);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/features")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"features\":\"CABA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateFeaturesTest() throws Exception {
        Answer answer = new Answer(1, "SE AJUSTA");
        AuditType auditType = new AuditType(1, "AFIP");
        Audited audited = new Audited(1,"NO");
        Features updatedFeatures = new Features(1,auditType,answer, audited);

        Mockito.when(featuresService.updateFeatures(1, updatedFeatures)).thenReturn(updatedFeatures);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/features/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"features\":\"GBA\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(featuresService, Mockito.times(1))
                .updateFeatures(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Features.class));
    }

    @Test
    public void deleteFeaturesTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/features/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(featuresService, Mockito.times(1)).deleteFeatures(id);
    }

}
