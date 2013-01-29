package sem.eind.net;

public class HotelException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ErrorCodes error;
	private final String classname;

	public HotelException(String arg0, ErrorCodes error, String classname) {
		super(arg0);
		this.error=error;
		this.classname=classname;
	}

	public ErrorCodes getError() {
		return error;
	}

	public String getClassname() {
		return classname;
	}



}
