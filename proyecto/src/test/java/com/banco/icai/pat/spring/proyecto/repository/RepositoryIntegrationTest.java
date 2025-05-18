package com.banco.icai.pat.spring.proyecto.repository;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import com.banco.icai.pat.spring.proyecto.entity.Token;
import com.banco.icai.pat.spring.proyecto.model.Sucursal;
import com.banco.icai.pat.spring.proyecto.util.BancoTools;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RepositoryIntegrationTest {
    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    CuentasRepository cuentasRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Test
    public void guardarTest(){ //Guardaremos un cliente con 2 cuentas
        cuentasRepository.deleteAll();
        clientesRepository.deleteAll();
        Cliente cliente= new Cliente();
        Cuenta cuenta1= new Cuenta();
        Cuenta cuenta2= new Cuenta();
        Token token= new Token();
        token.setCliente(cliente);

        //Cuenta 1
        cuenta1.setIban(BancoTools.generarIban(Sucursal.BARCELONA,"0001234567"));
        cuenta1.setSaldo(0);
        cuenta1.setSucursal(Sucursal.BARCELONA);
        cuenta1.setCliente(cliente);
        //Cuenta 2
        cuenta2.setIban(BancoTools.generarIban(Sucursal.MADRID,"7771234567"));
        cuenta2.setSaldo(0);
        cuenta2.setSucursal(Sucursal.MADRID);
        cuenta2.setCliente(cliente);
        //Cliente
        //cliente.setCliente_id(1L);
        cliente.setNombre("Anton");
        cliente.setDni("12345678Z");
        cliente.setApellido("Cobian");
        cliente.setEmail("pepelarana@gmail.com");
        cliente.setTelefono("640453289");
        cliente.setPassword("Aventura8");
        ArrayList<Cuenta> cuentas= new ArrayList<>();
        cuentas.add(cuenta1);
        cuentas.add(cuenta2);
        cliente.setCuentas(cuentas);

        //Guardamos
        clientesRepository.save(cliente);
        cuentasRepository.save(cuenta1);
        cuentasRepository.save(cuenta2);
        tokenRepository.save(token);

        //Comprobar que estan ahi
        assertEquals(1,clientesRepository.count());
        assertEquals(2,cuentasRepository.count());

        Cliente busqueda_cl = clientesRepository.findBydni(cliente.getDni()).orElse(null);
        Cuenta bc1= cuentasRepository.findByIban(cuenta1.getIban()).orElse(null);
        Cuenta bc2= cuentasRepository.findByIban(cuenta2.getIban()).orElse(null);
        Token t1= tokenRepository.findByCliente(cliente);

        //No deben ser null
        assertNotNull(busqueda_cl);
        assertNotNull(bc1);
        assertNotNull(bc2);
        assertNotNull(t1);


        //dni e email
        assertEquals(cliente.getDni(),busqueda_cl.getDni());
        assertEquals(cliente.getEmail(),busqueda_cl.getEmail());
        //iban y cliente
        assertEquals(cuenta1.getIban(),bc1.getIban());
        assertEquals(cuenta2.getIban(),bc2.getIban());
        assertEquals(bc1.getCliente(),busqueda_cl);
        assertEquals(bc2.getCliente(),busqueda_cl);




    }

    @Test
    public void borradoCompleto(){ //Al borrar un cliente, se debe borrar to_do lo asociado
        Cliente cliente= new Cliente();
        Cuenta cuenta1= new Cuenta();
        Cuenta cuenta2= new Cuenta();
        Token token= new Token();
        token.setCliente(cliente);

        //Cuenta 1
        cuenta1.setIban(BancoTools.generarIban(Sucursal.BARCELONA,"0001234567"));
        cuenta1.setSaldo(0);
        cuenta1.setSucursal(Sucursal.BARCELONA);
        cuenta1.setCliente(cliente);
        //Cuenta 2
        cuenta2.setIban(BancoTools.generarIban(Sucursal.MADRID,"7771234567"));
        cuenta2.setSaldo(0);
        cuenta2.setSucursal(Sucursal.MADRID);
        cuenta2.setCliente(cliente);
        //Cliente
        cliente.setNombre("Anton");
        cliente.setDni("12345678Z");
        cliente.setApellido("Cobian");
        cliente.setEmail("pepelarana@gmail.com");
        cliente.setTelefono("640453289");
        cliente.setPassword("Aventura8");
        ArrayList<Cuenta> cuentas= new ArrayList<>();
        cuentas.add(cuenta1);
        cuentas.add(cuenta2);
        cliente.setCuentas(cuentas);

        //Guardarlos
        clientesRepository.save(cliente);
        cuentasRepository.save(cuenta1);
        cuentasRepository.save(cuenta2);
        tokenRepository.save(token);

        //Comprobar que estan ahi
        assertEquals(1,clientesRepository.count());
        assertEquals(2,cuentasRepository.count());

        Cliente busqueda= clientesRepository.findBydni(cliente.getDni()).orElse(null);
        assertNotNull(busqueda);
        //Borrar el cliente y comprobar que se han borrado
        clientesRepository.delete(busqueda);
        assertEquals(0,clientesRepository.count());
        assertEquals(0,cuentasRepository.count());
        assertEquals(0,tokenRepository.count());
    }
    @Test
    public void integridadTest(){
        //Si guardo una cuenta sin cliente se deberia lanzar un error de integridad
        Cuenta cuenta= new Cuenta();
        cuenta.setIban(BancoTools.generarIban(Sucursal.MADRID,"0200051332"));
        cuenta.setSucursal(Sucursal.MADRID);
        cuenta.setSaldo(400);
        //No le pongo cliente


        assertThrows(DataIntegrityViolationException.class, () -> {
            cuentasRepository.save(cuenta); // Aquí se lanza la excepción
        });

    }
}
