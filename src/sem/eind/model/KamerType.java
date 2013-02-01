package sem.eind.model;

import java.util.ArrayList;

public class KamerType {
	
	private ArrayList<Kamer> kamers= new ArrayList<Kamer>();
	
	private final boolean rokenToegestaan;
	
	private final ArrayList<Bed> bedden;
	
	private final ArrayList<Reservering> reserveringen=new ArrayList<Reservering>();
	
	private final double maximumprijs;
		
	public KamerType(boolean rokenToegestaan, ArrayList<Bed> bedden, double maxprijs){
		this.rokenToegestaan=rokenToegestaan;
		this.bedden=bedden;
		this.maximumprijs=maxprijs;
	}
	
	
	public void addKamer(Kamer k){
		kamers.add(k);
	}
	
	public void removeKamer(Kamer k){
		kamers.remove(k);
	}

	public ArrayList<Kamer> getKamers(){
		return kamers;
	}
	
	public int getAantalKamers(){
		return kamers.size();
	}
	
	public int getAantalSlaapplekken(){
		int returnable=0;
		for(Bed b:getBedden()){
			returnable=+b.getNumberOfPersons();
		}
		return returnable;
	}


	public boolean isRokenToegestaan() {
		return rokenToegestaan;
	}
	

	
	public ArrayList<Bed> getBedden() {
		return bedden;
	}

	public ArrayList<Reservering> getReserveringen() {
		return reserveringen;
	}
	
	public void addReservering(Reservering r){
		reserveringen.add(r);
	}

	public double getMaximumprijs() {
		return maximumprijs;
	}

	public enum Bed{
		KINDER("kinderbed", 1),
		EENPERSOONS("eenpersoonsbed", 1),
		TWEEPERSOONSBED("tweepersoonsbed", 2);
		
		private final String name;
		private final int numberOfPersons;
		private Bed(String name, int numberOfPersons){
			this.name=name;
			this.numberOfPersons=numberOfPersons;			
		}
		public int getNumberOfPersons() {
			return numberOfPersons;
		}
		public String getName() {
			return name;
		}
	}
}
