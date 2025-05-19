package com.banco.icai.pat.spring.proyecto.repository;

import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import com.banco.icai.pat.spring.proyecto.entity.Pago;
import com.banco.icai.pat.spring.proyecto.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PagosRepository extends CrudRepository<Pago,Long> {
    List<Pago> findByCuentaOrigen(Cuenta cuenta);

    List<Pago> findByCuentaDestino(Cuenta cuenta);
}
