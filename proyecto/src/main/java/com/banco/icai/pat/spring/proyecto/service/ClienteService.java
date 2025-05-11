package com.banco.icai.pat.spring.proyecto.service;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.model.ClientResponse;
import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import com.banco.icai.pat.spring.proyecto.repository.ClientesRepository;
import com.banco.icai.pat.spring.proyecto.repository.CuentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class ClienteService {
    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    CuentasRepository cuentasRepository;


    public static ClientResponse profile(Cliente cliente) {
        if (cliente == null) return null;
        ClientResponse clientResponse = new ClientResponse(cliente.email, cliente.nombre, cliente.cuentas);
        return clientResponse;
    }


    public static ClientResponse profile(RegisterRequest register) {
        Cliente cliente = new Cliente();
        cliente.nombre = register.nombre();
        cliente.apellido_1= register.apellido();
        cliente.email = register.email();
        cliente.telefono = register.telefono();
        cliente.password = register.password();
        return profile(cliente);
    }

}
