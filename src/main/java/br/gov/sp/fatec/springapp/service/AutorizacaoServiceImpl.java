package br.gov.sp.fatec.springapp.service;

import java.util.HashSet;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.springapp.entity.Autorizacao;
import br.gov.sp.fatec.springapp.entity.Usuario;
import br.gov.sp.fatec.springapp.exception.RegistroNaoEncontradoException;
import br.gov.sp.fatec.springapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springapp.repository.UsuarioRepository;

@Service("autorizacaoService")
public class AutorizacaoServiceImpl implements AutorizacaoService {
	@Autowired
	private AutorizacaoRepository autorizacaoRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Override
	@Transactional
	public Autorizacao novaAutorizacao(String nome, String emailUsuario) {
		Autorizacao aut = new Autorizacao();
		aut.setNome(nome);
		autorizacaoRepo.save(aut);
		
		Usuario usuario = usuarioRepo.buscarUsuarioPorEmail(emailUsuario);
		if (usuario == null) {
			throw new UsernameNotFoundException(
					"Usuário com email " + emailUsuario + " não foi encontrado");
		}
		
		usuario.setAutorizacoes(new HashSet<Autorizacao>());
		usuario.getAutorizacoes().add(aut);
		usuarioRepo.save(usuario);
		
		return aut;
	}

	@Override
	public Autorizacao buscarAutorizacaoPorNome(String nome) {
		Autorizacao autorizacao = autorizacaoRepo.findByNome(nome);
		
		if (autorizacao != null) {
			return autorizacao;
		}
		throw new RegistroNaoEncontradoException("Autorizacao nao encontrada!");
		
	}
}
