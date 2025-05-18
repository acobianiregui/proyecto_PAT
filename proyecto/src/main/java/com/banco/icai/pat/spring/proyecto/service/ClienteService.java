package com.banco.icai.pat.spring.proyecto.service;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import com.banco.icai.pat.spring.proyecto.entity.Pago;
import com.banco.icai.pat.spring.proyecto.entity.Token;
import com.banco.icai.pat.spring.proyecto.model.ClientResponse;
import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import com.banco.icai.pat.spring.proyecto.model.TransferenciaRequest;
import com.banco.icai.pat.spring.proyecto.repository.ClientesRepository;
import com.banco.icai.pat.spring.proyecto.repository.CuentasRepository;
import com.banco.icai.pat.spring.proyecto.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    CuentasRepository cuentasRepository;
    @Autowired
    TokenRepository tokenRepository;

    public Token login(String email, String password) {
        Optional<Cliente> cliente = clientesRepository.findByEmail(email);
        if (cliente.isEmpty()) return null;
        if(cliente.get().password.equals(password)) {
            Cliente cliente1=cliente.get();
            Token token = tokenRepository.findByCliente(cliente1);
            if (token == null) return token;

            token = new Token();
            token.setCliente(cliente1);
            return tokenRepository.save(token);
        }
        return null;
    }

    public ClientResponse profile(Cliente cliente) {
        if (cliente == null) return null;
        ClientResponse clientResponse = new ClientResponse(cliente.email, cliente.nombre, cliente.cuentas);
        return clientResponse;
    }

    public ClientResponse profile(RegisterRequest register) {
        Cliente cliente = new Cliente();
        cliente.nombre = register.nombre();
        cliente.apellido = register.apellido();
        cliente.email = register.email();
        cliente.telefono = register.telefono();
        cliente.password = register.password();
        return profile(cliente);
    }
    public void realizar_transferencia(TransferenciaRequest transferencia) {
        Cuenta origen = cuentasRepository.findByIban(transferencia.iban_cuenta_origen()).orElse(null);
        if(origen == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta origen no encontrada");
        }
        Cuenta destino = cuentasRepository.findByIban(transferencia.iban_cuenta_destino()).orElse(null);
        if(destino == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta destino no encontrada");
        }
        if(origen.getSaldo() < transferencia.importe()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }
        //Si llegamos aqui todo OK
        origen.setSaldo(origen.getSaldo()-transferencia.importe());
        destino.setSaldo(destino.getSaldo()+transferencia.importe());
        cuentasRepository.save(origen);
        cuentasRepository.save(destino);

    }




}
