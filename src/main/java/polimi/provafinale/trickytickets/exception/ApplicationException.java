package polimi.provafinale.trickytickets.exception;

// Eccezione specifica utilizzata per gli errori dell'applicazione

public class ApplicationException extends Exception {
	private static final long serialVersionUID = 1L;

	public ApplicationException(String msg) {
		super(msg);
	}
}
