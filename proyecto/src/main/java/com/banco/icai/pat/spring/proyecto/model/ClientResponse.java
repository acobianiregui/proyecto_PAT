package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.entity.Cuenta;

import java.util.ArrayList;

public record ClientResponse (
        String email,
        String nombre,
        ArrayList<Cuenta> cuentas
)
{}
