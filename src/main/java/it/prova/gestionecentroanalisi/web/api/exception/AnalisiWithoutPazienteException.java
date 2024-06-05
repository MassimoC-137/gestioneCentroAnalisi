package it.prova.gestionecentroanalisi.web.api.exception;

public class AnalisiWithoutPazienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AnalisiWithoutPazienteException(String msg) {
		super(msg);
	}

}