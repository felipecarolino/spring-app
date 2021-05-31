package br.gov.sp.fatec.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springapp.entity.Usuario;
import br.gov.sp.fatec.springapp.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@JsonView(View.UsuarioResumo.class)
	@GetMapping
	public List<Usuario> buscarTodos() {
		return usuarioService.buscarTodos();
	}
	
	@JsonView(View.UsuarioCompleto.class)
	@GetMapping(value = "/{id}")
	public Usuario buscarPorId(@PathVariable("id") Long id) {
		return usuarioService.buscarPorId(id);
	}
	
	@JsonView(View.UsuarioResumo.class)
	@GetMapping(value = "/nome")
	public Usuario buscarPorNome(@RequestParam(value = "nome") String nome) {
		return usuarioService.buscarPorNome(nome);
	}
	
	@JsonView(View.UsuarioCompleto.class)
	@PostMapping
	public ResponseEntity <Usuario> cadastrarNovoUsuario(@RequestBody Usuario usuario, UriComponentsBuilder uriComponentsBuilder) {
		
		usuario = usuarioService.novoUsuario(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), "ROLE_USER");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(
				uriComponentsBuilder.path(
						"/usuario/" + usuario.getId()).build().toUri());
		
		return new ResponseEntity<Usuario>(usuario, responseHeaders, HttpStatus.CREATED);
	}
	
	@JsonView(View.UsuarioCompleto.class)
	@PutMapping(value = "/{id}")
	public ResponseEntity <Usuario> editarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario, UriComponentsBuilder uriComponentsBuilder) {
		
		usuario = usuarioService.editarUsuario(id, usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getAutorizacoes());
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(
				uriComponentsBuilder.path(
						"/usuario/" + usuario.getId()).build().toUri());
		
		return new ResponseEntity<Usuario>(usuario, responseHeaders, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Usuario> deletarUsuario(@PathVariable Long id, UriComponentsBuilder uriComponentsBuilder) {
	    var removido = usuarioService.deletarUsuario(id);
	    
	    HttpHeaders responseHeaders = new HttpHeaders();
	    
	    if (removido != null) {
	    	return new ResponseEntity<Usuario>(responseHeaders, HttpStatus.NOT_FOUND);
	    }
		return new ResponseEntity<Usuario>(responseHeaders, HttpStatus.OK);
	  }
}
