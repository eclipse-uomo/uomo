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

import static org.eclipse.uomo.icu.impl.DataHelper.BDT_DELIM;

import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.sql.Timestamp;

import org.eclipse.uomo.core.UOMoRuntimeException;
import org.eclipse.uomo.icu.types.IBasicType;

/**
 * Timestamp extends java.util.Date, which goes down to the millisecond
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public class TimeStamp extends java.util.Date implements IBasicType {
	final static long serialVersionUID = 362498820763181265L;

	/**
	 * TimeStamp constructor - sets a TimeStamp to the current time
	 */
	public TimeStamp() {
		super((TimeStamp.BuildTimeStamp()).getTime());

	}

	/**
	 * TimeStamp constructor with millisecs (long)
	 */
	public TimeStamp(long m) {
		super(m);

	}

	/**
	 * TimeStamp constructor with String. buildTimeStamp returns a TimeStamp
	 * object, which can't be used directly, so we use getTime
	 * 
	 * @throws UOMoRuntimeException
	 */
	public TimeStamp(String s) throws UOMoRuntimeException {
		super((TimeStamp.BuildTimeStamp(s)).getTime());
	}

	/**
	 * TimeStamp constructor with a java.sql.Timestamp buildTimeStamp returns a
	 * TimeStamp object, which can't be used directly, so we use
	 * getTime
	 * 
	 * @throws UOMoRuntimeException
	 */
	public TimeStamp(Timestamp ts) throws UOMoRuntimeException {
		super((TimeStamp.BuildTimeStamp(ts)).getTime());
	}

	/**
	 * Create a TimeStamp object using current time
	 * 
	 * @return TimeStamp
	 */
	static TimeStamp BuildTimeStamp() {

		Calendar aGregCal = new GregorianCalendar();

		java.util.Date date = aGregCal.getTime();
		return new TimeStamp(date.getTime());
	}

	/**
	 * Create an (non-Java.util) TimeStamp using a String. This has format
	 * ccyymmddThh:mm:ssttt!zone;calendar;era where zone is an alpha time zone
	 * name known to UOMo; calendar and era are optional. The time from :ss
	 * is also optional. Instead of !zone, you may instead use Ahh:mm, where A
	 * is + or -; in this case, no daylight savings time logic is available. If
	 * adjustment and time zone are both missing, treat as 0, i.e. time is UTC
	 * If an adjustment is present, the sign is as normally used to identify the
	 * time zone - i.e. the offset needed to convert UTC to local time, even
	 * though the first part is local time. This means that, to compute the UTC,
	 * the sign must be reversed - i.e. local time 08:00 am in the EDT zone will
	 * be represented as 08:00-04:00, so that UTC is computed by adding the two
	 * values together. Calendar and era will be ignored for now.
	 * 
	 * @return TimeStamp
	 * @param s
	 *            java.lang.String
	 * @throws UOMoRuntimeException
	 */
	static TimeStamp BuildTimeStamp(String s) throws UOMoRuntimeException {
		String str;
		int sp = s.indexOf(BDT_DELIM); // strip off calendar and/or era
		if (sp == -1)
			str = s;
		else
			str = s.substring(0, sp);

		String adj = null;
		TimeZone tz = null;

		sp = str.indexOf('!');

		if (sp > -1) {
			String zoneString = str.substring(sp + 1);
			tz = TimeTz.GetTimeZone(zoneString);
			if (tz == null)
				throw new UOMoRuntimeException("Time Zone not found: "
						+ zoneString);
			str = str.substring(0, sp);
		}

		else {
			sp = str.indexOf('+');
			if (sp == -1)
				sp = str.indexOf('-');
			if (sp > -1) {
				adj = str.substring(sp);
				str = str.substring(0, sp);
			}
		}

		if (str.length() < 14)
			throw new UOMoRuntimeException("Timestamp too short: " + s);

		if (str.charAt(8) != 'T' || str.charAt(11) != ':')
			throw new UOMoRuntimeException("Invalid TimeStamp: " + s);

		String secs = "00000";

		if (str.length() > 14) {
			if (str.charAt(14) != ':')
				throw new UOMoRuntimeException("Invalid TimeStamp: " + s);
			if (str.length() != 17 && str.length() != 20)
				throw new UOMoRuntimeException("Invalid TimeStamp: " + s);
			secs = str.substring(15) + secs;
			secs = secs.substring(0, 5);
		}
		TimeZone zone = TimeZone.getTimeZone("UTC");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mmssSSS");
		formatter.setTimeZone(zone);

		String dateString = str.substring(0, 8) + str.substring(9, 14) + secs;

		ParsePosition pos = new ParsePosition(0);
		java.util.Date date = formatter.parse(dateString, pos);
		TimeStamp ts = new TimeStamp(date.getTime());

		long millis = 0;

		if (tz != null) {
			millis = ts.getTime();
			millis -= tz.getRawOffset();
			ts.setTime(millis);
			if (tz.inDaylightTime(ts))
				millis -= 3600000;
			ts.setTime(millis);
		}

		if (adj != null) {
			int hrs;
			int mins = 0;

			if (adj.length() == 3)
				hrs = Integer.parseInt(adj.substring(1));
			else {
				if (adj.charAt(3) != ':' || adj.length() != 6)
					throw new UOMoRuntimeException(
							"Invalid TimeStamp adjustment: " + s);
				hrs = Integer.parseInt(adj.substring(1, 3));
				mins = Integer.parseInt(adj.substring(4, 6));
			}

			if (adj.charAt(0) == '+') {
				hrs = -hrs;
				mins = -mins;
			} else if (adj.charAt(0) != '-')
				throw new UOMoRuntimeException(
						"Invalid TimeStamp adjustment sign: " + s);

			millis = ts.getTime();

			millis += (3600 * hrs + 60 * mins) * 1000;

			ts.setTime(millis);
		}

		return ts;
	}

	/**
	 * Create an TimeStamp using a java.sql.Timestamp. This differs
	 * from an UOMo TimeStamp in that it goes down to the nanosecond. We
	 * will therefore lose fractional milliseconds.
	 * 
	 * @return TimeStamp
	 * @param ts
	 *            java.sql.Timestamp
	 * @throws UOMoRuntimeException
	 */
	static TimeStamp BuildTimeStamp(java.sql.Timestamp ts)
			throws UOMoRuntimeException {
		long millis = ts.getTime(); // gets integral number of seconds
		millis += ts.getNanos() / 1000000;
		return new TimeStamp(millis);
	}

	/**
	 * Convert this TimeStamp to a java.sql.Timestamp
	 * 
	 * @return java.sql.Timestamp
	 */
	public Timestamp convertToSQL() {

		long millis = getTime() % 1000;
		java.sql.Timestamp ts = new Timestamp(this.getTime() - millis);
		ts.setNanos((new Long(millis * 1000000)).intValue());
		return ts;

	}

	/**
	 * Format a (non-Java.util) TimeStamp object with specified alpha zone or
	 * numeric (+/-) zone
	 * 
	 * @return java.lang.String
	 * @throws UOMoRuntimeException
	 */
	public String formatWithZone(String s) throws UOMoRuntimeException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ssSSS");
		TimeZone zone = TimeZone.getTimeZone("UTC");

		formatter.setTimeZone(zone);

		long millis = getTime();

		TimeStamp ts = new TimeStamp(millis);

		String z = s;

		if (s.charAt(0) == '+' || s.charAt(0) == '-') {
			int hrs;
			int mins = 0;

			if (s.length() == 3)
				hrs = Integer.parseInt(s.substring(1));
			else {
				if (s.charAt(3) != ':' || s.length() != 6)
					throw new UOMoRuntimeException(
							"Invalid TimeStamp adjustment: " + s);
				hrs = Integer.parseInt(s.substring(1, 3));
				mins = Integer.parseInt(s.substring(4, 6));
			}

			if (s.charAt(0) == '-') {
				hrs = -hrs;
				mins = -mins;
			}
			millis += (hrs * 3600 + mins * 60) * 1000;
			ts.setTime(millis);
		} else {
			TimeZone tz = TimeTz.GetTimeZone(s);
			if (tz == null)
				throw new UOMoRuntimeException("Time Zone not found: " + s);
			millis += tz.getRawOffset();
			ts.setTime(millis);
			if (tz.inDaylightTime(ts))
				ts.setTime(millis + 3600000);
			z = '!' + s;
		}

		String str = formatter.format(ts);
		str = str.substring(0, 8) + 'T' + str.substring(8) + z;

		return str;

	}

	/**
	 * Display a (non-Java.util) TimeStamp object as a 26-byte string
	 * 
	 * @return java.lang.String
	 */
	public String serialize() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ssSSS");
		TimeZone zone = TimeZone.getTimeZone("UTC");

		formatter.setTimeZone(zone);

		String str = formatter.format(this);
		str = str.substring(0, 8) + 'T' + str.substring(8) + "+00:00";
		return str;

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
