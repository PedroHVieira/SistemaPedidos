package com.pedro.sistemapedido.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "roles")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "role_id")
	private Long id;
	
	@NotNull
	@Column(name = "role_nome")
	private String nome;
	
	@NotNull
	@Column(name = "role_dt_criacao")
	private Timestamp data_criacao;
	
	public Roles(String nome, Timestamp data) {
		this.nome = nome;
		this.data_criacao = data;
	}
	
	public Roles() {
		
	}

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

	public Timestamp getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(Timestamp data_criacao) {
		this.data_criacao = data_criacao;
	}
}
