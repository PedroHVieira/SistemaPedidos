package com.pedro.sistemapedido.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*RN01: No formulário de cadastro de Cliente, o formulário deverá possuir os seguintes campos:
• Nome (campo obrigatório)
• Telefone (campo obrigatório)
• Data de Nascimento (campo obrigatório)*/

import com.sun.istack.NotNull;

@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "clie_id")
	private Long id;
	
	@NotNull
	@Column(name = "clie_nome")
	private String nome;
	
	@NotNull
	@Column(name = "clie_ativo")
	private Boolean ativo;
	
	@NotNull
	@Column(name = "clie_telefone")
	private String telefone;
	
	@NotNull
	@Column(name = "clie_dt_criacao")
	private Timestamp dataCriacao;
	
	@NotNull
	@Column(name = "clie_dt_nascimento")
	private Date dataNascimento;

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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	
}
