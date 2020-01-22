package com.cursomc2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc2.domain.Cliente;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

    List<Cliente> findByEmail(String email);
    List<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

}
