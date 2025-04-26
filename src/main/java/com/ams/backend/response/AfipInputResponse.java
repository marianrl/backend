package com.ams.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AfipInputResponse {
  private int id;
  private String lastName;
  private String name;
  private String cuil;
  private String file;
  private String allocation;
  private ClientResponse client;
  private String uoc;
  private BranchResponse branch;
  private @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate admissionDate;
  private FeaturesResponse features;
  private AuditResponse audit;
}