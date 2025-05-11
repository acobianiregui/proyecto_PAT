package com.banco.icai.pat.spring.proyecto.service;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.model.ClientResponse;
import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import com.banco.icai.pat.spring.proyecto.repository.ClientesRepository;
import com.banco.icai.pat.spring.proyecto.repository.CuentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    CuentasRepository cuentasRepository;

    public ClientResponse login(String email, String password) {
        Optional<Cliente> cliente = clientesRepository.findByEmail(email);
        if (cliente.isEmpty()) return null;
        if(cliente.get().password.equals(password)) {
            ClientResponse clienteresponse = new ClientResponse(cliente.get().email, cliente.get().nombre, cliente.get().cuentas);
            return clienteresponse;
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

}
