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
		if(reserveringen==null)
			reserveringen=new HashMap<Integer, Reservering>();
		reserveringen.put(reserveringsNummer, this);
		RESERVERIGNSCOUNTER++;
	}
	/**
	 * Geeft true terug als de input data overlap hebben met de data van deze reservering.
	 * @param year het te vergelijken jaar.
	 * @param month de te vergelijken maand.
	 * @param day de te vergelijken dag.
	 * @param numberOfDays het aantal dagen verblijf(einddatum wordt uitgerekend aan de hand hiervan)
	 * @return
	 */
	public boolean heeftOverlap(int year, int month, int day, int numberOfDays){
		LocalDate aankomst=new LocalDate(year, month, day);
		LocalDate vertrek=new LocalDate(year,month, day).plusDays(numberOfDays);
		
		return heeftOverlap(aankomst, vertrek);
		
	}
	/**
	 * Geeft true terug als de input data overlap hebben met de data van deze reservering.
	 * @param aankomst Datum van aankomst.
	 * @param vertrek	Datum van vertrek.
	 * @return
	 */
	public boolean heeftOverlap(LocalDate aankomst,LocalDate vertrek){
		return (dagVanVertrek.compareTo(aankomst)>0&&dagVanVertrek.compareTo(vertrek)<0)||
			(dagVanAankomst.compareTo(aankomst)>=0&&dagVanAankomst.compareTo(vertrek)>-1);
	}
	
	public KamerType getKamerType(){
		return kamerType;
	}

	public Integer getReserveringsNummer() {
		return reserveringsNummer;
	}
	/**
	 * Anulleert deze reservering.
	 */
	public void annuleer(){
		reserveringen.remove(reserveringsNummer);
		getKamerType().getReserveringen().remove(this);
	}
	/**
	 * geeft De reservering terug aan de hand van het reserveringsnummer
	 */
	public static Reservering getReserveringForNummer(Integer nummer){
		return reserveringen.get(nummer);
	}
}
