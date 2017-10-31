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
package org.eclipse.uomo.business.types.impl;

import java.util.*;

import org.eclipse.osgi.util.NLS;
import org.eclipse.uomo.business.internal.Messages;
import org.eclipse.uomo.business.types.BDTHelper;
import org.eclipse.uomo.business.types.BDTypeException;
import org.eclipse.uomo.business.types.IMarket;
import org.unitsofmeasurement.quantity.Time;
import javax.measure.Unit;

import com.ibm.icu.util.Holiday;

/**
 * Market object - object is mutable, but only the holiday table for a market
 * will be changed on a regular basis (plus the closed indicators); open and
 * close times will only be changed at BDT Load time.
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.4 ($Revision$), $Date$
 */
public class Market {
	static class MarketImpl implements IMarket {

		/**
		 * Returns true if market is open for specified FIType
		 * 
		 * @return boolean
		 */
		String m_name;
		String m_symbol;
		String m_code = null; // this must be the Reuters code

		String m_timeZone = null; // alpha TimeZone ID - can be used by
									// TimeStamp methods)
		String m_openTime = "09:30"; // default values //$NON-NLS-1$
		String m_closeTime = "16:00"; //     same //$NON-NLS-1$
		String m_countryCode = null;
		String m_quoteCurrency = null;
		Map<String, List<Time>> m_times = null; // HashMap of open and close times
												// - key is fiType

		Map<Date, Holiday> m_holidays = null; // list of holidays - keyed on date
		Map<Date, Holiday> m_replHolidays = new HashMap<Date, Holiday>(); // used to build new holiday
												// list
		// TODO introdude ICU4J Holiday type and related framework
		
		/**
		 * Returns true if market is open right now for specified FIType
		 * 
		 * @return boolean
		 */

		public boolean isOpen(String fiType) {

			// TimeStamp ts = new TimeStamp(); // set timestamp to right now!
			// return isOpen(ts, fiType);

			return false;

		}

		/**
		 * Returns true if market is open at specified time and for specified
		 * FIType
		 * 
		 * @return boolean
		 */

		public boolean isOpen(Unit<Time> ts, String fiType) {

			if (m_code.equals("M")) { //$NON-NLS-1$
				System.err
						.println("Montreal Exchange forced closed for testing!"); //$NON-NLS-1$
				System.err.println("Remember to correct code later!"); //$NON-NLS-1$
				return false;
			}

			String open = m_openTime;
			String close = m_closeTime;
			boolean closeInd = false;
			if (m_times != null) {
				List list = (List) m_times.get(fiType);
				closeInd = list.get(0).equals("1"); //$NON-NLS-1$
				open = (String) list.get(1);
				close = (String) list.get(2);
			}

			if (closeInd)
				return false;

			// try {String nowInTZ = ts.formatWithZone(m_timeZone); // now in
			// market time zone
			//
			// TimeZone tz = TimeTz.GetTimeZone(m_timeZone); // get from
			// BDTHelper table
			// String date = nowInTZ.substring(0,8);
			//
			// Calendar cal = new GregorianCalendar(tz); // needed to obtain day
			// of week
			// cal.setTime(ts);
			// int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			// HashMap hm = getHolidays();
			// if (dayOfWeek == Calendar.SATURDAY || dayOfWeek ==
			// Calendar.SUNDAY ||
			// hm != null && null != hm.get(date)) // or is date in list of
			// holidays?
			// return false;
			//
			//
			// TimeStamp tsStart = new TimeStamp(date + 'T' + open + '!' +
			// m_timeZone);
			// TimeStamp tsEnd = new TimeStamp(date + 'T' + close + '!' +
			// m_timeZone);
			//
			// if (ts.before(tsStart) || ts.after(tsEnd))
			// return false;
			//
			// }
			// catch (BDTypeException ex) {
			// System.err.println("Date error: " + ex);
			// }
			//
			return true;

		}

		/**
		 * Returns true if market is open for specified Date and FI Type
		 * (partial dates will be treated as not open); times will not be
		 * checked at all
		 * 
		 * @return boolean
		 */
		public boolean isOpen(Date date, String fiType) {

			if (m_code.equals("M")) { //$NON-NLS-1$
				System.err
						.println("Montreal Exchange forced closed for testing!"); //$NON-NLS-1$
				System.err.println("Remember to correct code later!"); //$NON-NLS-1$
				return false;
			}

			boolean closeInd = false;
			if (m_times != null) {
				List list = (List) m_times.get(fiType);
				closeInd = list.get(0).equals("1"); //$NON-NLS-1$
			}

			if (closeInd)
				return false;

			// try {TimeStamp timeWithinDate = new TimeStamp(date + "T12:00!" +
			// m_timeZone);
			//
			// TimeZone tz = TimeTz.GetTimeZone(m_timeZone); // get from
			// BDTHelper table
			// Calendar cal = new GregorianCalendar(tz); // needed to obtain day
			// of week
			// cal.setTime(timeWithinDate);
			// int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			// HashMap hm = getHolidays();
			// if (dayOfWeek == Calendar.SATURDAY || dayOfWeek ==
			// Calendar.SUNDAY ||
			// hm != null && null != hm.get(date)) // or is date in list of
			// holidays?
			// return false;
			//
			//
			// }
			// catch (BDTypeException ex) {
			// System.err.println("Date error: " + ex);
			// }

			return true;

		}

		/**
		 * Get HashMap of open/close times
		 */

		public Map<String, List<Time>> getTimes() {
			return m_times;
		}

		/**
		 * Set HashMap with open/close times
		 */

		public void setTimes(HashMap<String, List<Time>> hm) {
			m_times = hm;
		}

		/**
		 * Insert the method's description here. Creation date: (9/20/00 2:49:20
		 * PM)
		 * 
		 * @return java.lang.String
		 */
		public String serialize() {

			String str = m_code + ';' + m_timeZone + ';' + m_countryCode + ';'
					+ m_quoteCurrency + ';' + '{';
			Iterator iter = m_times.keySet().iterator();
			boolean first = true;
			while (iter.hasNext()) {
				if (!first)
					str = str + ';';
				first = false;
				String key = (String) iter.next();
				List vec = (List) m_times.get(key);
				str = str
						+ key
						+ "={" + vec.get(0) + ';' + vec.get(1) + ';' + vec.get(2) + '}'; //$NON-NLS-1$
			}
			str = str + "}{"; //$NON-NLS-1$

			iter = m_holidays.keySet().iterator();
			first = true;
			while (iter.hasNext()) {
				if (!first)
					str = str + ';';
				first = false;
				Date d = (Date) iter.next();
				str = str + d.toString();
			}
			str = str + '}';
			return str;

		}

		MarketImpl(String s1, String s2, String s3, String s4) {
			m_code = s1;
			m_timeZone = s2;
			m_countryCode = s3;
			m_quoteCurrency = s4;
		}

		public String getTimeZone() {
			return m_timeZone;
		}

		public synchronized Map<Date, Holiday> getHolidays() {
			return m_holidays;
		}

		public synchronized void setHolidays(Map<Date, Holiday> hm) {
			m_holidays = hm;
		}

		public Map<Date, Holiday> getReplHolidays() {
			return m_replHolidays;
		}

		public void setReplHolidays(Map<Date, Holiday> hm) {
			m_replHolidays = hm;
		}

		public void setTimes(Map<String, List<Time>> hm) {
			m_times = hm;
		}

		public String getName() {
			return m_name;
		}

		public String getSymbol() {
			return m_symbol;
		}

	}

	/**
	 * Insert the method's description here. Creation date: (9/22/00 4:09:05 PM)
	 * 
	 * @return com.jpmorrsn.jbdtypes.IMarket
	 * @param s1
	 *            java.lang.String
	 * @param s2
	 *            java.lang.String
	 * @param s3
	 *            java.lang.String
	 * @param s4
	 *            java.lang.String
	 */
	static IMarket createMarket(String s1, String s2, String s3, String s4) {
		return new MarketImpl(s1, s2, s3, s4);
	}

	/**
	 * Return an IMarket object given the name - return null if string empty
	 * 
	 * @return org.eclipse.uomo.business.types.IMarket
	 * @param s
	 *            java.lang.String
	 */
	public static IMarket get(String s) throws BDTypeException {

		if (s.equals("")) //$NON-NLS-1$
			return null;
		else {
			IMarket mkt = BDTHelper.getMarkets().get(s);
			if (mkt == null) {
				// System.err.println("Invalid market code: " + s);
				throw new BDTypeException(NLS.bind(
						Messages.Market_invalid_code, s)); //$NON-NLS-1$
			}
			return mkt;
		}
	}
}
