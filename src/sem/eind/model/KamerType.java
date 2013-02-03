package sem.eind.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import sem.eind.model.KamerType.Bed;

public class KamerType {
	
	private ArrayList<Kamer> kamers= new ArrayList<Kamer>();
	
	private final boolean rokenToegestaan;
	
	private final HashMap<Bed,Integer> bedden=new HashMap<Bed, Integer>();
	
	private final ArrayList<Reservering> reserveringen=new ArrayList<Reservering>();
	
	private final double maximumprijs;
		
	public KamerType(boolean rokenToegestaan, int[] bedden, double maxprijs)throws IllegalArgumentException{
		if(bedden.length!=Bed.values().length)
			throw new IllegalArgumentException("Het aantal waarden in int[] bedden moet gelijk zijn aan het aantal Enums in Bed.");
		this.rokenToegestaan=rokenToegestaan;
		for(int i=0;i<bedden.length;i++){
			Bed bed=Bed.values()[i];
			this.bedden.put(bed, bedden[i]);
		}
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
	/**
	 * geeft het aantal slaapplekken terug wat dit kamertype heeft
	 * @return
	 */
	public int getAantalSlaapplekken(){
		int returnable=0;
		for(Entry<Bed, Integer> entry:bedden.entrySet()){
			returnable=returnable+entry.getValue()*entry.getKey().getNumberOfPersons();
		}
		return returnable;
	}


	public boolean isRokenToegestaan() {
		return rokenToegestaan;
	}
	

	
	public HashMap<Bed,Integer> getBedden() {
		return bedden;
	}
	
	public boolean containsBeds(Integer[] bednumbers) throws IllegalArgumentException{
		if(bednumbers.length!=Bed.values().length)
			throw new IllegalArgumentException("Het aantal bednummers in int[] bednummers moet gelijk zijn aan het aantal verschillende bedden in Kamertype.Bed");
		for(int i=0;i<bednumbers.length;i++){
			if(bednumbers[i]!=getBedden().get(Bed.values()[i]))
				return false;
		}
		return true;
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
		
		@Override
		public String toString(){
			return name;
		}
	}
}
