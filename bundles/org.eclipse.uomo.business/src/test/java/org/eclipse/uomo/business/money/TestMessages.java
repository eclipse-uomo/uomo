package org.eclipse.uomo.business.money;

import org.eclipse.osgi.util.NLS;

public class TestMessages extends NLS {
	private static final String BUNDLE_NAME = TestMessages.class.getPackage().getName() + ".testmessages"; //$NON-NLS-1$
	public static String MoneyDemo_Car_mileage;
	public static String MoneyDemo_Gas_price;
	public static String MoneyDemo_Trip_cost;
	public static String MoneyDemo_Trip_distance;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, TestMessages.class);
	}

	private TestMessages() {
	}
}
