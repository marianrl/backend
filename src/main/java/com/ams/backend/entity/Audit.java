package com.ams.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"Auditoria\"")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "\"fecha_auditoria\"", nullable = false)
    private @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate auditDate;

    @ManyToOne
    @JoinColumn(name = "\"id_tipo_auditoria\"")
    private AuditType auditType;

    @ManyToOne
    @JoinColumn(name = "\"id_auditado\"")
    private Audited audited;
}
