package com.banco.icai.pat.spring.proyecto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Pattern(regexp = "^(bizum|transferencia|pago)$")
    private String tipo;

    @Column(nullable = false)
    @NotNull
    private int importe;

    @JoinColumn(name = "cuenta_origen_id", nullable = false)
    @ManyToOne
    @NotNull
    private Cuenta cuenta_origen;

    @JoinColumn(name = "cuenta_destino_id")
    @ManyToOne
    private Cuenta cuenta_destino;

    @Column(nullable = false)
    @NotNull
    private String concepto;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getImporte() {
        return importe;
    }
    public void setImporte(int importe) {
        this.importe = importe;
    }
    public Cuenta getCuenta_origen() {
        return cuenta_origen;
    }
    public void setCuenta_origen(Cuenta cuenta_origen) {
        this.cuenta_origen = cuenta_origen;
    }
    public Cuenta getCuenta_destino() {
        return cuenta_destino;
    }
    public void setCuenta_destino(Cuenta cuenta_destino) {
        this.cuenta_destino = cuenta_destino;
    }
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

}
