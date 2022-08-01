package org.eclipse.uomo.business.types.impl;

import static org.eclipse.uomo.business.types.impl.DataHelper.BDT_DELIM;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.uomo.business.types.IBasicType;
import org.eclipse.uomo.core.UOMoRuntimeException;

/**
 * Define (non-Java.util) Date class
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public class BDate implements IBasicType {
	String m_date;

	/**
	 * Constructor for date with no parameter - use current date in this locale
	 * - dangerous!
	 */

	public BDate() {

		Calendar cal = new GregorianCalendar();

		java.util.Date date = cal.getTime();

		TimeStamp ts = new TimeStamp(date.getTime());
		m_date = ts.serialize().substring(0, 8);

	}

	/**
	 * Constructor for date with date string (yyyymmdd); optional calendar and
	 * era are being ignored for now
	 */

	public BDate(String s) {
		super();
		m_date = BuildDate(s);
	}

	/**
	 * Constructor for jbdtypes Date using java.sql.Date
	 */

	public BDate(java.sql.Date dt) {
		long millis = dt.getTime();
		TimeStamp ts = new TimeStamp(millis);

		m_date = BuildDate(ts.serialize().substring(0, 8));
	}

	/**
	 * Return true if <code>this</code> date is after specified date
	 * 
	 * @return boolean
	 * @param d
	 *            com.jpmorrsn.jbdtypes.Date
	 */
	public boolean after(BDate d) {
		return Integer.parseInt(this.m_date) > Integer.parseInt(d.m_date);
	}

	/**
	 * Return true if <code>this</code> date is before specified date
	 * 
	 * @return boolean
	 * @param d
	 *            com.jpmorrsn.jbdtypes.Date
	 */
	public boolean before(BDate d) {
		return Integer.parseInt(this.m_date) < Integer.parseInt(d.m_date);
	}

	/**
	 * Strip off calendar and era (if any)
	 * 
	 * @return java.lang.String
	 * @param s
	 *            java.lang.String
	 */
	static String BuildDate(String s) {
		int sp = s.indexOf(BDT_DELIM);
		if (sp == -1)
			return s;
		else
			return s.substring(0, sp);
	}

	/**
	 * Build a TimeStamp using specified TimeTz object
	 * 
	 * @return com.jpmorrsn.jbdtypes.TimeStamp
	 * @param t
	 *            com.jpmorrsn.jbdtypes.TimeTz
	 * @throws UOMoRuntimeException
	 */
	public TimeStamp buildTimeStamp(TimeTz t) throws UOMoRuntimeException {

		return new TimeStamp(m_date + 'T' + t.serialize());
	}

	/**
	 * Convert this Date to a java.sql.Date
	 * 
	 * @return java.sql.Timestamp
	 */
	public java.sql.Date convertToSQL() {

		TimeStamp ts = new TimeStamp(m_date + "T00:00");
		return new java.sql.Date(ts.getTime());

	}

	/**
	 * Return true if <code>this</code> date is same as specified date
	 * 
	 * @return boolean
	 * @param d
	 *            com.jpmorrsn.jbdtypes.Date
	 */
	public boolean equals(BDate d) {
		return Integer.parseInt(this.m_date) == Integer.parseInt(d.m_date);
	}

	/**
	 * Display a (non-Java.util) jbdtypes Date object as an 8-byte string
	 * 
	 * @return java.lang.String
	 */
	public String serialize() {

		return m_date;

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
