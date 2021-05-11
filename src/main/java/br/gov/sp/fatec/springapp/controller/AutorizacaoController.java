package br.gov.sp.fatec.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springapp.entity.Autorizacao;
import br.gov.sp.fatec.springapp.service.AutorizacaoService;

@RestController
@CrossOrigin
@RequestMapping(value = "/autorizacao")
public class AutorizacaoController {
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	@JsonView(View.AutorizacaoResumo.class)
	@GetMapping(value = "/{autorizacao}")
	public Autorizacao buscarAutorizacaoPorNome(@PathVariable("autorizacao") String nome) {
		return autorizacaoService.buscarAutorizacaoPorNome(nome);
	}

}
