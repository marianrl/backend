package com.ams.backend.service;

import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
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
    private FeaturesRepository featuresRepository;

    private FeaturesService featuresService;

    @Before
    public void setup() {
        featuresService = new FeaturesService(featuresRepository);
    }

    @Test
    public void testGetAllFeatures() {
        List<Features> features = new ArrayList<>();
        Mockito.when(featuresRepository.findAll()).thenReturn(features);
        List<Features> actualFeatures = featuresService.getAllFeatures();

        assertEquals(features, actualFeatures);
    }

    @Test
    public void testGetFeaturesById() throws ResourceNotFoundException {
        int featuresId = 1;
        AuditType auditType = new AuditType(1, "AFIP");
        Answer answer = new Answer(1, "SE AJUSTA");
        Features expectedFeatures = new Features(featuresId,auditType, answer );

        Mockito.when(featuresRepository.findById(featuresId)).thenReturn(Optional.of(expectedFeatures));
        Features actualFeatures = featuresService.getFeaturesById(featuresId);

        assertEquals(expectedFeatures, actualFeatures);
    }

    @Test
    public void testCreateFeatures() {
        int featuresId = 1;
        AuditType auditType = new AuditType(1, "AFIP");
        Answer answer = new Answer(1, "SE AJUSTA");
        Features feature = new Features(featuresId,auditType,answer);


        Mockito.when(featuresRepository.save(feature)).thenReturn(feature);
        Features actualFeatures = featuresService.createFeatures(feature);

        assertEquals(actualFeatures, feature);
    }

    @Test
    public void testUpdateFeatures() throws ResourceNotFoundException {
        int featurId = 1;
        AuditType auditType = new AuditType(1, "AFIP");
        Answer answer = new Answer(1, "SE AJUSTA");
        Features feature = new Features(featurId,auditType,answer);
        Features updatedFeatures = new Features(featurId,auditType,answer);

        Mockito.when(featuresRepository.findById(1)).thenReturn(Optional.of(feature));
        Features actualFeatures = featuresService.updateFeatures(featurId, updatedFeatures);

        assertEquals(updatedFeatures.getId(), actualFeatures.getId());
        assertEquals(updatedFeatures.getAuditType(), actualFeatures.getAuditType());
        assertEquals(updatedFeatures.getAnswer(), actualFeatures.getAnswer());
    }

    @Test
    public void testDeleteFeatures() throws ResourceNotFoundException {
        int featurId = 1;
        AuditType auditType = new AuditType(1, "AFIP");
        Answer answer = new Answer(1, "SE AJUSTA");
        Features feature = new Features(featurId,auditType,answer);

        Mockito.when(featuresRepository.findById(1)).thenReturn(Optional.of(feature));
        featuresService.deleteFeatures(featurId);

        verify(featuresRepository).deleteById(1);
    }
}

