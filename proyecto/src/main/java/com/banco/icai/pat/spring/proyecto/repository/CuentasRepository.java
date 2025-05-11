package com.banco.icai.pat.spring.proyecto.repository;

import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CuentasRepository extends CrudRepository<Cuenta, Long> {
    Optional<Cuenta> findByIban(String iban);
}
