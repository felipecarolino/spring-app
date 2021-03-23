package br.gov.sp.fatec.springapp.service;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.springapp.entity.Autorizacao;
import br.gov.sp.fatec.springapp.entity.Usuario;
import br.gov.sp.fatec.springapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springapp.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private AutorizacaoRepository autorizacaoRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Override
	@Transactional
	public Usuario novoUsuario(String nome, String email, String senha, String nomeAutorizacao) {
		Autorizacao autorizacao = autorizacaoRepo.findByNome(nomeAutorizacao);
		if (autorizacao == null) {
			autorizacao = new Autorizacao();
			autorizacao.setNome(nomeAutorizacao);
			autorizacaoRepo.save(autorizacao);
		}
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		usuario.setAutorizacoes(new HashSet<Autorizacao>());
		usuario.getAutorizacoes().add(autorizacao);
		usuarioRepo.save(usuario);
		return usuario;
	}
}
