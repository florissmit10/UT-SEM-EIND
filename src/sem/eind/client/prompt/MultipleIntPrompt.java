package sem.eind.client.prompt;

public class MultipleIntPrompt extends Prompt<Integer[]>{

	Object[] fieldNames;
	/**
	 * Maakt een prompt aan de hand van een Object array. Voor elk object word de toString aangeroepen en worden allen weergegeven aan de gebruiker.
	 * @param promptMessage
	 * @param fields De velden die worden weergegeven aan de gebruiker. Om functioneel te zijn dient de toString() iets relevants voor de gebruiker weer te geven.
	 */
	public MultipleIntPrompt(String promptMessage, Object[] fields) {
		super(promptMessage);
		this.fieldNames=fields;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInputValid(String input) {
		int[] inputNumber=new int[fieldNames.length];
		int i=0;
		String[] inp=input.split(" ");
		if(inp.length!=fieldNames.length)
			return false;
		for(String s:inp){
			try{
				inputNumber[i]=Integer.parseInt(s);
			}catch(NumberFormatException e){
				return false;
			}
		}
		return true;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFormat() {
		String format="";
		for(Object s:fieldNames){
			format=format+" "+s.toString();
		}
		return "("+format+")";
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer[] parseObjectFromString(String string) throws NumberFormatException{
		String[] stringArray=string.split(" ");
		Integer[] returnable=new Integer[stringArray.length];
		for(int i=0;i<stringArray.length;i++){
			returnable[i]=Integer.parseInt(stringArray[i]);			
		}
		return returnable;
	}
}