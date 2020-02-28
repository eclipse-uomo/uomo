/**
 * Copyright (c) 2005, 2011, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.icu.impl;

import java.util.*;

/**
 * This is a helper class supporting the other classes in this package
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */

public abstract class DataHelper {
	public static final char BDT_DELIM = ';';

	// The following table will be loaded from a database - but not before DCUT3

	static HashMap<String, SimpleTimeZone> s_timeZoneTable = null; // table of
																	// time
																	// zones

	static {

		s_timeZoneTable = new HashMap<String, SimpleTimeZone>();

		SimpleTimeZone tz;

		tz = addTimeZone("UTC", 0.0); // UTC - same as GMT
		tz = addTimeZone("GMT", 0.0); // UTC - same as GMT

		tz = addTimeZone("EST", -5.0); // Eastern Standard Time
		tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

		tz = addTimeZone("NST", -3.5); // Newfoundland
		tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

		tz = addTimeZone("AST", -4.0); // Atlantic Standard Time
		tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

		tz = addTimeZone("CST", -6.0); // Central Standard Time
		tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

		tz = addTimeZone("SST", -6.0); // Central Standard Time (Sask) - no DST

		tz = addTimeZone("MST", -7.0); // Mountain Standard Time
		tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

		tz = addTimeZone("PST", -8.0); // Pacific Standard Time
		tz.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		tz.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

		addTimeZone("JST", 9.0); // Japan standard time - no daylight savings
									// time

	}

	/**
	 * Dummy constructor for BDTHelper - ensures no instance can be built
	 */

	private DataHelper() {
	}

	/**
	 * Add a currency to s_currencyTable (at initialization time)
	 * 
	 * @param s
	 *            java.lang.String
	 * @param precision
	 *            int
	 */
	// public static void AddCurrency(String s, int precision) {
	// Currency cur = new Currency(s, precision);
	// s_currencyTable.put(s, cur);
	// }
	/**
	 * Add a Market to s_marketTable (at initialization time)
	 * 
	 * @param s1
	 *            java.lang.String - code
	 * @param s2
	 *            java.lang.String - timezone
	 * @param s3
	 *            java.lang.String - country_code
	 * @param s4
	 *            java.lang.String - quote_currency
	 */
	// public static void AddMarket(String s1, String s2, String s3, String s4)
	// {
	// IMarket mkt = Market.CreateMarket(s1, s2, s3, s4);
	// mkt.setHolidays(null);
	//
	// s_marketTable.put(s1, mkt);
	// }
	/**
	 * Add a Market to s_marketTable (at initialization time)
	 * 
	 * @param s1
	 *            java.lang.String - code
	 * @param s2
	 *            java.lang.String - timezone
	 * @param s3
	 *            java.lang.List - vector of close indicator, open time, close
	 *            time
	 */
	// public static void AddMarketTimes(String s1, String s2, List list) {
	//
	// IMarket mkt = Market.Get(s1);
	//
	// HashMap<String, List> hm = null;
	//
	// hm = mkt.getTimes();
	// if (hm == null) {
	// hm = new HashMap<String, List>();
	// mkt.setTimes(hm);
	// }
	// hm.put(s2, list);
	//
	// }
	/**
	 * Add a java.util.SimpleTimeZone
	 * 
	 * @param String
	 *            - identifier of time zone
	 * @param double - hours to be added to UTC to get local time (may be
	 *        fractional)
	 * @return SimpleTimeZone
	 */
	private static SimpleTimeZone addTimeZone(String id, double off) {

		int offset = (new Double(off * 3600000)).intValue();

		SimpleTimeZone tz = new SimpleTimeZone(offset, id);

		s_timeZoneTable.put(id, tz);

		return tz;

	}
}
