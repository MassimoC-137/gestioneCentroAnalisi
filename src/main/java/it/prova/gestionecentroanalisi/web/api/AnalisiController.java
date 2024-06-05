package it.prova.gestionecentroanalisi.web.api;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionecentroanalisi.dto.AnalisiDTO;
import it.prova.gestionecentroanalisi.model.Analisi;
import it.prova.gestionecentroanalisi.service.AnalisiService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/analisi")
public class AnalisiController {

	@Autowired
	private AnalisiService analisiservice;
	
	@GetMapping("/listAll")
	public List<AnalisiDTO> listAll() {
		return analisiservice.listAll().stream().map(analisi -> {
			return AnalisiDTO.buildAnalisiDTOFromModel(analisi);
		}).collect(Collectors.toList());

	}

	@GetMapping
	public List<AnalisiDTO> listMine(Principal principal) {
		return analisiservice.listMine(principal.getName()).stream().map(analisi -> {
			return AnalisiDTO.buildAnalisiDTOFromModel(analisi);
		}).collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AnalisiDTO inserisciNuovo(@Valid @RequestBody AnalisiDTO input, Principal principal) {
		Analisi analisi = input.buildAnalisiModel();
		analisi = analisiservice.inserisciNuova(analisi, principal.getName());
		return AnalisiDTO.buildAnalisiDTOFromModel(analisi);
	}
	
}
