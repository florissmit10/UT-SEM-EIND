package sem.eind.model.test;

import sem.eind.model.Billable;
import sem.eind.model.Rekening;
import junit.framework.TestCase;

public class RekeningTest extends TestCase {
	Rekening rekening1;

	@Override
	protected void setUp() throws Exception {
		rekening1=new Rekening();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		rekening1=null;
		super.tearDown();
	}
	
	public void testGetTotaalbedrag(){
		assertTrue(rekening1.getTotaalbedrag()==0);
		rekening1.addBillable(new Billable("Test", 10.0));
		assertTrue(rekening1.getTotaalbedrag()==10.0);
		rekening1.addBillable(new Billable("Test1", 12.2));
		assertTrue(rekening1.getTotaalbedrag()==22.2);
	}
}
