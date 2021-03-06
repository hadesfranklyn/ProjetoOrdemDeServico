package com.github.hadesfranklyn.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.hadesfranklyn.dtos.OrdemDeServicoDTO;
import com.github.hadesfranklyn.services.OrdemDeServicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/ordemDeServicos")
public class OrdemDeServicoResource {

	@Autowired
	private OrdemDeServicoService ordemDeServicoService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<OrdemDeServicoDTO> findById(@PathVariable Integer id) {
		OrdemDeServicoDTO objDTO = new OrdemDeServicoDTO(ordemDeServicoService.findById(id));
		return ResponseEntity.ok().body(objDTO);
	}

	@GetMapping
	public ResponseEntity<List<OrdemDeServicoDTO>> findAll() {
		List<OrdemDeServicoDTO> listDTO = ordemDeServicoService.findAll().stream()
				.map(obj -> new OrdemDeServicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<OrdemDeServicoDTO> create(@Valid @RequestBody OrdemDeServicoDTO obj) {
		obj = new OrdemDeServicoDTO(ordemDeServicoService.create(obj));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping
	public ResponseEntity<OrdemDeServicoDTO> update(@Valid @RequestBody OrdemDeServicoDTO objDTO) {
		objDTO = new OrdemDeServicoDTO(ordemDeServicoService.update(objDTO));
		return ResponseEntity.ok().body(objDTO);
	}
}
