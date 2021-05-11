package br.gov.sp.fatec.springapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springapp.controller.View;

@Entity
@Table(name = "usr_usuario")
public class Usuario {
	
	@JsonView(View.UsuarioResumo.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usr_id")
	private Long id;
	
	@JsonView({View.UsuarioResumo.class, View.AutorizacaoResumo.class, View.EstrelaResumo.class})
	@Column(name = "usr_nome", unique = true, length = 20, nullable = false)
	private String nome;
	@Column(name = "usr_email", unique = true, length = 100, nullable = false)
	private String email;
	@Column(name = "usr_senha", length = 100, nullable = false)
	private String senha;
	
	@JsonView(View.UsuarioResumo.class)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "uau_usuario_autorizacao",
		joinColumns = { @JoinColumn(name = "usr_id") },
		inverseJoinColumns = { @JoinColumn(name = "aut_id") }
	)
	private Set<Autorizacao> autorizacoes;
	
	@JsonView(View.UsuarioResumo.class)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "descobridor")
	private Set<Estrela> estrelas;

//Getters e Setters
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(Set<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	public Set<Estrela> getEstrelas() {
		return estrelas;
	}

	public void setEstrelas(Set<Estrela> estrelas) {
		this.estrelas = estrelas;
	}

}
