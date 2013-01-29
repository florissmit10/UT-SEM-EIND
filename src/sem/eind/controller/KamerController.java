package sem.eind.controller;

import java.util.List;

import sem.eind.model.*;
import sem.eind.net.ErrorCodes;
import sem.eind.net.HotelException;

public class KamerController {
	
	public static void checkin(Kamer k, List<Gast> gasten)throws HotelException{
		if(k.getGasten()!=null)
			throw new HotelException("Kamer "+k.getNummer()+" is bezet!", ErrorCodes.OCCUPIED, k.getClass().toString());
		for(Gast g:gasten){
			if(g.getKamer()!=null)
			throw new HotelException("gast "+g.getNaam()+" heeft al een kamer", ErrorCodes.OCCUPIED, g.getClass().toString());
		}
		k.setGasten(gasten);
		for(Gast g:gasten){
			g.setKamer(k);
		}
	}

	public static void checkuit(Kamer k) throws HotelException{
		if(k.getGasten()==null)
			throw new HotelException("Er zijn geen gasten in kamer "+k.getNummer(), ErrorCodes.NOTFOUND,k.getClass().toString());

		for(Gast g:k.getGasten()){
			g.setKamer(null);
		}
		k.setGasten(null);
	}


}
