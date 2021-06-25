package com.pedro.sistemapedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pedro.sistemapedido.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query(value="SELECT * FROM produto WHERE prod_nome LIKE %?1% and prod_ativo = 1",nativeQuery = true)
	List<Produto> findByNomeAutoComplete(String nome);
}
