package br.gov.sp.fatec.springapp.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.gov.sp.fatec.springapp.entity.Autorizacao;
import br.gov.sp.fatec.springapp.entity.Usuario;

public interface UsuarioService extends UserDetailsService {
	public Usuario novoUsuario(String nome, String email, String senha, String nomeAutorizacao);
	
	public Usuario editarUsuario(Long id, String nome, String email, String senha, Set<Autorizacao> autorizacoes );
	
	public List<Usuario> buscarTodos();
	
	public Usuario buscarPorId(Long id);
	
	public Usuario buscarPorNome(String nome);
	
	public Usuario deletarUsuario(Long id);
	
	public String buscarAut(String nome);
	
	public String buscarId(String nome);

}
