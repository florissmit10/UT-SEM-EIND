package sem.eind.model;

public class Gast {
	
	private String naam;
	private Kamer kamer;
	
	public Gast(String naam){
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

}
