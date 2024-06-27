package it.prova.gestionecentroanalisi.web.api;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionecentroanalisi.dto.UtenteDTO;
import it.prova.gestionecentroanalisi.model.MedicoPaziente;
import it.prova.gestionecentroanalisi.model.Ruolo;
import it.prova.gestionecentroanalisi.model.Utente;
import it.prova.gestionecentroanalisi.security.dto.UtenteInfoJWTResponseDTO;
import it.prova.gestionecentroanalisi.service.UtenteService;

@RestController
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
	
	@GetMapping("{id}")
	public UtenteDTO getById(@PathVariable Long id) {
		return UtenteDTO.buildUtenteDTOFromModel(utenteService.caricaSingoloUtente(id));
	}
	
	@GetMapping
    public List<Utente> getAllUsers() {
        return utenteService.listAllUtenti();
    }

    @PostMapping
    public Utente createUser(@RequestBody UtenteDTO utenteDTO) {
        return utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));
    }

    @PutMapping("/{id}")
    public Utente updateUser(@PathVariable Long id, @RequestBody UtenteDTO utenteDTO) {
        Utente utenteToUpdate = utenteService.caricaSingoloUtente(id);
        utenteToUpdate.setNome(utenteDTO.getNome());
        utenteToUpdate.setCognome(utenteDTO.getCognome());
        utenteToUpdate.setEmail(utenteDTO.getEmail());
        utenteToUpdate.setRuoli(new HashSet<>());
        if (utenteDTO.getRuoliIds() != null) {
            for (Long ruoloId : utenteDTO.getRuoliIds()) {
                utenteToUpdate.addRole(new Ruolo(ruoloId));
            }
        }
        return utenteService.aggiorna(utenteToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        utenteService.rimuovi(id);
    }

    @PostMapping("/{id}/promote")
    public void promoteUser(@PathVariable Long id, @RequestBody String roleCode) {
        utenteService.assignRoleToUser(id, roleCode);
    }

    @PostMapping("/{id}/demote")
    public void demoteUser(@PathVariable Long id, @RequestBody String roleCode) {
        utenteService.removeRoleFromUser(id, roleCode);
    }

    @PostMapping("/{medicoId}/addPaziente/{pazienteId}")
    public ResponseEntity<MedicoPaziente> aggiungiPazienteAMedico(@PathVariable Long medicoId, @PathVariable Long pazienteId) {
        MedicoPaziente collegamento = utenteService.aggiungiPazienteAMedico(medicoId, pazienteId);
        return new ResponseEntity<>(collegamento, HttpStatus.OK);
    }

    @GetMapping("/{medicoId}/pazienti")
    public ResponseEntity<List<Utente>> trovaPazientiPerMedico(@PathVariable Long medicoId) {
        List<Utente> pazienti = utenteService.trovaPazientiPerMedico(medicoId);
        return new ResponseEntity<>(pazienti, HttpStatus.OK);
    }

    @GetMapping("/{pazienteId}/medici")
    public ResponseEntity<List<Utente>> trovaMediciPerPaziente(@PathVariable Long pazienteId) {
        List<Utente> medici = utenteService.trovaMediciPerPaziente(pazienteId);
        return new ResponseEntity<>(medici, HttpStatus.OK);
    }

}
