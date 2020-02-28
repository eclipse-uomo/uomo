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
package org.eclipse.uomo.icu.types;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

/**
 * This is a helper class supporting the other classes in this package
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */

public abstract class BDTHelper {
	public static enum Operation {
		EQ, // equal
		LT, // less than
		LE, // less than or equal
		GT, // greater than
		GE, // greater than or equal
		NE; // not equal
	}
	
	// Define a MathContext object providing 20 digits
	public static MathContext MATH_CONTEXT = new MathContext(20);

	static HashMap<String, String> s_countryTable = null;

	public static Map<String, String> getCountries() {
		return s_countryTable;
	}

	static {
		s_countryTable = new HashMap<String, String>();

		s_countryTable.put("CA", null);
		s_countryTable.put("US", null);

	}

	public static final String DEFAULT_COUNTRY = "CA"; // Canada is default

	// The following tables will be loaded from a database

	// static HashMap<String, Currency> s_currencyTable = null; // table of
	// currencies
	//
	// static {
	//
	// s_currencyTable = new HashMap<String, Currency>();
	//
	// // Precision of -1 means undefined precision
	//
	// AddCurrency("CAD", 2); // Canadian dollars
	//
	// AddCurrency("USD", 2); // US dollars
	//
	// AddCurrency("GBP", 2); // Great Britain Pounds
	//
	// AddCurrency("FRF", 2); // French Francs
	//
	// AddCurrency("ITL", 0); // Italian Lire
	//
	// AddCurrency("BHD", 3); // Bahraini Dinar
	//
	// AddCurrency("XAU", -1); // Euro Gold
	//
	// }

	static HashMap<String, IMarket> s_marketTable = new HashMap<String, IMarket>(); // table
																					// of
	/**
	 * Dummy constructor for BDTHelper - ensures no instance can be built
	 */

	private BDTHelper() {
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
	 * Use value (BigDecimal) to calculate vulgar fraction, and set fields. This
	 * logic uses the actual number of digits to the right of the decimal point.
	 * Thus .50 will be expressed as 2/4, rather than 1/2.
	 * 
	 * @return int[]
	 * @param bd
	 *            java.math.BigDecimal
	 * @throws BDTypeException
	 */
	static int[] calcVulgarFrac(BigDecimal bd) throws BDTypeException {

		int[] parts = { 0, 0, 0 };
		parts[0] = bd.intValue();
		BigDecimal temp = new BigDecimal(parts[0]);
		BigDecimal temp2 = bd.subtract(temp, MATH_CONTEXT);
		String s = temp2.toString();
		int len = s.length() - s.indexOf('.') - 1; // determine no. of places of
													// decimals
		BigDecimal two = new BigDecimal(2);
		BigDecimal pow = two.pow(len); // we now have correct power of 2
		parts[2] = pow.intValue();
		pow = pow.multiply(temp2, MATH_CONTEXT);
		try {
			parts[1] = pow.intValueExact();
		} catch (ArithmeticException e) {
			parts[2] = 0; // reset denominator, so value will display as decimal
			throw new BDTypeException(
					"Fractional value not divisible by power of 2: " + bd);
		}
		;
		return parts;

	}

	/**
	 * Generalized compare for exchange rates
	 * 
	 * @return boolean
	 * @param x
	 *            ExchRate - first exchange rate
	 * @param y
	 *            ExchRate - second exchange rate
	 * @param op
	 *            int - desired relationship (less than, equal, not equal, etc.)
	 * @throws BDTypeException
	 */
	// static boolean Comp(ExchangeRate x, ExchangeRate y, int op) throws
	// BDTypeException{
	// if (x.m_sourceCurrency != y.m_sourceCurrency)
	// throw new BDTypeException("Source currency mismatch: " +
	// x.m_sourceCurrency.getAbbrev() + ", " + y.m_sourceCurrency.getAbbrev());
	// if (x.m_targetCurrency != y.m_targetCurrency)
	// throw new BDTypeException("Target currency mismatch: " +
	// x.m_targetCurrency.getAbbrev() + ", " + y.m_targetCurrency.getAbbrev());
	// switch (op) {
	// case EQ: return (0 == x.m_value.compareTo(y.m_value));
	// case NE: return (0 != x.m_value.compareTo(y.m_value));
	// case LT: return (-1 == x.m_value.compareTo(y.m_value));
	// case LE: return (1 != x.m_value.compareTo(y.m_value));
	// case GT: return (1 == x.m_value.compareTo(y.m_value));
	// case GE: return (-1 != x.m_value.compareTo(y.m_value));
	// default: throw new BDTypeException("Unknown compare operator: " + op);
	// }
	//
	// }
	/**
	 * Generalized compare for monetary amounts
	 * 
	 * @return boolean
	 * @param x
	 *            Monetary - first monetary amount
	 * @param y
	 *            Monetary - second monetary amount
	 * @param op
	 *            int - desired relationship (less than, equal, not equal, etc.)
	 * @throws BDTypeException
	 */
	// static boolean Comp(Money x, Money y, int op) throws BDTypeException{
	// if (x.m_currency != y.m_currency)
	// throw new BDTypeException("Currency mismatch: " +
	// x.getCurrAbbr() + ", " + y.getCurrAbbr());
	// switch (op) {
	// case EQ: return (0 == x.m_value.compareTo(y.m_value));
	// case NE: return (0 != x.m_value.compareTo(y.m_value));
	// case LT: return (-1 == x.m_value.compareTo(y.m_value));
	// case LE: return (1 != x.m_value.compareTo(y.m_value));
	// case GT: return (1 == x.m_value.compareTo(y.m_value));
	// case GE: return (-1 != x.m_value.compareTo(y.m_value));
	// default: throw new BDTypeException("Unknown compare operator: " + op);
	// }
	// }
	/**
	 * Generalized compare for Percents
	 * 
	 * @return boolean
	 * @param x
	 *            Percent - first Percent
	 * @param y
	 *            Percent - second Percent
	 * @param op
	 *            int - desired relationship (less than, equal, not equal, etc.)
	 * @throws BDTypeException
	 */
	public static boolean comp(PercentAmount x, PercentAmount y, Operation op)
			throws BDTypeException {

		switch (op) {
		case EQ:
			return (0 == x.compareTo(y));
		case NE:
			return (0 != x.compareTo(y));
		case LT:
			return (-1 == x.compareTo(y));
		case LE:
			return (1 != x.compareTo(y));
		case GT:
			return (1 == x.compareTo(y));
		case GE:
			return (-1 != x.compareTo(y));
		default:
			throw new BDTypeException("Unknown compare operator: " + op);
		}

	}

	/**
	 * Generalized compare for BigDecimal
	 * 
	 * @return boolean
	 * @param x
	 *            java.math.BigDecimal - first BigDecimal
	 * @param y
	 *            java.math.BigDecimal - second BigDecimal
	 * @param op
	 *            int - desired relationship (less than, equal, not equal, etc.)
	 * @throws BDTypeException
	 */

	static boolean comp(BigDecimal x, BigDecimal y, Operation op)
			throws BDTypeException {
		switch (op) {
		case EQ:
			return (0 == x.compareTo(y));
		case NE:
			return (0 != x.compareTo(y));
		case LT:
			return (-1 == x.compareTo(y));
		case LE:
			return (1 != x.compareTo(y));
		case GT:
			return (1 == x.compareTo(y));
		case GE:
			return (-1 != x.compareTo(y));
		default:
			throw new BDTypeException("Unknown compare operator: " + op);
		}

	}

	/**
	 * Create an IPrice (MPrice or PCPrice) from a string containing
	 * code/currency, value and 'P', 'V' or ' ' - all concatenated together. If
	 * final character is absent, decide based on first 3 chars (code/currency).
	 * 
	 * @return IPrice
	 * @param s
	 *            java.lang.String
	 * @throws BDTypeException
	 */
	// public static IPrice CreatePrice(String s) throws BDTypeException {
	// String c = s.substring(0, 3);
	// String v = s.substring(3);
	// int len = v.length();
	//
	// String last = v.substring(len - 1, len);
	//
	// if (last.equals("P") || !last.equals(" ") && !last.equals("V") &&
	// PCPrice.s_types.containsKey(c))
	// return new PCPrice(c + v);
	// else
	// return new MPrice(c + v);
	//
	//
	//
	// }
	/**
	 * Insert the method's description here. Creation date: (9/27/00 6:02:51 PM)
	 * 
	 * @return com.sun.java.util.collections.HashMap
	 */
	public static Map<String, IMarket> getMarkets() {
		return s_marketTable;
	}
}
