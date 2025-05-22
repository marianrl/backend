package com.ams.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditResponse {
  private int id;
  private LocalDate auditDate;
  private AuditTypeResponse idTipoAuditoria;
  private AuditedResponse idAuditado;
}