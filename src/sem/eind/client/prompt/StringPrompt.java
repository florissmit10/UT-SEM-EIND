package sem.eind.client.prompt;


public class StringPrompt extends Prompt{

	public StringPrompt(String promptMessage) {
		super(promptMessage);
	}

	@Override
	public boolean parseInput(String input) {
		return input.length()>0;
	}

	@Override
	public String getFormat() {
		return "";
	}
}