package com.ams.backend.service;

import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.FeaturesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FeaturesService {

    @Autowired
    private FeaturesRepository featuresRepository;

    public List<Features> getAllFeatures() {
        return featuresRepository.findAll();
    }

    public Features getFeaturesById(int id) throws ResourceNotFoundException {
        return featuresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Features not found for this id :: " + id));
    }

    public Features createFeatures(Features features) {
        return featuresRepository.save(features);
    }

    public Features updateFeatures(int id, Features providedFeatures) throws ResourceNotFoundException {
        Features features = featuresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Features not found for this id :: " + id));

        features.setAnswer(providedFeatures.getAnswer());
        features.setAuditType(providedFeatures.getAuditType());

        featuresRepository.save(features);

        return features;
    }

    public void deleteFeatures(int id) throws ResourceNotFoundException{
        featuresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Features not found for this id :: " + id));

        featuresRepository.deleteById(id);
    }
}
