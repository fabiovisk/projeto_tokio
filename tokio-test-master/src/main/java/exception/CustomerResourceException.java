package exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CustomerResourceException extends RuntimeException {

	private static final long serialVersionUID = -8672783219395926999L;

	public CustomerResourceException(String message) {
		super(message);
	}


}
