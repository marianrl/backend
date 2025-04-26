package com.ams.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditResponse {
  private int id;
  private @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate auditDate;
  private AuditTypeResponse idTipoAuditoria;
  private AuditedResponse idAuditado;
}