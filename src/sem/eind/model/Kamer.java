package sem.eind.model;

import java.util.ArrayList;
import java.util.List;

public class Kamer {
	private ArrayList<Gast> gasten=new ArrayList<Gast>();
	
	private final KamerType type;
	
	private final int nummer;
	
	public Kamer(KamerType type, int nummer){
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
