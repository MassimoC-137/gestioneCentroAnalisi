package it.prova.gestionecentroanalisi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.prova.gestionecentroanalisi.model.MedicoPaziente;
import it.prova.gestionecentroanalisi.model.Utente;

public interface MedicoPazienteRepository extends JpaRepository<MedicoPaziente, Long> {

    @Query("SELECT mp.paziente FROM MedicoPaziente mp WHERE mp.medico.id = :medicoId")
    List<Utente> findPazientiByMedicoId(@Param("medicoId") Long medicoId);

    @Query("SELECT mp.medico FROM MedicoPaziente mp WHERE mp.paziente.id = :pazienteId")
    List<Utente> findMediciByPazienteId(@Param("pazienteId") Long pazienteId);
}

