package it.prova.gestionecentroanalisi.service;

import java.util.List;

import it.prova.gestionecentroanalisi.model.MedicoPaziente;
import it.prova.gestionecentroanalisi.model.Utente;

public interface UtenteService {

	public List<Utente> listAllUtenti();

	public Utente caricaSingoloUtente(Long id);

	public Utente caricaSingoloUtenteConRuoli(Long id);

	public Utente aggiorna(Utente utenteInstance);

	public Utente inserisciNuovo(Utente utenteInstance);

	public void rimuovi(Long idToRemove);

	public Utente findByUsernameAndPassword(String username, String password);

	public Utente eseguiAccesso(String username, String password);

	public void changeUserAbilitation(Long utenteInstanceId);

	public Utente findByUsername(String username);
	
	public void assignRoleToUser(Long userId, String roleCode);

	public void removeRoleFromUser(Long userId, String roleCode);
	
	public Utente salvaUtente(Utente utente);
	
	public List<Utente> trovaPazientiPerMedico(Long medicoId);
	
	public List<Utente> trovaMediciPerPaziente(Long pazienteId);
	
	public MedicoPaziente aggiungiPazienteAMedico(Long medicoId, Long pazienteId);

}
