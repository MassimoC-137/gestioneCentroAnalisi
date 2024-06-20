package it.prova.gestionecentroanalisi.repository;

import org.springframework.data.repository.CrudRepository;

import it.prova.gestionecentroanalisi.model.Ruolo;

public interface RuoloRepository  extends CrudRepository<Ruolo, Long>{

	Ruolo findByDescrizione(String descrizione);

	Ruolo findByCodice(String codice);
}
