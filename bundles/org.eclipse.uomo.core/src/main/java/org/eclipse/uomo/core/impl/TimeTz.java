package org.eclipse.uomo.core.impl;

import java.util.TimeZone;

import org.eclipse.uomo.core.IBasicType;
import org.eclipse.uomo.core.UOMoRuntimeException;

/**
 * TimeTz is mostly needed in conjunction with dates, so it is held as a String
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
class TimeTz implements IBasicType {
	String m_ttz;

	/**
	 * TimeTz constructor with String. Format is same as time and adjustment
	 * part of TimeStamp string.
	 */
	public TimeTz(String s) {
		m_ttz = s;
	}

	/**
	 * Build a TimeStamp using specified Date object
	 * 
	 * @return com.jpmorrsn.jbdtypes.TimeStamp
	 * @param d
	 *            com.jpmorrsn.jbdtypes.Date
	 * @throws BDTypeException
	 */
	TimeStamp buildTimeStamp(Date d) throws UOMoRuntimeException {

		return new TimeStamp(d.serialize() + 'T' + m_ttz);

	}

	/**
	 * Find a TimeZone (alpha only) in static TimeZone table
	 * 
	 * @return java.util.SimpleTimeZone
	 * @param s
	 *            java.lang.String
	 */
	public static TimeZone GetTimeZone(String s) {
		return (TimeZone) DataHelper.s_timeZoneTable.get(s);
	}

	/**
	 * Display a TimeTz object
	 * 
	 * @return java.lang.String
	 */
	public String serialize() {

		return m_ttz;

	}

	/**
	 * Create a String from this object
	 * 
	 * @return java.lang.String
	 */
	public String toString() {
		return serialize();
	}
}
