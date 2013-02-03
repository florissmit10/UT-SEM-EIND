package sem.eind.net;

import sem.eind.client.prompt.*;

import sem.eind.model.KamerType.Bed;

public enum Command {
	CHECKIN("Inchecken van gasten in een kamer","checkin",new Prompt[]{
														new MultipleIntPrompt("Welke bedden moeten er in de kamer zijn?", Bed.values()),
														new BooleanPrompt("Roken?"),
														new StringPrompt("Geef de namen van de gasten: (gescheiden door komma's)")}),
	CHECKUIT("Uitchecken van gasten uit een kamer","checkUit",new Prompt[]{
														new IntegerPrompt("Welk kamernummer?")}),
	RESERVERING("Reserveren van een kamer","maakReservering",new Prompt[]{
														new MultipleIntPrompt("Welke datum zou u aan willen komen?",new String[]{"jaar","maand", "dag"}),
														new IntegerPrompt("Hoeveel nachten wilt u verblijven?"),
														new MultipleIntPrompt("Welke bedden moeten er in de kamer zijn?", Bed.values()),
														new BooleanPrompt("Roken?")}),
	ANNULEERRESERVERING("Annuleren van een reservering","annulleerReservering",new Prompt[]{
														new IntegerPrompt("Wat is uw reserveringsnummer?")			
	}),
	REKENING( "Uitprinten van een rekening","getRekening",new Prompt[]{
														new IntegerPrompt("Wat is uw reserveringsnummer?")
	});
	
	public static final char DELIM='\u0000';
	private Prompt<?>[] prompts;
	private String menuText;
	private String methodName;
	private Command(String text, String methodName, Prompt<?>[] requiredInput){
		prompts=requiredInput;
		this.methodName=methodName;
		menuText=text;
	}
	
	public String toString(){
		return menuText;
	}
	
	public Prompt<?>[] getPrompts(){
		return prompts;
	}
	
	public Class<?>[] getArgumentTypes(){
		Class<?>[] argTypes=new Class<?>[getPrompts().length];
		for(int i=0;i<getPrompts().length;i++){
			argTypes[i]=getPrompts()[i].getPromptType();
		}
		
		return argTypes;
	}
	
	public String getCallableMethodName(){
		
		return methodName;		
	}
}
