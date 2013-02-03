package sem.eind.client.prompt;


public class BooleanPrompt extends Prompt<Boolean>{

	public BooleanPrompt(String promptMessage) {
		super(promptMessage);
	}

	@Override
	public boolean isInputValid(String input) {
		return input.equalsIgnoreCase("ja")||input.equalsIgnoreCase("nee");
	}

	@Override
	public String getFormat() {
		return "(ja/nee)";
	}

	@Override
	public Boolean parseObjectFromString(String string) {
		return Boolean.parseBoolean(string);
	}

	@Override
	public Class<Boolean> getPromptType() {
		return Boolean.class;
	}

}