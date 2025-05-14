package com.banco.icai.pat.spring.proyecto.entity;

import com.banco.icai.pat.spring.proyecto.model.Sucursal;
import com.banco.icai.pat.spring.proyecto.validacion.ValidIban;
import jakarta.persistence.*;

@Entity
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuenta_id;

    @Column(nullable = false,unique = true)
    @ValidIban
    private String iban;

    @Column(nullable = false)
    private double saldo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
