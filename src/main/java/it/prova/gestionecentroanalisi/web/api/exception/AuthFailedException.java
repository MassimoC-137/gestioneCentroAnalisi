package it.prova.gestionecentroanalisi.web.api.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthFailedException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public AuthFailedException(String msg) {
		super(msg);
	}

}
