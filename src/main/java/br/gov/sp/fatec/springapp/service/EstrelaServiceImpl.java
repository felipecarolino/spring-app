package br.gov.sp.fatec.springapp.service;



import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.springapp.entity.Estrela;
import br.gov.sp.fatec.springapp.entity.Usuario;
import br.gov.sp.fatec.springapp.repository.EstrelaRepository;
import br.gov.sp.fatec.springapp.repository.UsuarioRepository;

@Service("estrelaService")
public class EstrelaServiceImpl implements EstrelaService {
	@Autowired
	private EstrelaRepository estrelaRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Override
	@Transactional
	public Estrela adicionarEstrela(String identificadorUsuario, String nome, String ra, String dec, String tipo) {
		Usuario usuario = usuarioRepo.findTop1ByNomeOrEmail(identificadorUsuario, identificadorUsuario);
		if (usuario == null) {
			throw new UsernameNotFoundException(
					"Usuário com identificador " + identificadorUsuario + " não foi encontrado");
		}
		Estrela estrela = new Estrela();
		estrela.setNome(nome);
		estrela.setRa(ra);
		estrela.setDec(dec);
		estrela.setTipo(tipo);
		estrela.setDescobridor(usuario);
		estrela.setDataHora(new Date());
		estrelaRepo.save(estrela);
		return estrela;
	}
}
