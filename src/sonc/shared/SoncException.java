package sonc.shared;

/**
 * @author Ricardo Giovani
 *
 */
public class SoncException extends Exception {
	private static final long serialVersionUID = 1L;

	public SoncException() {
		super("It has occurred an error on system.");
	}

	public SoncException(String message) {
		super(message);
	}

	public SoncException(String message, Throwable cause) {
		super(message, cause);
	}

	public SoncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SoncException(Throwable cause) {
		super(cause);
	}
}