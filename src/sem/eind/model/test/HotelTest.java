package sem.eind.model.test;

import sem.eind.model.Hotel;
import sem.eind.net.HotelException;
import junit.framework.TestCase;

public class HotelTest extends TestCase {
	private Hotel hotel1;

	//default correct settings
	String defaultGastnamen="Floris, Dennis";
	Integer[] defaultBedden=new Integer[]{0, 2, 0};
@Override
protected void setUp() throws Exception {
	hotel1=new Hotel();
	super.setUp();
}

@Override
	protected void tearDown() throws Exception {
		hotel1=null;
		super.tearDown();
	}
public void testCheckin(){
	Exception ex=null;
	try {
		hotel1.checkin(defaultBedden,  false, defaultGastnamen, true);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Als er legale parameters meegegeven worden mag er geen exception gegooid worden", ex==null);

	ex=null;
	try {
		hotel1.checkin(new Integer[]{10,10,10},  false, defaultGastnamen, true);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Als een kamertype niet bestaat moet er een exception gegooid worden", ex!=null);
}
public void testCheckuit(){
	Exception ex=null;
		try {
		hotel1.checkin(defaultBedden,  false, defaultGastnamen, true);
		hotel1.checkUit(21);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Testen of checkuit werkt, wanneer dit op juiste manier gedaan wordt", ex==null);
	
	ex=null;
	try {
		hotel1.checkin(new Integer[]{10,10,10},  false, defaultGastnamen, true);
		hotel1.checkUit(21);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Als een kamer niet bezet is moet er een exception gegooid worden", ex!=null);
	
	ex=null;
		try {
		hotel1.checkin(defaultBedden,  false, defaultGastnamen, true);
		hotel1.checkUit(12);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Als een gast in een andere kamer zit dan dat hij wil uitchecken moet er een exception gegooid worden", ex!=null);
	}
	
public void testMaakReservering(){
	Exception ex=null;
	try {
		hotel1.maakReservering(new Integer[]{2013,11,10},3,new Integer[]{0,2,0},false);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Testen of een juiste reservering correct werkt", ex==null);
	
	ex=null;
	try {
		hotel1.maakReservering(new Integer[]{2013,11,10},3,new Integer[]{10,10,10},false);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Als een kamertype niet bestaat moet er een exception gegooid worden", ex!=null);
	}
public void testAnnulleerReservering(){
	Exception ex=null;
	try {
		hotel1.maakReservering(new Integer[]{2013,11,10},3,new Integer[]{0,2,0},false);
		hotel1.annulleerReservering(1);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Testen of een juiste annuleren correct werkt", ex==null);

	ex=null;
	try {
		hotel1.maakReservering(new Integer[]{2013,11,10},3,new Integer[]{10,10,10},false);
		hotel1.annulleerReservering(1);
	} catch (HotelException e) {
		ex=e;
	}
	assertTrue("Als een reservering niet bestaat maar toch iemand probeert te annuleren moet een exception gegooid worden", ex!=null);
}
}