package br.gov.sp.fatec.springapp.service;

import java.util.List;

import br.gov.sp.fatec.springapp.entity.Estrela;

public interface EstrelaService {
	public Estrela adicionarEstrela(Long idUsuario, String nome, String ra, String dec, String tipo);
	
	public List<Estrela> buscarTodas();
	
	public Estrela buscarPorId(Long id);
	
	public Estrela buscarPorNome(String nome);
	
	public Estrela editarEstrela(Long id, String nome, String ra, String dec, String tipo);
	
	public Estrela deletarEstrela(Long id);
}
