package br.gov.sp.fatec.springapp.service;

import br.gov.sp.fatec.springapp.entity.Usuario;

public interface UsuarioService {
	public Usuario novoUsuario(String nome, String email, String senha, String nomeAutorizacao);

}
