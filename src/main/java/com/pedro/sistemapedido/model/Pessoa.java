package com.pedro.sistemapedido.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

@Entity
@Table(name = "pessoa")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "pess_id")
	private Long id;

	@NotNull
	@Column(name = "pess_nome")
	private String nome;

	@NotNull
	@Column(name = "pess_email")
	private String email;

	@NotNull
	@Column(name = "pess_dt_criacao")
	private Timestamp data_criacao;

	@Nullable
	@Column(name = "pess_obs", columnDefinition = "TEXT")
	private String obs;

	public Pessoa(String nome, String email, Timestamp data_criacao, String obs) {
		this.nome = nome;
		this.email = email;
		this.data_criacao = data_criacao;
		this.obs = obs;
	}

	public Pessoa() {

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(Timestamp data_criacao) {
		this.data_criacao = data_criacao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
	
	
}
