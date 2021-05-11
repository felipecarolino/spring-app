package br.gov.sp.fatec.springapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springapp.controller.View;

@Entity
@Table(name = "aut_autorizacao")
public class Autorizacao {
	
	@JsonView(View.UsuarioCompleto.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aut_id")
	private Long id;
	
	@JsonView({View.UsuarioResumo.class, View.AutorizacaoResumo.class})
	@Column(name = "aut_nome", unique = true, length = 20, nullable = false)
	private String nome;
	
	@JsonView(View.AutorizacaoResumo.class)
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "autorizacoes")
	private Set<Usuario> usuarios;

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

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
