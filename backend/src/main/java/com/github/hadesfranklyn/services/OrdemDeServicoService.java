package com.github.hadesfranklyn.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hadesfranklyn.domain.Cliente;
import com.github.hadesfranklyn.domain.OrdemDeServico;
import com.github.hadesfranklyn.domain.Tecnico;
import com.github.hadesfranklyn.domain.enums.Prioridade;
import com.github.hadesfranklyn.domain.enums.Status;
import com.github.hadesfranklyn.dtos.OrdemDeServicoDTO;
import com.github.hadesfranklyn.repositories.OrdemDeServicoRepository;
import com.github.hadesfranklyn.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemDeServicoService {

	@Autowired
	private OrdemDeServicoRepository ordemDeServicoRepository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	public OrdemDeServico findById(Integer id) {
		Optional<OrdemDeServico> obj = ordemDeServicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + OrdemDeServico.class.getName()));
	}

	public List<OrdemDeServico> findAll() {
		return ordemDeServicoRepository.findAll();
	}

	public OrdemDeServico create(@Valid OrdemDeServicoDTO objDTO) {
		return fromDTO(objDTO);
	}

	public OrdemDeServico update(@Valid OrdemDeServicoDTO objDTO) {
		findById(objDTO.getId());
		return fromDTO(objDTO);
	}

	private OrdemDeServico fromDTO(OrdemDeServicoDTO objDTO) {
		OrdemDeServico newObj = new OrdemDeServico();
		newObj.setId(objDTO.getId());
		newObj.setObservacoes(objDTO.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
		newObj.setStatus(Status.toEnum(objDTO.getStatus()));

		Tecnico tec = tecnicoService.findById(objDTO.getTecnico());
		Cliente cli = clienteService.findById(objDTO.getCliente());

		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		if (newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return ordemDeServicoRepository.save(newObj);
	}

}
