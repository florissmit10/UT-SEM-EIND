package sem.eind.net;

import sem.eind.client.prompt.BooleanPrompt;
import sem.eind.client.prompt.DatePrompt;
import sem.eind.client.prompt.IntegerPrompt;
import sem.eind.client.prompt.Prompt;
import sem.eind.client.prompt.StringPrompt;

public enum Command {
	CHECKIN("Inchecken van gasten in een kamer",new Prompt[]{
														new IntegerPrompt("Hoeveel eenpersoonsbedden?"),
														new IntegerPrompt("Hoeveel kinderbedden?"), 
														new IntegerPrompt("Hoeveel tweepersoonsbedden?"), 
														new BooleanPrompt("Roken?"),
														new StringPrompt("Geef de namen van de gasten: (gescheiden door komma's)")}),
	CHECKUIT("Uitchecken van gasten uit een kamer",new Prompt[]{
														new IntegerPrompt("Welk kamernummer?")}),
	RESERVERING("Reserveren van een kamer",new Prompt[]{
														new DatePrompt("Welke datum zou u aan willen komen?"),
														new IntegerPrompt("Hoeveel nachten wilt u verblijven?"),
														new IntegerPrompt("Hoeveel eenpersoonsbedden?"),
														new IntegerPrompt("Hoeveel kinderbedden?"), 
														new IntegerPrompt("Hoeveel tweepersoonsbedden?"), 
														new BooleanPrompt("Roken?")}),
	ANNULEERRESERVERING("Annuleren van een reservering",new Prompt[]{
														new IntegerPrompt("Wat is uw reserveringsnummer?")			
	}),
	REKENING( "Uitprinten van een rekening",new Prompt[]{
														new IntegerPrompt("Wat is uw reserveringsnummer?")
	});
	
	public static final char DELIM='\u0000';
	private Prompt[] prompts;
	private String menuText;
	private Command(String text, Prompt[] requiredInput){
		prompts=requiredInput;
		menuText=text;
	}
	
	public String toString(){
		return menuText;
	}
	
	public Prompt[] getPrompts(){
		return prompts;
	}
}
