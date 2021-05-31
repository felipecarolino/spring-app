package br.gov.sp.fatec.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RegistroDuplicadoException extends RuntimeException {
	
	private static final long serialVersionUID = 7268872019822058884L;

	public RegistroDuplicadoException () {
		super();
	}
	
	public RegistroDuplicadoException (String message) {
		super(message);
	}
	
	public RegistroDuplicadoException (Throwable cause) {
		super(cause);
	}
	
	public RegistroDuplicadoException (String message, Throwable cause) {
		super(message, cause);
	}
}
