package sem.eind.client.prompt;

public abstract class Prompt{
	
	private String promptMessage;
	public Prompt(String promptMessage){
		this.promptMessage=promptMessage;
	}
	
	public abstract boolean parseInput(String input);
	
	public abstract String getFormat();
	
	public String getPrompt(){
		return promptMessage+ getFormat();
	}
}