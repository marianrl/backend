package com.ams.backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditTypeRequest {
  private int id;

  @NotBlank(message = "Audit type is required")
  private String auditType;
}