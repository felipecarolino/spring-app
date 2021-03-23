package br.gov.sp.fatec.springapp.service;

import br.gov.sp.fatec.springapp.entity.Estrela;

public interface EstrelaService {
	public Estrela adicionarEstrela(String identificadorUsuario, String nome, String ra, String dec, String tipo);
}
