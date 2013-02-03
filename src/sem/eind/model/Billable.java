package sem.eind.model;

public class Billable{
	private double prijs;
	private String beschrijving;
	
	public Billable(String beschrijving, double prijs){
		this.beschrijving=beschrijving;
		this.prijs=prijs;
	}
	public double getPrijs(){
		return prijs;
	}
	public String getBeschrijving(){
		return beschrijving;
	}
	
	@Override
	public String toString(){
		return String.format("%-30s", getBeschrijving())+prijs;
	}
}