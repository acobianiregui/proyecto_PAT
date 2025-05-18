package com.banco.icai.pat.spring.proyecto.controller;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.entity.Token;
import com.banco.icai.pat.spring.proyecto.model.ClientResponse;
import com.banco.icai.pat.spring.proyecto.model.LoginRequest;
import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import com.banco.icai.pat.spring.proyecto.model.TransferenciaRequest;
import com.banco.icai.pat.spring.proyecto.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.management.StringValueExp;

@RestController
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @PostMapping("/api/royale")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientResponse> register(@Valid @RequestBody RegisterRequest register) {
        try {
            ClientResponse response = clienteService.profile(register);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PostMapping("/api/royale/users")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest credentials) {
        Token token = clienteService.login(credentials.email(), credentials.password());
        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ResponseCookie session = ResponseCookie
                .from("session", String.valueOf(token.getId()))
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, session.toString()).build();
    }

    
    @PostMapping("/api/royale/transferencia")
    public ResponseEntity<Void> transferencia(@Valid @RequestBody TransferenciaRequest transferencia) {
        try{
            clienteService.realizar_transferencia(transferencia);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }
}

