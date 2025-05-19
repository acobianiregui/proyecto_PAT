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

    TokenRepository tokenRepository;
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

            if (token != null) return token;

            token = new Token();
            token.setCliente(cliente1);
            return tokenRepository.save(token);
        }
        return null;
    }

    public ClientResponse profile(Cliente cliente) {
        if (cliente == null) return null;
        clientesRepository.save(cliente);
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
        cliente.setDni(register.dni());
        return profile(cliente);
    }
    public void realizar_transferencia(TransferenciaRequest transferencia, String tokenId) {
        Optional<Token> token= tokenRepository.findById(Long.valueOf(Long.parseLong(tokenId)));
        if(token.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No existe el token");}
        Cliente cliente=clientesRepository.findByEmail(token.get().getCliente().getEmail()).orElse(null);
        if(cliente == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No existe el cliente");
        }

        Cuenta destino = cuentasRepository.findByIban(transferencia.iban_cuenta_destino()).orElse(null);
        if(destino == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta destino no encontrada");
        }
        Cuenta origen = cuentasRepository.findByIban(transferencia.iban_cuenta_origen()).orElse(null);
        if (origen == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta origen no encontrada");
        }
        if(!origen.getCliente().getEmail().equals(cliente.getEmail())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No tienes permiso para acceder a esta cuenta");
        }

        if(origen.getSaldo() < transferencia.importe()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }
        //Si llegamos aqui todo OK
        origen.setSaldo(origen.getSaldo()-transferencia.importe());
        destino.setSaldo(destino.getSaldo()+transferencia.importe());
        cuentasRepository.save(origen);
        cuentasRepository.save(destino);
        Pago pago= new Pago();
        pago.setCuenta_origen(origen);
        pago.setCuenta_destino(destino);
        pago.setImporte(transferencia.importe());
        pago.setTipo("transferencia");
        pagosRepository.save(pago);

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

    public void crearCuenta(CrearCuenta crearCuenta, String tokenId) {
        Optional<Token> token= tokenRepository.findById(Long.valueOf(Long.parseLong(tokenId)));
        if(token.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No existe el token");}

        Optional<Cliente> cliente0=clientesRepository.findByEmail(token.get().getCliente().getEmail());
        if(cliente0.isPresent()){
            if(cuentasRepository.findByIban(crearCuenta.numeroCuenta()).isPresent()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Numero de cuenta ya usado");
            }
            Cliente cliente=cliente0.get();
            Cuenta cuenta= new Cuenta();
            cuenta.setIban(BancoTools.generarIban(Sucursal.valueOf(crearCuenta.sucursal()),crearCuenta.numeroCuenta()));
            cuenta.setSucursal(Sucursal.valueOf(crearCuenta.sucursal()));
            cuenta.setCliente(cliente);
            cuenta.setSaldo(50.0);
            cliente.cuentas.add(cuenta);
            clientesRepository.save(cliente);
        }
    }

    public void hacerBizum(BizumRequest bizumRequest, String tokenId) {
        Cliente cliente_orig=authentication(tokenId);
        Optional<Cliente> cliente0=clientesRepository.findByTelefono(bizumRequest.telefono_destino());
        if(cliente0.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe el cliente destino");
        }
        Cliente cliente=cliente0.get();
        Optional<Cuenta> cuenta0=cuentasRepository.findByIban(bizumRequest.iban_origen());
        if(cuenta0.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe la cuenta origen");
        }
        //IMPORTANTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if(!cuenta0.get().getCliente().getEmail().equals(cliente_orig.getEmail())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No tienes permiso para acceder a esta cuenta");
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
    public void realizarCompra(CompraRequest compraRequest,String tokenid){
        Optional<Token> token= tokenRepository.findById(Long.valueOf(tokenid));
        if(token.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No existe el token");}
        Optional<Cliente> cliente0=clientesRepository.findByEmail(token.get().getCliente().getEmail());
        if(cliente0.isPresent()){
            Optional<Cuenta> cuenta0=cuentasRepository.findByIban(compraRequest.iban());
            if(cuenta0.isPresent()){
                Cuenta cuenta=cuenta0.get();
                if(!cuenta.getCliente().getEmail().equals(cliente0.get().getEmail())){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No tienes permiso para acceder a esta cuenta");
                }
                if(cuenta.getSaldo()<compraRequest.importe()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No hay suficiente saldo saldo");
                }
                cuenta.setSaldo(cuenta.getSaldo()-compraRequest.importe());
                cuentasRepository.save(cuenta);
                //GUARDAR LA COMPRA AQUIIIIIIIIIIIIIIIIIIIIIIIII

            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe la cuenta");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe el cliente");
        }

    }
    public void modificarSaldo(SaldoModRequest operacion, String tokenid){
        Optional<Token> token= tokenRepository.findById(Long.valueOf(tokenid));
        if(token.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No existe el token");}
        Optional<Cliente> cliente0=clientesRepository.findByEmail(token.get().getCliente().getEmail());
        if(cliente0.isPresent()){
            Optional<Cuenta> cuenta0=cuentasRepository.findByIban(operacion.iban());
            if(cuenta0.isPresent()){
                Cuenta cuenta=cuenta0.get();
                if(!cuenta.getCliente().getEmail().equals(cliente0.get().getEmail())){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"No tienes permiso para acceder a esta cuenta");
                }
                if(operacion.tipoOperacion().equals("ingreso")){
                    cuenta.setSaldo(cuenta.getSaldo()+operacion.importe());
                }else if (operacion.tipoOperacion().equals("retirada")){
                    if(cuenta.getSaldo()<operacion.importe()){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No hay suficiente saldo saldo");
                    }
                    cuenta.setSaldo(cuenta.getSaldo()-operacion.importe());
                }else{
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tipo de operacion no valido");
                }
                cuentasRepository.save(cuenta);
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe la cuenta");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existe el cliente");
        }
    }


}
