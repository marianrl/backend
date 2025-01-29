package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.Features;
import com.ams.backend.exception.ResourceNotFoundException;

public interface FeaturesService {
  List<Features> getAllFeatures();
  Features getFeaturesById(int id) throws ResourceNotFoundException;
  Features createFeatures(Features features);
  Features updateFeatures(int id, Features providedFeatures) throws ResourceNotFoundException;
  void deleteFeatures(int id) throws ResourceNotFoundException;
}
