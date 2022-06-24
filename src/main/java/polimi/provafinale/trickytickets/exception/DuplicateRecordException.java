package polimi.provafinale.trickytickets.exception;


//Eccezione specifica utilizzata per segnalare dati già esistenti


public class DuplicateRecordException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateRecordException(String msg) {
		super(msg);
	}
}
