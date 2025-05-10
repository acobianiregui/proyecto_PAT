package com.banco.icai.pat.spring.proyecto.entity;

import jakarta.persistence.*;

@Entity
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String iban;

    @Column(nullable = false)
    private double saldo;

    @Column(nullable = false)
    private String sucursal;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
