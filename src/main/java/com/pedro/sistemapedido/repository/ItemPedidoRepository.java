package com.pedro.sistemapedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.sistemapedido.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{

}
