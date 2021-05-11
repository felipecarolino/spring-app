package br.gov.sp.fatec.springapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;

import br.gov.sp.fatec.springapp.entity.Autorizacao;
import br.gov.sp.fatec.springapp.entity.Usuario;
import br.gov.sp.fatec.springapp.exception.RegistroNaoEncontradoException;
import br.gov.sp.fatec.springapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springapp.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private AutorizacaoRepository autorizacaoRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private PasswordEncoder passEncoder;

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
		usuario.setSenha(passEncoder.encode(senha));
		usuario.setAutorizacoes(new HashSet<Autorizacao>());
		usuario.getAutorizacoes().add(autorizacao);
		usuarioRepo.save(usuario);
		return usuario;
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<Usuario> buscarTodos() {

		return usuarioRepo.findAll();
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Usuario buscarPorId(Long id) {
		Optional<Usuario> usuario = usuarioRepo.findById(id);
		if (usuario.isPresent()) {
			return usuario.get();
		}
		throw new RegistroNaoEncontradoException("Usuario nao encontrado");
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Usuario buscarPorNome(String nome) {
		Usuario usuario = usuarioRepo.buscaUsuarioPorNome(nome);

		if (usuario != null) {
			return usuario;
		}
		throw new RegistroNaoEncontradoException("Usuario nao encontrado");
	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Usuario editarUsuario(Long id, String nome, String email, String senha, Set<Autorizacao> autorizacoes) {

		Optional<Usuario> usuario = usuarioRepo.findById(id);
		if (usuario.isPresent()) {
			Usuario usuarioE = usuario.get();
			usuarioE.setNome(nome);
			;
			usuarioE.setEmail(email);
			usuarioE.setSenha(senha);
			if (usuarioE.getAutorizacoes() != autorizacoes) {
				for (Autorizacao aut : autorizacoes) {
					if (autorizacaoRepo.findByNome(aut.getNome()) == null) {
						Autorizacao autorizacao = new Autorizacao();
						autorizacao.setNome(aut.getNome());
						autorizacaoRepo.save(autorizacao);
						usuarioE.getAutorizacoes().add(autorizacao);
					} else {
						usuarioE.getAutorizacoes().add(autorizacaoRepo.findByNome(aut.getNome()));
					}
				}
			}
			usuarioRepo.save(usuarioE);
			return usuarioE;
		}
		throw new RegistroNaoEncontradoException("Usuario nao encontrado");

	}
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Usuario deletarUsuario(Long id) {
		Optional<Usuario> usuario = usuarioRepo.findById(id);
		if (usuario.isPresent() && usuario.get().getEstrelas().size() != 0) {
			throw new RegistroNaoEncontradoException("Usuario possui estrelas atreladas");
		}

		if (usuario.isPresent()) {
			usuarioRepo.deleteById(usuario.get().getId());
		} else {
			throw new RegistroNaoEncontradoException("Usuario nao encontrado");
		}
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepo.findByNome(username);
		if (usuario == null) {
          throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
      }
		
		return User.builder().username(username).password(usuario.getSenha()).authorities(usuario.getAutorizacoes().stream().map(Autorizacao::getNome).collect(Collectors.toList()).toArray(new String[usuario.getAutorizacoes().size()])).build();
	}
}
