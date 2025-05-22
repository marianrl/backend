package com.ams.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeaturesResponse {
  private int id;
  private AuditTypeResponse auditType;
  private AnswerResponse answer;
}