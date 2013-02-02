package sem.eind.client.prompt;

public class DatePrompt extends Prompt{

	public DatePrompt(String promptMessage) {
		super(promptMessage);
	}

	@Override
	public boolean parseInput(String input) {
		int[] inputNumber=new int[3];
		int i=0;
		for(String s:input.split(" ")){
			try{
				inputNumber[i]=Integer.parseInt(s);
			}catch(NumberFormatException e){
				return false;
			}
		}
		return false;
	}

	@Override
	public String getFormat() {
		return "(jaar maand dag)";
	}
}