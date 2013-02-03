package sem.eind.client.prompt;


public class StringPrompt extends Prompt<String>{

	public StringPrompt(String promptMessage) {
		super(promptMessage);
	}

	@Override
	public boolean isInputValid(String input) {
		return input.length()>0;
	}

	@Override
	public String getFormat() {
		return "";
	}


	@Override
	public String parseObjectFromString(String string) {
		return string;
	}
}