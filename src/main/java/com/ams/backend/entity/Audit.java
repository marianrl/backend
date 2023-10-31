package com.ams.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "Auditoria")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "audit_number", nullable = false)
    private int auditNumber;

    @Column(name = "fecha_auditoria", nullable = false)
    private String auditDate;

    @ManyToOne
    @JoinColumn(name = "id_tipo_auditoria")
    private AuditType idTipoAuditoria;

    @ManyToOne
    @JoinColumn(name = "id_auditado")
    private Audited idAuditado;


}
