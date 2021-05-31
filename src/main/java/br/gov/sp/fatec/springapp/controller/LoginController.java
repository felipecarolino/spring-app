package br.gov.sp.fatec.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.sp.fatec.springapp.security.JwtUtils;
import br.gov.sp.fatec.springapp.security.Login;
import br.gov.sp.fatec.springapp.service.UsuarioService;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin
public class LoginController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping()
	public Login autenticar(@RequestBody Login login) throws JsonProcessingException {
				
		Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
		auth = authManager.authenticate(auth);
		login.setPassword(null);
		login.setAutorizacao(usuarioService.buscarAut(login.getUsername()));
		login.setToken(JwtUtils.generateToken(auth));
		login.setId(usuarioService.buscarId(login.getUsername()));
		return login;
	}

}
