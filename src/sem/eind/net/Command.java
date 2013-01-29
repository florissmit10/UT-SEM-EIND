package sem.eind.net;

public enum Command {
	EXIT(0);
	
	
	private int arguments;
	private Command(int args){
		arguments=args;
	}
	

}
