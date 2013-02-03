package sem.eind.model;

public class Gast {
	
	private String naam;
	private Kamer kamer;
	
	/**
	 * 
	 * @param naam naam van de gast.
	 * @throws IllegalArgumentException If naam==null
	 */
	
	public Gast(String naam)throws IllegalArgumentException{
		if(naam==null)
			throw new IllegalArgumentException("Gast kan niet geïnitialiseerd worden zonder naam");
		this.naam=naam;
	}

	public String getNaam(){
		return naam;
	}
	
	public Kamer getKamer() {
		return kamer;
	}

	public void setKamer(Kamer kamer) {
		this.kamer = kamer;
	}

	@Override
	public String toString(){
		return naam;
	}
}
