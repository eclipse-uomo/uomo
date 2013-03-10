package org.eclipse.uomo.examples.business.console.internal;

import org.eclipse.osgi.util.NLS;

public class DemoMessages extends NLS {
	private static final String BUNDLE_NAME = DemoMessages.class.getPackage().getName() + ".demomessages"; //$NON-NLS-1$
	public static String MoneyDemo_Car_mileage;
	public static String MoneyDemo_Gas_price;
	public static String MoneyDemo_Trip_cost;
	public static String MoneyDemo_Trip_distance;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, DemoMessages.class);
	}

	private DemoMessages() {
	}
}
