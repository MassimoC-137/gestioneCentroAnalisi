package it.prova.gestionecentroanalisi.web.api.exception;

public class AnalisiPazienteAlreadyValorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AnalisiPazienteAlreadyValorizedException(String msg) {
		super(msg);
	}
}
