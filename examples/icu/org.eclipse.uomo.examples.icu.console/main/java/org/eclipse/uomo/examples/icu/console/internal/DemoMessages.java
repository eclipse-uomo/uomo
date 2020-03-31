/*
 * Copyright (c) 2005, 2020, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.icu.console.internal;

import org.eclipse.osgi.util.NLS;

public class DemoMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.uomo.business.money.demomessages"; //$NON-NLS-1$
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
