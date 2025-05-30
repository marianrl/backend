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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "\"Usuario\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "\"nombre\"", nullable = false)
    private String name;

    @Column(name = "\"apellido\"", nullable = false)
    private String lastName;

    @Column(name = "\"mail\"", nullable = false)
    private String mail;

    @Column(name = "\"contraseña\"", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "\"id_rol\"")
    private Role role;

}
