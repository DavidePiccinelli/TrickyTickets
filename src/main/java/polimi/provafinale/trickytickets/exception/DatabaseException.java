package polimi.provafinale.trickytickets.exception;

//Eccezione specifica utilizzata per gli errori nella gestione del Database

public class DatabaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg) {
		super(msg);
	}
}
