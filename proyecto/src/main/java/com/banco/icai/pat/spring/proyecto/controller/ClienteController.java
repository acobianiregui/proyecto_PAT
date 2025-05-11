package com.banco.icai.pat.spring.proyecto.controller;

import com.banco.icai.pat.spring.proyecto.model.ClientResponse;
import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import com.banco.icai.pat.spring.proyecto.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse register(@Valid @RequestBody RegisterRequest register) {
        try {
            return ClienteService.profile(register);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }



}
