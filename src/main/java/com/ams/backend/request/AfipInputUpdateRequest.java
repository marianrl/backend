package com.ams.backend.request;

import jakarta.persistence.Id;
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
public class AfipInputUpdateRequest {
    @Id
    private int id;
    private String lastName;
    private String name;
    private String cuil;
    private String file;
    private String allocation;
    private String uoc;
    private @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate admissionDate;
    private int answerId;
    private int auditTypeId;
}
