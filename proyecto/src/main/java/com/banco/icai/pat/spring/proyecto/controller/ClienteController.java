package com.banco.icai.pat.spring.proyecto.controller;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.entity.Token;
import com.banco.icai.pat.spring.proyecto.model.*;
import com.banco.icai.pat.spring.proyecto.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/api/royale/cuentas")
    public ResponseEntity<Void> crearCuenta(@Valid @RequestBody CrearCuenta crearCuenta,
                                            @CookieValue(value = "session", required = true) String session) {
        Cliente cliente= clienteService.authentication(session);
        if (cliente == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        try {
            clienteService.crearCuenta(crearCuenta,session);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @DeleteMapping("/api/royale")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(@CookieValue(value = "session", required = true) String session) {
        Cliente cliente = clienteService.authentication(session);
        if (cliente == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        clienteService.logout(session);
        ResponseCookie expireSession = ResponseCookie
                .from("session")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).header(HttpHeaders.SET_COOKIE, expireSession.toString()).build();
    }

    @PostMapping("/api/royale/bizum")
    public ResponseEntity<Void> hacerBizum(@Valid @RequestBody BizumRequest bizumRequest,
                                           @CookieValue(value = "session", required = true) String session){
        try {
            clienteService.hacerBizum(bizumRequest,session);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @GetMapping("/api/royale")
    public ResponseEntity<ClientResponse> getCliente(@CookieValue(value = "session", required = true) String session) {
        Cliente cliente = clienteService.authentication(session);
        if (cliente == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ClientResponse response = clienteService.profile(cliente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/api/royale/transferencia")
    public ResponseEntity<Void> transferencia(@Valid @RequestBody TransferenciaRequest transferencia,
                                              @CookieValue(value = "session", required = true) String session) {
        try{
            clienteService.realizar_transferencia(transferencia,session);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PutMapping("/api/royale/cuenta/saldo")
    public ResponseEntity<Void> modificarSaldo(@Valid @RequestBody SaldoModRequest modificarSaldo,
                                               @CookieValue(value = "session", required = true) String session) {
        try {
            clienteService.modificarSaldo(modificarSaldo,session);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

}

