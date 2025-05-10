package com.banco.icai.pat.spring.proyecto.controller;

import com.banco.icai.pat.spring.proyecto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    
}
