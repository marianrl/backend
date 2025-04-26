package com.ams.backend.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AfipInputUpdateRequest;
import com.ams.backend.request.InputRequest;
import com.ams.backend.response.AfipInputResponse;

public interface AfipInputService {
  List<AfipInputResponse> getAllAfipInputs();

  Optional<AfipInputResponse> getAfipInputById(int id);

  List<AfipInputResponse> getAfipInputByAuditNumber(int id);

  List<AfipInputResponse> getFilteredAfipInputs(String apellido, String nombre, String cuil, String legajo,
      String asignacion, Long idCliente, String uoc,
      Long idSucursal, LocalDate fechaIngreso, Long idCaracteristicas);

  AfipInputResponse createAfipInput(InputRequest inputRequest) throws ResourceNotFoundException;

  AfipInputResponse updateAfipInput(int id, AfipInputUpdateRequest afipInputUpdateRequest)
      throws ResourceNotFoundException;

  void deleteAfipInput(int id) throws ResourceNotFoundException;

  List<AfipInputResponse> createAfipInputs(List<InputRequest> inputRequests) throws ResourceNotFoundException;
}
