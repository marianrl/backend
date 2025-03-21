package com.ams.backend.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.request.AfipInputUpdateRequest;

public interface AfipInputService {
  List<AfipInput> getAllAfipInputs();
  Optional<AfipInput> getAfipInputById(int id);
  List<AfipInput> getAfipInputByAuditNumber(int id);
  List<AfipInput> getFilteredAfipInputs(String apellido, String nombre, String cuil, String legajo, 
                                        String asignacion, Long idCliente, String uoc, 
                                        Long idSucursal, LocalDate fechaIngreso, Long idCaracteristicas);
  AfipInput createAfipInput(AfipInput afipInput);
  AfipInput updateAfipInput(int id, AfipInputUpdateRequest afipInputUpdateRequest) throws ResourceNotFoundException;
  void deleteAfipInput(int id) throws ResourceNotFoundException;
}
