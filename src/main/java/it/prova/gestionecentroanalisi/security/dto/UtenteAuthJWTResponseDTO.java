package it.prova.gestionecentroanalisi.security.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtenteAuthJWTResponseDTO {
	private String token;
	private String type = "Bearer";
	private String username;
	private String email;
	private List<String> roles;

	public UtenteAuthJWTResponseDTO(String token, String username, String email, List<String> roles) {
		this.token = token;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

}
