package br.gov.sp.fatec.springapp.service;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.springapp.entity.Estrela;
import br.gov.sp.fatec.springapp.entity.Usuario;
import br.gov.sp.fatec.springapp.exception.RegistroNaoEncontradoException;
import br.gov.sp.fatec.springapp.repository.EstrelaRepository;
import br.gov.sp.fatec.springapp.repository.UsuarioRepository;

@Service("estrelaService")
public class EstrelaServiceImpl implements EstrelaService {
	@Autowired
	private EstrelaRepository estrelaRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Override
	@PreAuthorize("hasAnyRole('USER, ADMIN')")
	@Transactional
	public Estrela adicionarEstrela(Long idUsuario, String nome, String ra, String dec, String tipo) {
		Optional<Usuario> usuario = usuarioRepo.findById(idUsuario);
		if (usuario == null) {
			throw new UsernameNotFoundException(
					"Usuário com identificador " + idUsuario + " não foi encontrado");
		}
		Estrela estrela = new Estrela();
		estrela.setNome(nome);
		estrela.setRa(ra);
		estrela.setDec(dec);
		estrela.setTipo(tipo);
		estrela.setDescobridor(usuario.get());
		estrela.setDataHora(new Date());
		estrelaRepo.save(estrela);
		return estrela;
	}
	
	@Override
	public List<Estrela> buscarTodas() {
		return estrelaRepo.findAll();
	}

	@Override
	public Estrela buscarPorId(Long id) {
		Optional<Estrela> estrela = estrelaRepo.findById(id);
		if(estrela.isPresent()) {
			return estrela.get();
		}
		throw new RegistroNaoEncontradoException("Estrela nao encontrada");
	}

	@Override
	public Estrela buscarPorNome(String nome) {
		return estrelaRepo.buscarEstrelaPorNome(nome);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Estrela editarEstrela(Long id, String nome, String ra, String dec, String tipo) {
		Optional<Estrela> estrela = estrelaRepo.findById(id);
		if(estrela.isPresent()) {
			Estrela estrelaE = estrela.get();
			estrelaE.setNome(nome);;
			estrelaE.setRa(ra);
			estrelaE.setDec(dec);
			estrelaE.setTipo(tipo);
			estrelaRepo.save(estrelaE);
			return estrelaE;
		}	
		throw new RegistroNaoEncontradoException("Estrela nao encontrada");
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Estrela deletarEstrela(Long id) {
		Optional<Estrela> estrela = estrelaRepo.findById(id);
		
		if(estrela.isPresent()) {
			estrelaRepo.deleteById(estrela.get().getId());
		}
		else {
			throw new RegistroNaoEncontradoException("Estrela nao encontrada");
		}
		return null;
	}
}
