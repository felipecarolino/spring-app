package br.gov.sp.fatec.springapp.service;

import br.gov.sp.fatec.springapp.entity.Autorizacao;

public interface AutorizacaoService {
	public Autorizacao novaAutorizacao(String nome, String emailUsuario);
	
	public Autorizacao buscarAutorizacaoPorNome(String nome);

}
