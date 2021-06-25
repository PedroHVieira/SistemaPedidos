package com.pedro.sistemapedido.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

/*
	RN02: No formulário de cadastro de Produto, o formulário deverá possuir os seguintes campos:
	
	• Nome (campo obrigatório)
	• Valor (campo obrigatório)
*/

@Entity
@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "prod_id")
	private Long id;
	
	@NotNull
	@Column(name = "prod_nome")
	private String nome;
	
	@NotNull
	@Column(name = "prod_ativo")
	private Boolean ativo;
	
	@NotNull
	@Column(name = "prod_valor")
	private Float valor;
	
	@NotNull
	@Column(name = "clie_dt_criacao")
	private Timestamp dataCriacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
		
}
