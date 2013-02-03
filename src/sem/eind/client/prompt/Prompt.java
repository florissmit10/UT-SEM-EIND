package sem.eind.client.prompt;

import java.lang.reflect.ParameterizedType;
/**
 * Klasse die als verantwoordelijkheid heeft om invoer te controlerenop type's
 * @author floris
 *
 * @param <T> Het type wat deze prompt moet opleveren.
 */
public abstract class Prompt<T>{
	
	private String promptMessage;
	public Prompt(String promptMessage){
		this.promptMessage=promptMessage;
	}
	/**
	 * moet de input parsen en teruggeven of het voldoet aan de eisen.
	 */
	public abstract boolean isInputValid(String input);
	
	/**
	 * geeft het formaat van de gewenste invoer terug. Hulp voor de gebruiker.
	 */
	public abstract String getFormat();
	
	/**
	 * Geeft het type terug wat deze prompt omvat.
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getPromptType(){
		 ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		 return (Class<T>) superclass.getActualTypeArguments()[0];
	}
	/**
	 * 	Geeft de vraag terug die gesteld moet worden aan de gebruiker.
	 * @return
	 */
	public String getPrompt(){
		return promptMessage+ getFormat();
	}
	/**
	 * Geeft het object terug aan de hand van een String
	 */
	public abstract T parseObjectFromString(String string);

}