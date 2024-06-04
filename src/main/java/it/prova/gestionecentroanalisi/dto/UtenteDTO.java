package it.prova.gestionecentroanalisi.dto;

import java.util.Arrays;
import java.util.stream.Collectors;

import it.prova.gestionecentroanalisi.model.Ruolo;
import it.prova.gestionecentroanalisi.model.StatoUtente;
import it.prova.gestionecentroanalisi.model.Utente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtenteDTO {

	private Long id;

	@NotBlank(message = "{username.notblank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	@NotBlank(message = "{password.notblank}")
	@Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String password;

	private String confermaPassword;
	
	@NotBlank(message = "{email.notblank}")
	private String email;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotBlank(message = "{codice_fiscale.notblank}")
	private String codiceFiscale;
	
	@NotNull(message = "{attivo.notblank}")
	private Boolean attivo;

	@NotNull(message = "{stato.notblank}")
	private StatoUtente stato;
	
	private Long[] ruoliIds; 
	
	public UtenteDTO(Long id, String username, String email, String nome, String cognome, String codiceFiscale, Boolean attivo) {
		super(); 
		this.id = id; 
		this.username = username; 
		this.email = email;
		this.nome = nome; 
		this.cognome = cognome; 
		this.codiceFiscale = codiceFiscale;
		this.attivo = attivo; 
	}
	
	public Utente buildUtenteModel(Boolean includeIdRoles) {
		Utente res = new Utente(this.id, this.username, this.email, this.password, this.nome, this.cognome, this.codiceFiscale, this.attivo);
				if (includeIdRoles && ruoliIds != null)
					res.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet()));
				return res;
	}
	
	public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel) {
		UtenteDTO result = new UtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getEmail(), utenteModel.getNome(),
				utenteModel.getCognome(), utenteModel.getCodiceFiscale(), utenteModel.getAttivo()); 
		if (!utenteModel.getRuoli().isEmpty())
			result.ruoliIds = utenteModel.getRuoli().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});
		return result;
	}
	
	
	
	
	
	
}
