package sem.eind.client.prompt;


public class BooleanPrompt extends Prompt{

	public BooleanPrompt(String promptMessage) {
		super(promptMessage);
	}

	@Override
	public boolean parseInput(String input) {
		return input.equalsIgnoreCase("ja")||input.equalsIgnoreCase("nee");
	}

	@Override
	public String getFormat() {
		return "(ja/nee)";
	}

}