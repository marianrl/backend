package com.ams.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FeaturesResponse {
  private int id;
  private AuditTypeResponse auditType;
  private AnswerResponse answer;
}