package br.gov.sp.fatec.springapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.springapp.entity.Autorizacao;
import br.gov.sp.fatec.springapp.entity.Estrela;
import br.gov.sp.fatec.springapp.entity.Usuario;
import br.gov.sp.fatec.springapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springapp.repository.EstrelaRepository;
import br.gov.sp.fatec.springapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springapp.service.UsuarioService;
import br.gov.sp.fatec.springapp.service.EstrelaService;

@SpringBootTest
@Transactional
@Rollback
class SpringAppApplicationTests {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private AutorizacaoRepository autRepo;
	
	@Autowired
	private EstrelaRepository estRepo;
	
	 @Autowired
	 private UsuarioService usuarioService;
	 
	 @Autowired
	 private EstrelaService estrelaService;

	@Test
	void contextLoads() {
	}

	@Test
	void testaInsercaoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome("User");
		usuario.setEmail("user@mail.com");
		usuario.setSenha("SenhaForte");
		usuario.setAutorizacoes(new HashSet<Autorizacao>());
		Autorizacao aut = new Autorizacao();
		aut.setNome("ROLE_USER1");
		autRepo.save(aut);
		usuario.getAutorizacoes().add(aut);
		usuarioRepo.save(usuario);
		assertNotNull(usuario.getAutorizacoes().iterator().next().getId());
	}

	@Test
	void testaInsercaoAutorizacao() {
		Usuario usuario = new Usuario();
		usuario.setNome("Usuario");
		usuario.setEmail("usuario@mail.com");
		usuario.setSenha("senha12345");
		usuarioRepo.save(usuario);
		Autorizacao aut = new Autorizacao();
		aut.setNome("ROLE_USER2");
		aut.setUsuarios(new HashSet<Usuario>());
		aut.getUsuarios().add(usuario);
		autRepo.save(aut);
		assertNotNull(aut.getUsuarios().iterator().next().getId());
	}

	@Test
	void testaAutorizacao() {
		Usuario usuario = usuarioRepo.findById(20L).get();
		assertEquals("ROLE_ADMIN", usuario.getAutorizacoes().iterator().next().getNome());
	}
	
	@Test
	void testaUsuario() {
		Autorizacao aut = autRepo.findById(15L).get();
		assertEquals("felipe", aut.getUsuarios().iterator().next().getNome());
	}
	
	@Test
	void testaInsercaoEstrela() {
		Usuario usuario = new Usuario();
		usuario.setNome("User");
		usuario.setEmail("user@mail.com");
		usuario.setSenha("SenhaForte");
		usuario.setAutorizacoes(new HashSet<Autorizacao>());
		Autorizacao aut = new Autorizacao();
		aut.setNome("ROLE_USER1");
		autRepo.save(aut);
		usuario.getAutorizacoes().add(aut);
		usuarioRepo.save(usuario);
		assertNotNull(usuario.getAutorizacoes().iterator().next().getId());
		Estrela estrela = new Estrela();
		estrela.setNome("V* AM Her");
		estrela.setDec("+49 52 04.75");
		estrela.setRa("18 16 13.25");
		estrela.setTipo("CV");
		Date date = new Date();
		estrela.setDataHora(date);
		estrela.setDescobridor(usuario);
		estRepo.save(estrela);
	}
	
	@Test
    void testaBuscaUsuarioNomeContains(){
        List<Usuario> usuarios = usuarioRepo.findByNomeContainsIgnoreCase("f");
        assertFalse(usuarios.isEmpty());       
    }

    @Test
    void testaBuscaUsuarioNome(){
        Usuario usuario = usuarioRepo.findByNome("felipe");
        assertNotNull(usuario);       
    }

    @Test
    void testaBuscaUsuarioNomeSenha(){
        Usuario usuario = usuarioRepo.findByNomeAndSenha("felipe", "pass");
        assertNotNull(usuario);       
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacao(){
        List<Usuario> usuarios = usuarioRepo.findByAutorizacoesNome("ROLE_ADMIN");
        assertFalse(usuarios.isEmpty());       
    }

    @Test
    void testaBuscaUsuarioNomeQuery(){
        Usuario usuario = usuarioRepo.buscaUsuarioPorNome("felipe");
        assertNotNull(usuario);       
    }

    @Test
    void testaBuscaUsuarioNomeSenhaQuery(){
        Usuario usuario = usuarioRepo.buscaUsuarioPorNomeESenha("felipe", "pass");
        assertNotNull(usuario);       
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacaoQuery(){
        List<Usuario> usuarios = usuarioRepo.buscaPorNomeAutorizacao("ROLE_ADMIN");
        assertFalse(usuarios.isEmpty());       
    }

    @Test
    void testaServicoCriaUsuario(){
        Usuario usuario = usuarioService.novoUsuario("user test", "test@test.com", "senha", "ROLE_ADMIN");
        assertNotNull(usuario);
    }

    //Transação cria uma nova estrela e adiciona a estrela criada a um usuario. 
    @Test
    void testaServicoCriaEstrela(){
        Estrela estrela = estrelaService.adicionarEstrela("felipe", "ASASSN -13ck", "00 11 33.70", "+04 52 04.75", "CV");
        assertNotNull(estrela);
    }

    // Consulta com 2 parâmetros -> EstrelaRepository
    @Test
    void buscaEstrelaPorNomeEIdQuery(){
        Estrela estrela = estRepo.buscaEstrelaPorNomeEId("AM Her", 3);
        assertNotNull(estrela);    
    }

    // Consulta com Join Usuario e Estrela -> UsuarioRepository
    @Test
    void testaBuscaUsuarioNomeEstrelaQuery(){
        Usuario usuario = usuarioRepo.buscaPorNomeEstrela("AM Her");
        assertNotNull(usuario);         
    }

}
