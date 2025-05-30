package com.ams.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "\"Ingreso_Afip\"")
public class AfipInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "\"apellido\"", nullable = false)
    private String lastName;

    @Column(name = "\"nombre\"", nullable = false)
    private String name;

    @Column(name = "\"cuil\"", nullable = false)
    private String cuil;

    @Column(name = "\"legajo\"", nullable = false)
    private String file;

    @Column(name = "\"asignacion\"", nullable = false)
    private String allocation;

    @ManyToOne
    @JoinColumn(name = "\"id_cliente\"")
    private Client client;

    @Column(name = "\"uoc\"", nullable = false)
    private String uoc;

    @ManyToOne
    @JoinColumn(name = "\"id_sucursal\"")
    private Branch branch;

    @Column(name = "\"fecha_ingreso\"", nullable = false)
    private @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate admissionDate;

    @ManyToOne
    @JoinColumn(name = "\"id_caracteristicas\"")
    private Features features;

    @ManyToOne
    @JoinColumn(name = "\"id_auditoria\"")
    private Audit audit;
}
