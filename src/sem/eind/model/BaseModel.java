package sem.eind.model;

import java.lang.reflect.Constructor;


public abstract class BaseModel {
	
	
	/**
	 * Zoekt in de sub klasse naar een constructor met de @TextFileInitialisable 
	 * annotatie en geeft het aantal argumenten terug wat deze constructor heeft.
	 * @return het aantal argumenten. 
	 */
	public int getNumberOfArguments(){
		int returnable=0;
		Class<? extends BaseModel> thisClass=this.getClass();
		Constructor<?>[] cs=thisClass.getDeclaredConstructors();
		for(Constructor<?> c:cs){
			if(c.getAnnotation(TextFileInitialisable.class)!=null);
				returnable=c.getParameterTypes().length;
		}		
		return returnable;
	}
	//TODO dit verder pimpen.
	public static Object initInstanceFromArgs(String[] args){
	
		return null;
	}
	
	public @interface TextFileInitialisable {}
}
