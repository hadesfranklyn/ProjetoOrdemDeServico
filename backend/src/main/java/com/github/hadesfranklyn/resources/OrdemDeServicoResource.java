package com.github.hadesfranklyn.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.hadesfranklyn.dtos.OrdemDeServicoDTO;
import com.github.hadesfranklyn.services.OrdemDeServicoService;

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
	
}
