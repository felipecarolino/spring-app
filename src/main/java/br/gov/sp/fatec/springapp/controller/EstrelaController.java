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

import br.gov.sp.fatec.springapp.entity.Estrela;
import br.gov.sp.fatec.springapp.service.EstrelaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/estrela")
public class EstrelaController {
	@Autowired
	private EstrelaService estrelaService;
	
	@JsonView(View.EstrelaResumo.class)
	@GetMapping
	public List<Estrela> buscarTodas() {
		return estrelaService.buscarTodas();
	}
	
	@JsonView(View.EstrelaResumo.class)
	@GetMapping(value = "/{id}")
	public Estrela buscarPorId(@PathVariable("id") Long id) {
		return estrelaService.buscarPorId(id);
	}
	
	@JsonView(View.EstrelaResumo.class)
	@GetMapping(value = "/nome")
	public Estrela buscarPorNome(@RequestParam(value = "nome") String nome) {
		return estrelaService.buscarPorNome(nome);
	}
	
	@JsonView(View.EstrelaResumo.class)
	@PostMapping
	public ResponseEntity <Estrela> cadastrarNovaEstrela(@RequestBody Estrela estrela, UriComponentsBuilder uriComponentsBuilder) {
		
		estrela = estrelaService.adicionarEstrela(estrela.getDescobridor().getId(), estrela.getNome(), estrela.getRa(), estrela.getDec(), estrela.getTipo());
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(
				uriComponentsBuilder.path(
						"/estrela/" + estrela.getId()).build().toUri());
		
		return new ResponseEntity<Estrela>(estrela, responseHeaders, HttpStatus.CREATED);
	}
	
	@JsonView(View.EstrelaResumo.class)
	@PutMapping(value = "/{id}")
	public ResponseEntity <Estrela> editarEstrela(@PathVariable("id") Long id, @RequestBody Estrela estrela, UriComponentsBuilder uriComponentsBuilder) {
		
		estrela = estrelaService.editarEstrela(id, estrela.getNome(), estrela.getRa(), estrela.getDec(), estrela.getTipo());
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(
				uriComponentsBuilder.path(
						"/estrela/" + estrela.getId()).build().toUri());
		
		return new ResponseEntity<Estrela>(estrela, responseHeaders, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Estrela> deletarEstrela(@PathVariable Long id, UriComponentsBuilder uriComponentsBuilder) {
	    var removido = estrelaService.deletarEstrela(id);
	    
	    HttpHeaders responseHeaders = new HttpHeaders();
	    
	    if (removido != null) {
	    	return new ResponseEntity<Estrela>(responseHeaders, HttpStatus.NOT_FOUND);
	    }
		return new ResponseEntity<Estrela>(responseHeaders, HttpStatus.OK);
	  }
}
