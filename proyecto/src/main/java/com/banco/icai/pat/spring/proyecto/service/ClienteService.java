package com.banco.icai.pat.spring.proyecto.service;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import com.banco.icai.pat.spring.proyecto.entity.Pago;
import com.banco.icai.pat.spring.proyecto.entity.Token;
import com.banco.icai.pat.spring.proyecto.model.*;
import com.banco.icai.pat.spring.proyecto.repository.ClientesRepository;
import com.banco.icai.pat.spring.proyecto.repository.CuentasRepository;
import com.banco.icai.pat.spring.proyecto.repository.PagosRepository;
import com.banco.icai.pat.spring.proyecto.repository.TokenRepository;
import com.banco.icai.pat.spring.proyecto.util.BancoTools;
import jakarta.servlet.ServletConfig;
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
    TokenRepository tokenRepository;;
    @Autowired
    private ServletConfig servletConfig;
    @Autowired
    private PagosRepository pagosRepository;

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


    public Cliente authentication(String tokenId) {
        Optional<Token> tokenO = tokenRepository.findById(Long.valueOf(tokenId));
        if (tokenO.isEmpty()) return null;
        Token token = tokenO.get();
        return token.getCliente();
    }

    public void logout(String tokenId) {
        Optional<Token> tokenO= tokenRepository.findById(Long.valueOf(tokenId));
        if (tokenO.isPresent()) {
            Token token = tokenO.get();
            tokenRepository.delete(token);
        }
    }

    public void crearCuenta(CrearCuenta crearCuenta) {
        Optional<Cliente> cliente0=clientesRepository.findByEmail(crearCuenta.cliente().email);
        if(cliente0.isPresent()){
            if(cuentasRepository.findByIban(crearCuenta.numeroCuenta()).isPresent()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Numero de cuenta ya usado");
            }
            Cliente cliente=cliente0.get();
            Cuenta cuenta= new Cuenta();
            cuenta.setIban(BancoTools.generarIban(Sucursal.valueOf(crearCuenta.sucursal()),crearCuenta.numeroCuenta()));
            cuenta.setSucursal(Sucursal.valueOf(crearCuenta.sucursal()));
            cuenta.setCliente(cliente);
            cliente.cuentas.add(cuenta);
            clientesRepository.save(cliente);
        }
    }

    public void hacerBizum(BizumRequest bizumRequest) {
        Optional<Cliente> cliente0=clientesRepository.findByTelefono(bizumRequest.telefono_destino());
        if(cliente0.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe el cliente destino");
        }
        Cliente cliente=cliente0.get();
        Optional<Cuenta> cuenta0=cuentasRepository.findByIban(bizumRequest.iban_origen());
        if(cuenta0.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe la cuenta origen");
        }
        Cuenta cuenta=cuenta0.get();
        if(cuenta.getSaldo()<bizumRequest.importe()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No hay suficiente saldo saldo");
        }
        if(cliente.getCuentas().size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El destinatario no tiene cuentas");
        }
        Cuenta cuenta_destino=cliente.getCuentas().get(0);
        cuenta.setSaldo(cuenta.getSaldo()-bizumRequest.importe());
        cuenta_destino.setSaldo(cuenta_destino.getSaldo()+bizumRequest.importe());
        Pago pago= new Pago();
        pago.setCuenta_origen(cuenta);
        pago.setCuenta_destino(cuenta_destino);
        pago.setImporte(bizumRequest.importe());
        pago.setTipo("bizum");
        pagosRepository.save(pago);
    }


}
