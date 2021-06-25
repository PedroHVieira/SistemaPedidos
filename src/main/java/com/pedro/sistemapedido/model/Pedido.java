package com.pedro.sistemapedido.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

/*
	RN03: No cadastro de Pedido:
	
	• Cliente (Irá listar todos os clientes ativos no sistema) (campo obrigatório)
	• Produto (irá listar todos os produtos ativos no sistema) (campo obrigatório)
	• Valor (Exibir o valor do produto selecionado)
	• O pedido poderá possuir um ou mais produtos.
	• O sistema deverá gravar a data e hora da realização do pedido
	• Deverá ser demonstrado o valor total do pedido.
*/

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "pedi_id")
	private Long id;
	
	@NotNull
	@Column(name = "pedi_valor_total")
	private Float valorTotal;
	
	@NotNull
	@Column(name = "pedi_quantidade")
	private Integer quantidade;
	
	@JoinColumn(name = "clie_id", referencedColumnName = "clie_id")
	@OneToOne
	private Cliente cliente;
	
	@NotNull
	@OneToMany( targetEntity=ItemPedido.class )
	private List<ItemPedido> itensPedido;
	
	@NotNull
	@Column(name = "pedi_dt_criacao")
	private Timestamp dataCriacao;
	
	public Pedido() {
		this.cliente = new Cliente();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
