package com.ams.backend.request;

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
public class InputRequest {
    private int auditId;
    private String lastName;
    private String name;
    private String cuil;
    private String file;
    private String allocation;
    private int client;
    private String uoc;
    private int branch;
    private @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate admissionDate;
}
