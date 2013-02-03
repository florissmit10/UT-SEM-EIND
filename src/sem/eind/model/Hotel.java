package sem.eind.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import sem.eind.net.ErrorCodes;
import sem.eind.net.HotelException;


public class Hotel {
	private ArrayList<Gast> gastlijst=new ArrayList<Gast>();
	private ArrayList<KamerType> kamerTypes= new ArrayList<KamerType>();
	
	
	public Kamer getKamerForNummer(int nummer){
		for(KamerType type:kamerTypes){
			for(Kamer k: type.getKamers()){
				if(k.getNummer()==nummer)
					return k;
			}
		}
		return null;
	}

	public ArrayList<Kamer> getKamersForKamerType(KamerType type){
		return type.getKamers();
	}
	
	public KamerType getKamerType(Integer[] bedden, boolean isRoken)throws HotelException{
		for(KamerType type:kamerTypes){
			if(type.containsBeds(bedden)&&type.isRokenToegestaan()==isRoken)
				return type;
		}
		throw new HotelException("Er is geen kamerType gevonden die voldoet aan de eisen.", ErrorCodes.NOTFOUND, "KamerType");
	}
	
	public void checkin(List<Gast> gasten, Kamer k)throws HotelException{
		if(k.getGasten()!=null)
			throw new HotelException("Kamer "+k.getNummer()+" is bezet!", ErrorCodes.OCCUPIED, this.getClass().toString());
		for(Gast g:gasten){
			if(g.getKamer()!=null)
			throw new HotelException("gast "+g.getNaam()+" heeft al een kamer", ErrorCodes.OCCUPIED, g.getClass().toString());
		}
		k.setGasten(gasten);
		for(Gast g:gasten){
			g.setKamer(k);
		}
	}

	public String checkin( Integer[] bedden, Boolean isRoken,String gastnamen) throws HotelException{
		KamerType type = getKamerType(bedden, isRoken);
		Kamer k = getKamersForKamerType(type).get(0);
		List<Gast> gasten=getNieuweGastenFromCommaSeperatedString(gastnamen);
		checkin(gasten, k);
		return "Het inchecken in kamer "+k.getNummer()+" is gelukt!";
	}
	
	public String checkUit(Integer i)throws HotelException{
		System.out.println("checkUit");
		Kamer kamer=getKamerForNummer(i);
		checkuit(kamer);
		return "Uitchecken uit kamer "+kamer.getNummer()+" is gelukt.";
	}
	
	public String maakReservering(Integer[] datum,Integer numberOfDays,Integer[] bedden, Boolean isRoken)throws HotelException{
		System.out.println("maakReservering");
		KamerType type=getKamerType(bedden, isRoken);
		int nummer =maakReservering(type, datum, numberOfDays);
		return "Het maken van de reservering is gelukt. Uw reserveringsnummer is "+nummer;
	}

	public String annulleerReservering(Integer reserveringsnummer) throws HotelException{
		System.out.println("annulleerReservering");
		Reservering reservering= Reservering.getReserveringForNummer(reserveringsnummer);
		if(reservering==null)
			throw new HotelException("", error, classname)
		return "";
	}
	
	public String getRekening(Integer kamernummer){
		System.out.println("getrekening");
		return "";
	}
	
	public  void checkuit(Kamer k) throws HotelException{
		if(k.getGasten()==null)
			throw new HotelException("Er zijn geen gasten in kamer "+k.getNummer(), ErrorCodes.NOTFOUND,this.getClass().toString());

		for(Gast g:k.getGasten()){
			g.setKamer(null);
		}
		k.setGasten(null);
	}
	
	public ArrayList<Gast> getNieuweGastenFromCommaSeperatedString(String gastnamen){
		ArrayList<Gast> returnable= new ArrayList<Gast>();
		for(String gast:gastnamen.split(",")){
			Gast g=new Gast(gast);
			returnable.add(g);
			gastlijst.add(g);
		}
		return returnable;
		
	}
	
	public int maakReservering(KamerType kamerType, Integer[] datum, int numberOfDays)throws HotelException, IllegalArgumentException{
		LocalDate aankomst=new LocalDate(datum[0],datum[1],datum[2]);
		LocalDate vertrek =new LocalDate(datum[0],datum[1],datum[2]).plusDays(numberOfDays);
		int numberOfOverlappingReservations=0;
		for(Reservering res:kamerType.getReserveringen()){
			if(res.heeftOverlap(aankomst, vertrek))
				numberOfOverlappingReservations++;
		}
		if(numberOfOverlappingReservations<kamerType.getAantalSlaapplekken()){
			Reservering reservering=new Reservering(kamerType, datum[0],datum[1],datum[2], numberOfDays);
			kamerType.addReservering(reservering);
			return reservering.getReserveringsNummer();
		
		}
		else{
			throw new HotelException("Er is geen ruimte in het hotel voor de gekozen data en kamertype", ErrorCodes.OCCUPIED, "Reservering");
		}
	}
}
