package com.pedro.sistemapedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.sistemapedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
