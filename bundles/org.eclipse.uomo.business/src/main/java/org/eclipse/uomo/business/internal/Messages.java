/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.business.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackage()
			.getName() + ".messages"; //$NON-NLS-1$
	public static String CurrencyConverter_exchangeRate_not_set;
	public static String CurrencyConverter_toString;
	public static String Market_invalid_code;
	public static String StockTicker_invalid_symbol;
	public static String StockTicker_invalid_market;
	public static String StockTicker_invalid_country;
	public static String StockTicker_missing_symbol;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
