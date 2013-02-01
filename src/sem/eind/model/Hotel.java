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

	public  void checkuit(Kamer k) throws HotelException{
		if(k.getGasten()==null)
			throw new HotelException("Er zijn geen gasten in kamer "+k.getNummer(), ErrorCodes.NOTFOUND,this.getClass().toString());

		for(Gast g:k.getGasten()){
			g.setKamer(null);
		}
		k.setGasten(null);
	}
	
	public ArrayList<Gast> getNieuweGastenFromStringArray(String[] gastnamen){
		ArrayList<Gast> returnable= new ArrayList<Gast>();
		for(String gast:gastnamen){
			Gast g=new Gast(gast);
			returnable.add(g);
			gastlijst.add(g);
		}
		return returnable;
		
	}
	
	public void maakReservering(KamerType kamerType, int year, int month, int day, int numberOfDays)throws HotelException, IllegalArgumentException{
		LocalDate aankomst=new LocalDate(year,month,day);
		LocalDate vertrek =new LocalDate(year,month,day).plusDays(numberOfDays);
		int numberOfOverlappingReservations=0;
		for(Reservering res:kamerType.getReserveringen()){
			if(res.heeftOverlap(aankomst, vertrek))
				numberOfOverlappingReservations++;
		}
		if(numberOfOverlappingReservations<kamerType.getAantalSlaapplekken())
			kamerType.addReservering(new Reservering(kamerType, year, month, day, numberOfDays));
		else{
			throw new HotelException("Er is geen ruimte in het hotel voor de gekozen data en kamertype", ErrorCodes.OCCUPIED, "Reservering");
		}
	}
	
	public void getReservering(){
		//TODO misschiennodig voor checkin
	}
}
