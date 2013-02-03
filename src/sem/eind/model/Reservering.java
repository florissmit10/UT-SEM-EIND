package sem.eind.model;


import java.util.HashMap;

import org.joda.time.LocalDate;

import sem.eind.net.HotelException;

public class Reservering {
	private KamerType kamerType;
	private LocalDate dagVanVertrek;
	private LocalDate dagVanAankomst;
	private Integer reserveringsNummer;
	
	public static Integer RESERVERIGNSCOUNTER=1;
	public static HashMap<Integer,Reservering> reserveringen;
	
	/**
	 * 
	 * @param type
	 * @param year
	 * @param month
	 * @param day
	 * @param numberOfDays
	 * @throws IllegalArgumentException
	 */
	public Reservering(KamerType type, int year, int month, int day, int numberOfDays) throws IllegalArgumentException{
		if(numberOfDays<1)
			throw new IllegalArgumentException("Kan geen reservering maken met numberOfDays<1");
		kamerType=type;
		dagVanAankomst=new LocalDate(year, month, day);
		dagVanVertrek=new LocalDate(year,month, day).plusDays(numberOfDays);
		reserveringsNummer=RESERVERIGNSCOUNTER;
		reserveringen.put(reserveringsNummer, this);
		RESERVERIGNSCOUNTER++;
	}
	
	public boolean heeftOverlap(int year, int month, int day, int numberOfDays){
		LocalDate aankomst=new LocalDate(year, month, day);
		LocalDate vertrek=new LocalDate(year,month, day).plusDays(numberOfDays);
		
		return heeftOverlap(aankomst, vertrek);
		
	}
	/**
	 * 
	 * @param aankomst
	 * @param vertrek
	 * @return
	 */
	public boolean heeftOverlap(LocalDate aankomst,LocalDate vertrek){
		
		//TODO goed testen met unit test
		return (dagVanVertrek.compareTo(aankomst)>0&&dagVanVertrek.compareTo(vertrek)<0)||
			(dagVanAankomst.compareTo(aankomst)>=0&&dagVanAankomst.compareTo(vertrek)>-1);
	}
	
	public KamerType getKamerType(){
		return kamerType;
	}

	public Integer getReserveringsNummer() {
		return reserveringsNummer;
	}
	
	public void annuleer(){
		reserveringen.remove(reserveringsNummer);
		getKamerType().getReserveringen().remove(this);
	}
	
	public static Reservering getReserveringForNummer(Integer nummer){
		return reserveringen.get(nummer);
	}
}
