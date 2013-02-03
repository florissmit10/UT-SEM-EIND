package sem.eind.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import sem.eind.net.ErrorCodes;
import sem.eind.net.HotelException;

/**
 * 
 * @author floris
 *
 */
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
	/**
	 * Geeft de kamer die correspondeert met een bepaald nummer
	 * @param nummer
	 * @return Kamer
	 */
	public Kamer getKamerForNummer(int nummer){
		for(KamerType type:kamerTypes){
			for(Kamer k: type.getKamers()){
				if(k.getNummer()==nummer)
					return k;
			}
		}
		return null;
	}
/**
 * Zoekt in de kamers van een KamerType naar een vrije kamer en geeft de eerste die gevonden wordt terug
 */
	public Kamer getVrijeKamerForKamerType(KamerType type){
		for (Kamer k:type.getKamers()){
			if(k.getGasten().isEmpty())
				return k;
		}
		return null;
	}
	/**
	 * Geeft het kamertype met bepaalde eigenschappen
	 * @param bedden van het formaat (int, int, int)
	 * @param isRoken true als roken is toegestaan
	 * @return KamerType
	 * @throws HotelException Wanneer geen kamer kan worden gevonden
	 */
	
	public KamerType getKamerType(Integer[] bedden, boolean isRoken)throws HotelException{
		for(KamerType type:kamerTypes){
			if(type.containsBeds(bedden)&&type.isRokenToegestaan()==isRoken)
				return type;
		}
		throw new HotelException("Er is geen kamerType gevonden die voldoet aan de eisen.", ErrorCodes.NOTFOUND);
	}
	/**
	 * Probeert een gast in te checken. Als dat niet lukt wordt een exception gegooid
	 * @param bedden het aantal bedden in de kamer. heeft het formaat(int, int, int)
	 * @param isRoken true als roken is toegestaan
	 * @param gastnamen namen van de gasten die willen inchecken, gescheiden door komma.
	 * @param isHoogTarief true wanneer deze gasten verblijven voor tarieven in het hoogseizoen.
	 * @return Een message met het resultaat voor de client.
	 * @throws HotelException
	 */
	public String checkin( Integer[] bedden, Boolean isRoken,String gastnamen, Boolean isHoogTarief) throws HotelException{
		KamerType type = getKamerType(bedden, isRoken);
		Kamer k = getVrijeKamerForKamerType(type);
		k.setHoogtarief(isHoogTarief);
		List<Gast> gasten=getNieuweGastenFromCommaSeperatedString(gastnamen);
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
	return "Het inchecken in kamer "+k.getNummer()+" is gelukt!";
	}
	/**
	 * Checkt alle gasten uit een kamer aan de hand van een kamernummer.
	 * @param i het kamernummer van de kamer waar uitgecheckt wil worden.
	 * @return Een message met het resultaat voor de client.
	 * @throws HotelException
	 */
	public String checkUit(Integer i)throws HotelException{
		System.out.println("checkUit");
		Kamer kamer=getKamerForNummer(i);
		if(kamer==null)
			throw new HotelException("Er is geen kamernummer gevonden met nummer "+i, ErrorCodes.NOTFOUND);
		if(kamer.getGasten()==null||kamer.getGasten().isEmpty())
			throw new HotelException("Er zijn geen gasten in kamer "+kamer.getNummer(), ErrorCodes.NOTFOUND);

		for(Gast g:kamer.getGasten()){
			g.setKamer(null);
		}
		kamer.setGasten(null);
		return "Uitchecken uit kamer "+kamer.getNummer()+" is gelukt."+'\n'+kamer.getRekening().toString();
	}
	/**
	 * Probeert een reservering te maken aan de hand van een aantal parameters
	 * @param datum int[] van de vorm (jaar, maand, dag)
	 * @param numberOfDays
	 * @param bedden het aantal bedden in de kamer. heeft het formaat(int, int, int)
	 * @param isRoken true als roken is toegestaan
	 * @return Een message met het resultaat voor de client.
	 * @throws HotelException als het niet lukt om een reservering te maken
	 */
	public String maakReservering(Integer[] datum,Integer numberOfDays,Integer[] bedden, Boolean isRoken)throws HotelException{
		KamerType type=getKamerType(bedden, isRoken);
		LocalDate aankomst=new LocalDate(datum[0],datum[1],datum[2]);
		LocalDate vertrek =new LocalDate(datum[0],datum[1],datum[2]).plusDays(numberOfDays);
		int numberOfOverlappingReservations=0;
		for(Reservering res:type.getReserveringen()){
			if(res.heeftOverlap(aankomst, vertrek))
				numberOfOverlappingReservations++;
		}
		if(numberOfOverlappingReservations<type.getAantalKamers()){
			Reservering reservering=new Reservering(type, datum[0],datum[1],datum[2], numberOfDays);
			type.addReservering(reservering);
			return "Het maken van de reservering is gelukt. Uw reserveringsnummer is "+reservering.getReserveringsNummer()+"";
		}
		else{
			throw new HotelException("Er is geen ruimte in het hotel voor de gekozen data en kamertype", ErrorCodes.OCCUPIED);
		}
		
	}
/**
 * Probeert een reservering te annuleren
 * @param reserveringsnummer nummer van de reservering.
 * @return Een message met het resultaat voor de client.
 * @throws HotelException
 */
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
	private void onNewDay(){
		today.plusDays(1);
		for(KamerType type:kamerTypes){
			for(Kamer kamer: type.getKamers()){
				if(!kamer.getGasten().isEmpty())
				kamer.getRekening().addBillable(new Billable("Overnachting k"+kamer.getNummer()+"", kamer.getPrijs()));
			}
		}
		
	}
/**
 * hulpmethode die een met komma's gescheiden String met gastnamen een ArrayList met gasten maakt.
 */
	private ArrayList<Gast> getNieuweGastenFromCommaSeperatedString(String gastnamen){
		ArrayList<Gast> returnable= new ArrayList<Gast>();
		for(String gast:gastnamen.split(",")){
			Gast g=new Gast(gast);
			returnable.add(g);
			gastlijst.add(g);
		}
		return returnable;
		
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
