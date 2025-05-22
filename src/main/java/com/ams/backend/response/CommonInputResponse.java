package com.ams.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonInputResponse {
  private int id;
  private String lastName;
  private String name;
  private String cuil;
  private String file;
  private String allocation;
  private ClientResponse client;
  private String uoc;
  private BranchResponse branch;
  private LocalDate admissionDate;
  private FeaturesResponse features;
  private AuditResponse audit;
}