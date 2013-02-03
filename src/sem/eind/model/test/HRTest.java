package sem.eind.model.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.swingui.TestRunner;

public class HRTest extends TestSuite{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestRunner.run(Test.class);

	}
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(HotelTest.class);
		suite.addTestSuite(RekeningTest.class);
		suite.addTestSuite(ReserveringTest.class);
		return suite;
	}
}
