package it.prova.gestionecentroanalisi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionecentroanalisi.model.Analisi;
import it.prova.gestionecentroanalisi.model.Utente;
import it.prova.gestionecentroanalisi.repository.AnalisiRepository;
import it.prova.gestionecentroanalisi.repository.UtenteRepository;
import it.prova.gestionecentroanalisi.web.api.exception.AnalisiPazienteAlreadyValorizedException;
import it.prova.gestionecentroanalisi.web.api.exception.AnalisiWithoutPazienteException;
import it.prova.gestionecentroanalisi.web.api.exception.ElementNotFoundException;
import it.prova.gestionecentroanalisi.web.api.exception.IdNotNullForInsertException;
import it.prova.gestionecentroanalisi.web.api.exception.NotSamePazienteException;

@Service
@Transactional
public class AnalisiServiceImpl implements AnalisiService {

	@Autowired
	private AnalisiRepository analisiRepository;

	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public List<Analisi> listAll() {
		return (List<Analisi>) analisiRepository.findAll();
	}

	@Override
	public List<Analisi> listMine(String username) {
		return analisiRepository.listAllAnalisiOfUtente(username);
	}

	@Override
	public Analisi findById(Long id, String username) {
		Optional<Utente> pazienteOptional = utenteRepository.findByUsername(username);
		Utente paziente = pazienteOptional.get();
		Analisi analisi = analisiRepository.findByIdEager(id);
		if (analisi.getPaziente().isAdmin())
			return analisi;
		else if (analisi.getPaziente().getId() == paziente.getId())
			return analisi;
		else
			throw new NotSamePazienteException("The current Paziente and the Analisi's paziente are not the same");

	}

	@Override
	public Analisi inserisciNuova(Analisi input, String username) {
		Optional<Utente> pazienteOptional = utenteRepository.findByUsername(username);
		Utente paziente = pazienteOptional.get();
		if (paziente == null)
			throw new ElementNotFoundException("Paziente not Found");
		if (input.getId() != null)
			throw new IdNotNullForInsertException("Id must be null for this operation");
		if (input.getPaziente() != null)
			throw new AnalisiPazienteAlreadyValorizedException("The paziente field must be null");
		input.setPaziente(paziente);
		return analisiRepository.save(input);

	}

	@Override
	public Analisi aggiorna(Analisi input, String username) {
		if (input.getPaziente() == null)
			throw new AnalisiWithoutPazienteException("This Analisi doesn't have a paziente associated to it.");
		if (!input.getPaziente().getUsername().equals(username))
			throw new NotSamePazienteException("The current Paziente and the Analisi's paziente are not the same");
		input.setPaziente(utenteRepository.findByUsername(username).orElse(null));
		return analisiRepository.save(input);
	}

	@Override
	public void delete(Long id, String username) {
		Analisi analisi = analisiRepository.findByIdEager(id);
		if (analisi.getPaziente().getUsername().equals(username))
			analisiRepository.deleteById(id);
		else
			throw new NotSamePazienteException("The current Paziente and the Analisi's paziente are not the same");

	}

}