package it.prova.gestionecentroanalisi.security.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtenteInfoJWTResponseDTO {

	private String nome;
	private String cognome;
	private String type = "Bearer";
	private String username;
	private String email;
	private List<String> roles;

	public UtenteInfoJWTResponseDTO(String nome, String cognome, String username, String email, List<String> roles) {
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

}
