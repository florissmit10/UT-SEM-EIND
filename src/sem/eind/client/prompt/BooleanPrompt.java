package sem.eind.client.prompt;


public class BooleanPrompt extends Prompt<Boolean>{
	/**
	 * {@inheritDoc}
	 */
	public BooleanPrompt(String promptMessage) {
		super(promptMessage);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInputValid(String input) {
		return input.equalsIgnoreCase("ja")||input.equalsIgnoreCase("nee");
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFormat() {
		return "(ja/nee)";
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean parseObjectFromString(String string) {
		if(string.equalsIgnoreCase("ja")){
			return true;
		}else if(string.equalsIgnoreCase("nee")){
			return false;
		}
		return null;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<Boolean> getPromptType() {
		return Boolean.class;
	}

}