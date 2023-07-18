package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.entity.Audited;
import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.FeaturesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FeatureServiceTest {

    @Mock
    private FeaturesRepository featurRepository;

    private FeaturesService featurService;

    @Before
    public void setup() {
        featurService = new FeaturesService(featurRepository);
    }

    @Test
    public void testGetAllFeaturess() {
        List<Features> featurs = new ArrayList<>();
        Mockito.when(featurRepository.findAll()).thenReturn(featurs);
        List<Features> actualFeaturess = featurService.getAllFeatures();

        assertEquals(featurs, actualFeaturess);
    }

    @Test
    public void testGetFeaturessById() throws ResourceNotFoundException {
        int featursId = 1;
        AuditType auditType = new AuditType(1, "AFIP");
        Answer answer = new Answer(1, "SE AJUSTA");
        Audited audited = new Audited(1,"NO");
        Features expectedFeatures = new Features(featursId,auditType, answer,audited );

        Mockito.when(featurRepository.findById(featursId)).thenReturn(Optional.of(expectedFeatures));
        Features actualFeatures = featurService.getFeaturesById(featursId);

        assertEquals(expectedFeatures, actualFeatures);
    }

    @Test
    public void testCreateFeatures() {
        int featursId = 1;
        AuditType auditType = new AuditType(1, "AFIP");
        Answer answer = new Answer(1, "SE AJUSTA");
        Audited audited = new Audited(1,"NO");
        Features featur = new Features(featursId,auditType,answer,audited);


        Mockito.when(featurRepository.save(featur)).thenReturn(featur);
        Features actualFeatures = featurService.createFeatures(featur);

        assertEquals(actualFeatures, featur);
    }

    @Test
    public void testUpdateFeatures() throws ResourceNotFoundException {
        int featurId = 1;
        AuditType auditType = new AuditType(1, "AFIP");
        Answer answer = new Answer(1, "SE AJUSTA");
        Audited audited = new Audited(1,"NO");
        Features featur = new Features(featurId,auditType,answer,audited);
        Features updatedFeatures = new Features(featurId,auditType,answer,audited);

        Mockito.when(featurRepository.findById(1)).thenReturn(Optional.of(featur));
        Features actualFeatures = featurService.updateFeatures(featurId, updatedFeatures);

        assertEquals(updatedFeatures.getId(), actualFeatures.getId());
        assertEquals(updatedFeatures.getAuditType(), actualFeatures.getAuditType());
        assertEquals(updatedFeatures.getAnswer(), actualFeatures.getAnswer());
        assertEquals(updatedFeatures.getAudited(), actualFeatures.getAudited());
    }

    @Test
    public void testDeleteFeatures() throws ResourceNotFoundException {
        int featurId = 1;
        AuditType auditType = new AuditType(1, "AFIP");
        Answer answer = new Answer(1, "SE AJUSTA");
        Audited audited = new Audited(1,"NO");
        Features featur = new Features(featurId,auditType,answer,audited);

        Mockito.when(featurRepository.findById(1)).thenReturn(Optional.of(featur));
        featurService.deleteFeatures(featurId);

        verify(featurRepository).deleteById(1);
    }
}

