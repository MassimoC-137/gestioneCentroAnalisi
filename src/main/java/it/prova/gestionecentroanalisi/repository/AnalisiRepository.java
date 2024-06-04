package it.prova.gestionecentroanalisi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionecentroanalisi.model.Analisi;

public interface AnalisiRepository  extends CrudRepository<Analisi, Long>{

	@Query(" select a from Analisi a left join fetch a.paziente u where u.username=:username")
	public List<Analisi> listAllAnalisiOfUtente(String username);

	@Query(" select a from Analisi a left join fetch a.paziente u where a.id=:id")
	public Analisi findByIdEager(Long id);

}
