package sem.eind.net;

public enum Command {
	CHECKIN(0),
	CHECKUIT(0),
	RESERVERING(0),
	ANNULEERRESERVERING(0),
	REKENING(0);
		
	private int arguments;
	private Command(int args){
		arguments=args;
	}
	
	public boolean enoughArguments(int arguments){
		return this.arguments==arguments;
	}
}
