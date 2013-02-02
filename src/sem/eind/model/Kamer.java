package sem.eind.model;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.txw2.IllegalAnnotationException;

import sem.eind.net.ErrorCodes;
import sem.eind.net.HotelException;

public class Kamer {
	private ArrayList<Gast> gasten=new ArrayList<Gast>();
	
	private final KamerType type;
	
	private final int nummer;
/**
 * 
 * @param type
 * @param nummer
 * @throws IllegalArgumentException if type==null||nummer<0
 */
	public Kamer(KamerType type, int nummer) throws IllegalArgumentException{
		if(type==null)
			throw new IllegalArgumentException("Kamer kan niet geïnitialiseerd worden zonder kamertype");
		if(nummer<0)
			throw new IllegalArgumentException("Kamer kan niet geïnitialiseerd worden met een negatief kamernummer");
		this.type=type;
		this.nummer=nummer;
		this.type.addKamer(this);
	}
	
	public ArrayList<Gast> getGasten(){
		return this.gasten;
	}
	
	public void setGasten(List<Gast> gasten){
		this.gasten=(ArrayList<Gast>) gasten;
	}

	public KamerType getType() {
		return type;
	}

	public int getNummer() {
		return nummer;
	}
	
}
