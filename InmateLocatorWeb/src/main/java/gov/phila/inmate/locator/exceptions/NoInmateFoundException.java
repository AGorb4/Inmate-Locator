package gov.phila.inmate.locator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoInmateFoundException extends Exception {

	private static final long serialVersionUID = -8106769939738482076L;
	
    public NoInmateFoundException(String message) {
        super(message);
    }
}
