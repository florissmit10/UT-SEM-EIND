package sem.eind.model;

import java.util.ArrayList;
import java.util.List;

public class KamerType {
	
	private ArrayList<Kamer> kamers= new ArrayList<Kamer>();
	
	private final boolean rokenToegestaan;
	
	private final ArrayList<Bed> bedden;
	
	private final double maximumprijs;
	
	private String qualitylevel="moeten we hier nog iets mee?";
	
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

	public List<Kamer> getKamers(){
		return kamers;
	}


	public boolean isRokenToegestaan() {
		return rokenToegestaan;
	}
	
	public ArrayList<Bed> getBedden() {
		return bedden;
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
