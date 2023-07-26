package com.ams.backend.controller;

import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.service.FeaturesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FeaturesController {

    @Autowired
    private FeaturesService featuresService;

    @GetMapping("/features")
    public List<Features> getAllFeatures() {

        return featuresService.getAllFeatures();
    }

    @GetMapping("/features/{id}")
    public ResponseEntity<Features> getFeaturesById(@PathVariable(value = "id") int featuresId)
            throws ResourceNotFoundException{
        Features features = featuresService.getFeaturesById(featuresId);

        return ResponseEntity.ok().body(features);
    }

    @PostMapping("/features")
    public Features createFeatures(@Valid @RequestBody Features features) {
        return featuresService.createFeatures(features);
    }

    @PutMapping("/features/{id}")
    public ResponseEntity<Features> updateFeatures(
            @PathVariable(value = "id") int featuresId,
            @Valid @RequestBody Features featuresDetails) throws ResourceNotFoundException {
        final Features updatedFeatures = featuresService.updateFeatures(featuresId, featuresDetails);

        return ResponseEntity.ok(updatedFeatures);
    }

    @DeleteMapping("/features/{id}")
    public ResponseEntity<Void> deleteFeatures(@PathVariable(value = "id") int featuresId)
            throws ResourceNotFoundException {
        featuresService.deleteFeatures(featuresId);

        return ResponseEntity.noContent().build();
    }
}
