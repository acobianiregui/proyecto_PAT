package com.banco.icai.pat.spring.proyecto.repository;

import com.banco.icai.pat.spring.proyecto.entity.Pago;
import com.banco.icai.pat.spring.proyecto.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface PagosRepository extends CrudRepository<Pago,Long> {
}
