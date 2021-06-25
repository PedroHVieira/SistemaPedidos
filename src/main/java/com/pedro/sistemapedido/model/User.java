package com.pedro.sistemapedido.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "user_id")
	private Long id;

	@NotNull
	@Column(name = "user_ativo")
	private Boolean ativo;

	@NotNull
	@Column(name = "user_senha")
	private String senha;

	@NotNull
	@Column(name = "user_dt_criacao")
	private Timestamp data_criacao;

	@JoinColumn(name = "pess_id", referencedColumnName = "pess_id")
	@OneToOne
	private Pessoa pessoa;

	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	@OneToOne
	private Roles roles;

	public User(String senha, Timestamp data, Pessoa pessoa, Roles roles) {
		this.ativo = true;
		this.senha = senha;
		this.data_criacao = data;
		this.pessoa = pessoa;
		this.roles = roles;
	}

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Timestamp getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(Timestamp data_criacao) {
		this.data_criacao = data_criacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

}
