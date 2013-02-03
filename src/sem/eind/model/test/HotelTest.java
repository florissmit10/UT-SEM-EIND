package sem.eind.model.test;

import sem.eind.model.Hotel;
import sem.eind.net.HotelException;
import junit.framework.TestCase;

public class HotelTest extends TestCase {
	private Hotel hotel1;
	private Hotel hotel2;
	private Hotel hotel3;
	
	//default correct settings
	String defaultGastnamen="Floris, Dennis";
	Integer[] defaultBedden=new Integer[]{0, 2, 0};
@Override
protected void setUp() throws Exception {
	hotel1=new Hotel();
	hotel2=new Hotel();
	hotel3=new Hotel();
	super.setUp();
}

@Override
	protected void tearDown() throws Exception {
		hotel1=null;
		hotel2=null;
		hotel3=null;
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


}
