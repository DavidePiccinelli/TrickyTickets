package polimi.provafinale.trickytickets.exception;

//Eccezione specifica utilizzata per segnalare il mancato risultato di una ricerca


public class RecordNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String msg) {
		super(msg);

	}
}
