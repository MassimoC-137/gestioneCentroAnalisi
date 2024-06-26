package it.prova.gestionecentroanalisi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionecentroanalisi.model.MedicoPaziente;
import it.prova.gestionecentroanalisi.model.Ruolo;
import it.prova.gestionecentroanalisi.model.Utente;
import it.prova.gestionecentroanalisi.repository.MedicoPazienteRepository;
import it.prova.gestionecentroanalisi.repository.RuoloRepository;
import it.prova.gestionecentroanalisi.repository.UtenteRepository;
import it.prova.gestionecentroanalisi.web.api.exception.ElementNotFoundException;
import it.prova.gestionecentroanalisi.web.api.exception.IdNotNullForInsertException;

@Service
@Transactional
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RuoloRepository ruoloRepository;
	
	@Autowired
    private MedicoPazienteRepository medicoPazienteRepository;

	@Override
	public List<Utente> listAllUtenti() {
		return (List<Utente>) utenteRepository.findAll();
	}

	@Override
	public Utente caricaSingoloUtente(Long id) {
		return utenteRepository.findById(id).orElse(null);
	}

	@Override
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return utenteRepository.fingByIdConRuoli(id).orElse(null);
	}

	@Override
	public Utente aggiorna(Utente utenteInstance) {
	    Utente utenteReloaded = utenteRepository.findById(utenteInstance.getId()).orElse(null);
	    if (utenteReloaded == null)
	        throw new RuntimeException("Utente non trovato");
	    utenteReloaded.setNome(utenteInstance.getNome());
	    utenteReloaded.setCognome(utenteInstance.getCognome());
	    utenteReloaded.setCodiceFiscale(utenteInstance.getCodiceFiscale());
	    utenteReloaded.setEmail(utenteInstance.getEmail());
	    utenteReloaded.setRuoli(utenteInstance.getRuoli());
	    return utenteRepository.save(utenteReloaded);
	}

	@Override
	public Utente inserisciNuovo(Utente utenteInstance) {
	    if (utenteInstance.getId() != null) {
	        throw new IdNotNullForInsertException("Id must be null for insert operation");
	    }
	    Set<Ruolo> ruoli = new HashSet<Ruolo>();
	    if(!utenteInstance.getUsername().equals("admin")) {	    	
	    	ruoli.add(ruoloRepository.findByDescrizione("Classic User"));
	    	utenteInstance.setRuoli(ruoli);
	    }
	    utenteInstance.setAttivo(true);
	    utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
	    return utenteRepository.save(utenteInstance);
	}


	@Override
	public void rimuovi(Long idToRemove) {
		utenteRepository.deleteById(idToRemove);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return utenteRepository.findByUsernameAndPasswordAndAttivo(username, password, true);
	}

	@Override
	public Utente eseguiAccesso(String username, String password) {
		Optional<Utente> optionalUser = utenteRepository.findByUsername(username);
		Utente user = optionalUser.get();
		if (user != null && passwordEncoder.matches(password, user.getPassword()))
			return user;
		return null;
	}		

	@Override
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Utente non trovato");
		utenteInstance.setAttivo(false);
	}

	@Override
	public Utente findByUsername(String username) {
		return utenteRepository.findByUsername(username).orElse(null);
	}
	
	@Override
    public void assignRoleToUser(Long userId, String roleCode) {
        Utente user = utenteRepository.findById(userId).orElseThrow(() -> new ElementNotFoundException("Utente non trovato"));
        Ruolo role = ruoloRepository.findByCodice(roleCode);
        user.addRole(role);
        utenteRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(Long userId, String roleCode) {
        Utente user = utenteRepository.findById(userId).orElseThrow(() -> new ElementNotFoundException("Utente non trovato"));
        Ruolo role = ruoloRepository.findByCodice(roleCode);
        user.removeRole(role);
        utenteRepository.save(user);
    }

    @Override
    public Utente salvaUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    public MedicoPaziente aggiungiPazienteAMedico(Long medicoId, Long pazienteId) {
        Utente medico = utenteRepository.findById(medicoId).orElseThrow(() -> new RuntimeException("Medico non trovato"));
        Utente paziente = utenteRepository.findById(pazienteId).orElseThrow(() -> new RuntimeException("Paziente non trovato"));

        MedicoPaziente medicoPaziente = new MedicoPaziente();
        medicoPaziente.setMedico(medico);
        medicoPaziente.setPaziente(paziente);

        return medicoPazienteRepository.save(medicoPaziente);
    }

    public List<Utente> trovaPazientiPerMedico(Long medicoId) {
        return medicoPazienteRepository.findPazientiByMedicoId(medicoId);
    }

    public List<Utente> trovaMediciPerPaziente(Long pazienteId) {
        return medicoPazienteRepository.findMediciByPazienteId(pazienteId);
    }
    
}
