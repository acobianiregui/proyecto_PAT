package com.banco.icai.pat.spring.proyecto.model;

import com.banco.icai.pat.spring.proyecto.entity.Cuenta;

import java.util.ArrayList;
import java.util.List;

public record ClientResponse (
        String email,
        String nombre,
        List<Cuenta> cuentas
)
{}
