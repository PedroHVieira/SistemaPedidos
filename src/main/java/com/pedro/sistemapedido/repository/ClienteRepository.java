package com.pedro.sistemapedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pedro.sistemapedido.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query(value="SELECT * FROM cliente WHERE clie_nome LIKE %?1% and clie_ativo = 1",nativeQuery = true)
	List<Cliente> findByNomeAutoComplete(String nome);
}
