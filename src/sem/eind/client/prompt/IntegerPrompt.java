package sem.eind.client.prompt;

public class IntegerPrompt extends Prompt<Integer>{
	
	/**
	 * {@inheritDoc}
	 */
	public IntegerPrompt(String promptMessage) {
		super(promptMessage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInputValid(String input) {
		try {
			int inp=Integer.parseInt(input);
			return inp>=0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFormat() {
		return "(geheel, positief getal)";
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer parseObjectFromString(String string) throws NumberFormatException {

		return Integer.parseInt(string);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<Integer> getPromptType() {
		return Integer.class;
	}
}