package com.ams.backend.service;

import com.ams.backend.entity.Features;
import com.ams.backend.entity.Answer;
import com.ams.backend.entity.AuditType;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.FeaturesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeatureServiceTest {

    @Mock
    private FeaturesRepository featuresRepository;

    @InjectMocks
    private FeaturesServiceImpl featuresService;

    private Features features;
    private Answer answer;
    private AuditType auditType;

    @BeforeEach
    public void setup() {
        answer = new Answer();
        answer.setId(1);
        answer.setAnswer("Yes");

        auditType = new AuditType();
        auditType.setId(1);
        auditType.setAuditType("Security");

        features = new Features();
        features.setId(1);
        features.setAnswer(answer);
        features.setAuditType(auditType);
    }

    @Test
    public void testGetAllFeatures() {
        List<Features> expectedFeatures = new ArrayList<>();
        expectedFeatures.add(features);
        when(featuresRepository.findAll()).thenReturn(expectedFeatures);

        List<Features> actualFeatures = featuresService.getAllFeatures();

        assertEquals(expectedFeatures, actualFeatures);
        verify(featuresRepository, times(1)).findAll();
    }

    @Test
    public void testGetFeaturesById() throws ResourceNotFoundException {
        when(featuresRepository.findById(1)).thenReturn(Optional.of(features));

        Features actualFeatures = featuresService.getFeaturesById(1);

        assertEquals(features, actualFeatures);
        verify(featuresRepository, times(1)).findById(1);
    }

    @Test
    public void testGetFeaturesById_NotFound() {
        when(featuresRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> featuresService.getFeaturesById(1));
    }

    @Test
    public void testCreateFeatures() {
        when(featuresRepository.save(features)).thenReturn(features);

        Features actualFeatures = featuresService.createFeatures(features);

        assertEquals(features, actualFeatures);
        verify(featuresRepository, times(1)).save(features);
    }

    @Test
    public void testUpdateFeatures() throws ResourceNotFoundException {
        Features updatedFeatures = new Features();
        Answer newAnswer = new Answer();
        newAnswer.setAnswer("No");
        AuditType newAuditType = new AuditType();
        newAuditType.setAuditType("Compliance");

        updatedFeatures.setAnswer(newAnswer);
        updatedFeatures.setAuditType(newAuditType);

        when(featuresRepository.findById(1)).thenReturn(Optional.of(features));
        when(featuresRepository.save(features)).thenReturn(features);

        Features actualFeatures = featuresService.updateFeatures(1, updatedFeatures);

        assertEquals(updatedFeatures.getAnswer().getAnswer(), actualFeatures.getAnswer().getAnswer());
        assertEquals(updatedFeatures.getAuditType().getAuditType(), actualFeatures.getAuditType().getAuditType());
        verify(featuresRepository, times(1)).findById(1);
        verify(featuresRepository, times(1)).save(features);
    }

    @Test
    public void testUpdateFeatures_NotFound() {
        Features updatedFeatures = new Features();
        Answer newAnswer = new Answer();
        newAnswer.setAnswer("No");
        AuditType newAuditType = new AuditType();
        newAuditType.setAuditType("Compliance");

        updatedFeatures.setAnswer(newAnswer);
        updatedFeatures.setAuditType(newAuditType);

        when(featuresRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> featuresService.updateFeatures(1, updatedFeatures));
    }

    @Test
    public void testDeleteFeatures() throws ResourceNotFoundException {
        when(featuresRepository.findById(1)).thenReturn(Optional.of(features));
        doNothing().when(featuresRepository).deleteById(1);

        featuresService.deleteFeatures(1);

        verify(featuresRepository, times(1)).findById(1);
        verify(featuresRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteFeatures_NotFound() {
        when(featuresRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> featuresService.deleteFeatures(1));
    }
}
