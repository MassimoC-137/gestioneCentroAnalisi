package it.prova.gestionecentroanalisi.web.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionecentroanalisi.dto.UtenteDTO;
import it.prova.gestionecentroanalisi.model.Utente;
import it.prova.gestionecentroanalisi.security.dto.UtenteInfoJWTResponseDTO;
import it.prova.gestionecentroanalisi.service.UtenteService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/utente")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	// testare chi ha accesso
	@GetMapping(value = "/userInfo")
	public ResponseEntity<UtenteInfoJWTResponseDTO> getUserInfo() {
		// se sono qui significa che sono autenticato quindi devo estrarre le info dal
		// contesto
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		List<String> ruoli = utenteLoggato.getRuoli().stream().map(item -> item.getCodice())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new UtenteInfoJWTResponseDTO(utenteLoggato.getNome(), utenteLoggato.getCognome(),
				utenteLoggato.getUsername(), utenteLoggato.getEmail(), ruoli));
	}

}
