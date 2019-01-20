package app.ws.exceptions;

public class UserServiceException extends RuntimeException{
	private static final long serialVersionUID = 770780011217075876L;

	public UserServiceException(String message) {
		super(message);
	}
}
