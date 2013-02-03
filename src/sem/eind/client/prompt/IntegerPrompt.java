package sem.eind.client.prompt;

public class IntegerPrompt extends Prompt<Integer>{

	public IntegerPrompt(String promptMessage) {
		super(promptMessage);
	}

	@Override
	public boolean isInputValid(String input) {
		try {
			int inp=Integer.parseInt(input);
			return inp>=0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public String getFormat() {
		return "(geheel, positief getal)";
	}

	@Override
	public Integer parseObjectFromString(String string) throws NumberFormatException {

		return Integer.parseInt(string);
	}

	@Override
	public Class<Integer> getPromptType() {
		return Integer.class;
	}
}