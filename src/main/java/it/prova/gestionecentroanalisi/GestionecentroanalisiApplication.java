package it.prova.gestionecentroanalisi;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionecentroanalisi.model.Ruolo;
import it.prova.gestionecentroanalisi.model.Utente;
import it.prova.gestionecentroanalisi.service.RuoloService;
import it.prova.gestionecentroanalisi.service.UtenteService;

@SpringBootApplication
public class GestionecentroanalisiApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(GestionecentroanalisiApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizione("Administrator") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizione("Classic Doctor") == null) {
            ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic Doctor", Ruolo.ROLE_CLASSIC_DOCTOR));
        }
		
		if (ruoloServiceInstance.cercaPerDescrizione("Classic User") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", Ruolo.ROLE_CLASSIC_PATIENT));
		}
		
		if (utenteServiceInstance.findByUsername("admin") == null) {
            Utente admin = new Utente();
            admin.setUsername("admin");
            admin.setPassword("admin"); 
            admin.setEmail("admin@admin.com");
            admin.setNome("Admin");
            admin.setCognome("Admin");
            admin.setCodiceFiscale("ADMIN001");
            admin.setAttivo(true);
            
            Set<Ruolo> ruoli = new HashSet<>();
            ruoli.add(ruoloServiceInstance.cercaPerDescrizione("Administrator"));
            admin.setRuoli(ruoli);

            utenteServiceInstance.inserisciNuovo(admin);
            
        }
	}
}
