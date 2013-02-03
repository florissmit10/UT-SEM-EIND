package sem.eind.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import sem.eind.net.ErrorCodes;
import sem.eind.net.HotelException;


public class Hotel {
	private ArrayList<Gast> gastlijst=new ArrayList<Gast>();
	private ArrayList<KamerType> kamerTypes= new ArrayList<KamerType>();
	private static LocalDate today = new LocalDate();
	
	public Hotel(){
		init();
	}
	
	public void init(){
		kamerTypes.add(new KamerType(true, new int[]{0, 0, 1}, 50));
		kamerTypes.add(new KamerType(false, new int[]{0, 0, 1}, 50));
		kamerTypes.add(new KamerType(false, new int[]{0, 2, 0}, 50));
		kamerTypes.add(new KamerType(false, new int[]{0, 2, 1}, 80));
		int i=1;
		for(KamerType type: kamerTypes){
			new Kamer(type, i);
			new Kamer(type,i+1);
			new Kamer(type,i+2);
			i=i+10;
		}
		
		
	}
	
	public Kamer getKamerForNummer(int nummer){
		for(KamerType type:kamerTypes){
			for(Kamer k: type.getKamers()){
				if(k.getNummer()==nummer)
					return k;
			}
		}
		return null;
	}

	public Kamer getVrijeKamerForKamerType(KamerType type){
		for (Kamer k:type.getKamers()){
			if(k.getGasten().isEmpty())
				return k;
		}
		return null;
	}
	
	public KamerType getKamerType(Integer[] bedden, boolean isRoken)throws HotelException{
		for(KamerType type:kamerTypes){
			if(type.containsBeds(bedden)&&type.isRokenToegestaan()==isRoken)
				return type;
		}
		throw new HotelException("Er is geen kamerType gevonden die voldoet aan de eisen.", ErrorCodes.NOTFOUND);
	}
	
	public void checkin(List<Gast> gasten, Kamer k)throws HotelException{
		if(!k.getGasten().isEmpty())
			throw new HotelException("Kamer "+k.getNummer()+" is bezet!", ErrorCodes.OCCUPIED);
		for(Gast g:gasten){
			if(g.getKamer()!=null)
			throw new HotelException("gast "+g.getNaam()+" heeft al een kamer", ErrorCodes.OCCUPIED);
		}
		k.setGasten(gasten);
		for(Gast g:gasten){
			g.setKamer(k);
		}
		k.setRekening(new Rekening());
	}

	public String checkin( Integer[] bedden, Boolean isRoken,String gastnamen, Boolean isHoogTarief) throws HotelException{
		KamerType type = getKamerType(bedden, isRoken);
		Kamer k = getVrijeKamerForKamerType(type);
		k.setHoogtarief(isHoogTarief);
		List<Gast> gasten=getNieuweGastenFromCommaSeperatedString(gastnamen);
		checkin(gasten, k);
		return "Het inchecken in kamer "+k.getNummer()+" is gelukt!";
	}
	
	public String checkUit(Integer i)throws HotelException{
		System.out.println("checkUit");
		Kamer kamer=getKamerForNummer(i);
		if(kamer==null)
			throw new HotelException("Er is geen kamernummer gevonden met nummer "+i, ErrorCodes.NOTFOUND);
		checkuit(kamer);
		return "Uitchecken uit kamer "+kamer.getNummer()+" is gelukt."+'\n'+kamer.getRekening().toString();
	}
	
	public String maakReservering(Integer[] datum,Integer numberOfDays,Integer[] bedden, Boolean isRoken)throws HotelException{
		KamerType type=getKamerType(bedden, isRoken);
		int nummer =maakReservering(type, datum, numberOfDays);
		return "Het maken van de reservering is gelukt. Uw reserveringsnummer is "+nummer+"";
	}

	public String annulleerReservering(Integer reserveringsnummer) throws HotelException{
		System.out.println("annulleerReservering");
		Reservering reservering= Reservering.getReserveringForNummer(reserveringsnummer);
		if(reservering==null)
			throw new HotelException("De reservering met nummer: "+reserveringsnummer+" bestaat niet.", ErrorCodes.NOTFOUND);
		reservering.annuleer();
		return "De reservering met nummer "+reservering.getReserveringsNummer()+" is geannuleerd";
	}
	
	public String getRekening(Integer kamernummer) throws HotelException{
		Kamer k =getKamerForNummer(kamernummer);
		if(k==null)
			throw new HotelException("Kamer met kamernummer "+kamernummer+"niet gevonden",ErrorCodes.NOTFOUND);
		else if(k.getGasten().isEmpty())
			throw new HotelException("Gevonden kamer is leeg",ErrorCodes.NOTFOUND);
		
		
		return k.getRekening().toString();
	}
	
	public String addDagen(Integer aantalDagen){
		for(int i=0;i<aantalDagen;i++){
			onNewDay();
		}
		return aantalDagen+" dagen verder. Datum is "+today.toString();
	}
	/**
	 * Methode die wordt aangeroepen wanneer er een nieuwe dag aanbreekt.
	 */
	public void onNewDay(){
		today.plusDays(1);
		for(KamerType type:kamerTypes){
			for(Kamer kamer: type.getKamers()){
				if(!kamer.getGasten().isEmpty())
				kamer.getRekening().addBillable(new Billable("Overnachting k"+kamer.getNummer()+"", kamer.getPrijs()));
			}
		}
		
	}
	
	public  void checkuit(Kamer k) throws HotelException{
		if(k.getGasten()==null||k.getGasten().isEmpty())
			throw new HotelException("Er zijn geen gasten in kamer "+k.getNummer(), ErrorCodes.NOTFOUND);

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
		if(numberOfOverlappingReservations<kamerType.getAantalKamers()){
			Reservering reservering=new Reservering(kamerType, datum[0],datum[1],datum[2], numberOfDays);
			kamerType.addReservering(reservering);
			return reservering.getReserveringsNummer();
		
		}
		else{
			throw new HotelException("Er is geen ruimte in het hotel voor de gekozen data en kamertype", ErrorCodes.OCCUPIED);
		}
	}
	public String toString(){
		String returnable="Hotel bovenzicht"+'\n';
		for(KamerType type:kamerTypes){
			for(Kamer k:type.getKamers()){
				returnable=returnable+k.toString()+'\n';
			}
		}		
		return returnable;
	}
}
