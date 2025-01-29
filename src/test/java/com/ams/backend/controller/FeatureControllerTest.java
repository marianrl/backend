package com.ams.backend.controller;

import com.ams.backend.entity.Features;
import com.ams.backend.service.interfaces.FeaturesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeatureControllerTest {

    @Mock
    private FeaturesService featuresService;

    @InjectMocks
    private FeaturesController featuresController;

    private Features features;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        features = new Features();
        features.setId(1);
    }

    @Test
    public void getAllFeaturesTest() throws Exception {
        List<Features> featuresList = Collections.singletonList(features);
        when(featuresService.getAllFeatures()).thenReturn(featuresList);

        List<Features> result = featuresController.getAllFeatures();

        assertEquals(featuresList, result);
        verify(featuresService, times(1)).getAllFeatures();
    }

    @Test
    public void getFeaturesByIdTest() throws Exception {
        when(featuresService.getFeaturesById(1)).thenReturn(features);

        ResponseEntity<Features> result = featuresController.getFeaturesById(1);

        assertEquals(features, result.getBody());
        verify(featuresService, times(1)).getFeaturesById(1);
    }

    @Test
    public void createFeaturesTest() throws Exception {
        when(featuresService.createFeatures(any(Features.class))).thenReturn(features);

        Features result = featuresController.createFeatures(features);

        assertEquals(features, result);
        verify(featuresService, times(1)).createFeatures(any(Features.class));
    }

    @Test
    public void updateFeaturesTest() throws Exception {
        Features features = new Features();
        when(featuresService.updateFeatures(eq(1), any(Features.class))).thenReturn(features);

        ResponseEntity<Features> result = featuresController.updateFeatures(1, features);

        assertEquals(features, result.getBody());
        verify(featuresService, times(1)).updateFeatures(eq(1), any(Features.class));
    }

    @Test
    public void deleteFeaturesTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(featuresService).deleteFeatures(1);

        ResponseEntity<Void> result = featuresController.deleteFeatures(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(featuresService, times(1)).deleteFeatures(1);
    }
}
