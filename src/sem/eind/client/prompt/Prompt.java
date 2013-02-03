package sem.eind.client.prompt;

import java.lang.reflect.ParameterizedType;

public abstract class Prompt<T>{
	
	private String promptMessage;
	public Prompt(String promptMessage){
		this.promptMessage=promptMessage;
	}
	
	public abstract boolean isInputValid(String input);
	
	public abstract String getFormat();
	
	@SuppressWarnings("unchecked")
	public Class<T> getPromptType(){
		 ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		 return (Class<T>) superclass.getActualTypeArguments()[0];
	}
	
	public String getPrompt(){
		return promptMessage+ getFormat();
	}
	public abstract T parseObjectFromString(String string);

}