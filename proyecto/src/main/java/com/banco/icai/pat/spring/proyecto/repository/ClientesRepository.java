package com.banco.icai.pat.spring.proyecto.repository;

import com.banco.icai.pat.spring.proyecto.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientesRepository extends CrudRepository<Cliente, Long> {
    Optional<Cliente> findBydni(String dni);
}
