package com.ams.backend.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.ams.backend.entity.CommonInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.mapper.CommonInputMapper;
import com.ams.backend.request.CommonInputUpdateRequest;
import com.ams.backend.request.InputRequest;

public interface CommonInputService {
  List<CommonInput> getAllCommonInputs();

  List<CommonInput> getCommonInputByAuditNumber(int id);

  Optional<CommonInput> getCommonInputById(int id);

  CommonInput createCommonInput(InputRequest inputRequest) throws ResourceNotFoundException;

  CommonInput updateCommonInput(int id, CommonInputUpdateRequest commonInputUpdateRequest)
      throws ResourceNotFoundException;

  void deleteCommonInput(int id) throws ResourceNotFoundException;

  List<CommonInput> createCommonInputs(List<InputRequest> inputRequests) throws ResourceNotFoundException;

  CommonInputMapper getMapper();
}
