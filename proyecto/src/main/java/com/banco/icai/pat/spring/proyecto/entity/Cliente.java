package com.banco.icai.pat.spring.proyecto.entity;


import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long cliente_id;

    @Column(nullable = false,unique = true)
    public String dni;

    @Column(nullable = false)
    public String nombre;

    @Column(nullable = false)
    public String apellido_1;

    @Column(nullable = true) //Puede haber gente con unico apellido
    public String apellido_2;

    @Column(nullable = false,unique = true)
    public String email;

    @Column(nullable = false)
    public String telefono;

    @Column(nullable = false)
    public String password;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    public ArrayList<Cuenta> cuentas= new ArrayList<>();

}
