package com.banco.icai.pat.spring.proyecto.repository;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import com.banco.icai.pat.spring.proyecto.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token,Long> {
    Token findByCliente(Cliente cliente);
}
