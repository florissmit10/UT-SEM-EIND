package sem.eind.model;

import java.util.ArrayList;
import java.util.List;

public class Kamer {
	private ArrayList<Gast> gasten=new ArrayList<Gast>();
	
	private final KamerType type;
	
	private Rekening rekening=new Rekening();
	
	private final int nummer;
	
	private boolean isHoogtarief=false;
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

	public Rekening getRekening() {
		return rekening;
	}
	
	public void setRekening(Rekening r){
		this.rekening=r;
	}

	public double getPrijs() {
		return type.getMaximumprijs()*(isHoogtarief?1:0.75);
	}

	public void setHoogtarief(Boolean isHoogTarief) {
		this.isHoogtarief=isHoogTarief;
	}
	
	@Override
	public String toString(){
	return "Kamer "+getNummer()+" Gasten: "+(getGasten().isEmpty()?"Leeg":getGasten().toString());
	}
}
