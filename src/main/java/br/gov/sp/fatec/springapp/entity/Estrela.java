	package br.gov.sp.fatec.springapp.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "est_estrela")
public class Estrela {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "est_id")
	private Long id;
	@Column(name = "est_nome", unique = true, length = 100, nullable = false)
	private String nome;
	@Column(name = "est_ra", length = 11, nullable = false)
	private String ra;
	@Column(name = "est_dec", length = 12, nullable = false)
	private String dec;
	@Column(name = "est_tipo", length = 10, nullable = false)
	private String tipo;
	@Column(name = "est_data_hora", nullable = false)
	private Date dataHora;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usr_descobridor_id")
	private Usuario descobridor;
	

	// Getters e Setters
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
	public String getRa() {
		return ra;
	}
	public void setRa(String ra) {
		this.ra = ra;
	}
	public String getDec() {
		return dec;
	}
	public void setDec(String dec) {
		this.dec = dec;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public Usuario getDescobridor() {
		return descobridor;
	}
	public void setDescobridor(Usuario descobridor) {
		this.descobridor = descobridor;
	}
	
	
}