package sem.eind.model.test;

import sem.eind.model.KamerType;
import sem.eind.model.Reservering;
import junit.framework.TestCase;

public class ReserveringTest extends TestCase {
	Reservering reservering1;
	@Override
	protected void setUp() throws Exception {
		KamerType type=new KamerType(true, new int[]{0,0,0},1.0);
		reservering1=new Reservering(type, 2013, 2, 14, 2);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		reservering1=null;
		super.tearDown();
	}
	
	public void testOverlap(){
		assertTrue(reservering1.heeftOverlap(2013,2,15 ,1));
		assertTrue(reservering1.heeftOverlap(2013,2,14 ,1));
		//vlak er na
		assertFalse(reservering1.heeftOverlap(2013,2,15 ,1));
		//vlak er voor
		assertFalse(reservering1.heeftOverlap(2013,2,13 ,1));
		
		//testen met andere maanden
		assertFalse(reservering1.heeftOverlap(2013,1,16 ,1));
		assertFalse(reservering1.heeftOverlap(2013,3,16 ,1));
		
		//testen met andere jaren
		assertFalse(reservering1.heeftOverlap(2012,1,16 ,1));
		assertFalse(reservering1.heeftOverlap(1912,1,16 ,1));
		assertFalse(reservering1.heeftOverlap(2112,1,16 ,1));
	}
}
