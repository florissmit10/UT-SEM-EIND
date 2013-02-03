package sem.eind.net;

public class HotelException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ErrorCodes error;

	public HotelException(String arg0, ErrorCodes error) {
		super(arg0);
		this.error=error;

	}

	public ErrorCodes getError() {
		return error;
	}


}
