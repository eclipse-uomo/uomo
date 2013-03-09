/*******************************************************************************
 * Crown Copyright (c) 2006, 2007, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *******************************************************************************/
package org.eclipse.uomo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.eclipse.osgi.util.NLS;
import org.eclipse.uomo.core.UOMoException;
import org.eclipse.uomo.util.internal.Messages;

/**
 * ISO 8601 Dates
 * 
 * HL7 uses a date format which is conforms to the constrained ISO 8601 that is
 * defined in ISO 8824 (ASN.1) under clause 32 (generalized time).
 * 
 * Because of the ubiquity of HL7 interfaces, and the general utility of this
 * time format representation, it's encountered fairly often in healthcare
 * 
 * This Date class repreesents the notion of a date time that may have years,
 * months, days, hours, minutes, seconds, milliseconds and timezones specified.
 * 
 * There is three ways to interact with this date - read and write the component
 * fields directly - read and write the string form using parse() and render() -
 * get and set the time as a java Date object using getAsDate and setAsDate
 * 
 * It's possible to set the component fields to non-sensical values. Use
 * validate() to check whether the values are okay.
 * 
 * Note about parsing:
 * 
 * There is several entry points to the parser. The simplest takes only the
 * actual string to parse. If the string is a proper value according to the
 * specifications referenced above, it will be read in.
 * 
 * In addition, there is another entry point that allows the caller to specify a
 * mask that describes what parts of the date are required, and what is allowed,
 * Consult the ISO_DATE_VALIDATION_FULL constant for an example of how to use
 * the mask. In addition, the caller can specify what to do with the timezone
 * 
 * Finally, there is a static routine that takes the same parameters and returns
 * a date or throws an exception
 * 
 * @deprecated TODO this should be merged with Date/Time aspect of Units
 */
public class Iso8601Date {

	// public constants FIXME change to enum values
	/**
	 * timezone information will be ignored
	 */
	public static final int IGNORE = 0;
	/**
	 * timezone is required
	 */
	public static final int REQUIRED = 1;
	/**
	 * timezone is optional
	 */
	public static final int OPTIONAL = 2;
	/**
	 * timezone is prohibited
	 */
	public static final int PROHIBITED = 3;
	/**
	 * parsing: timezone information will be cleared
	 */
	public static final int CLEAR = 4;
	/**
	 * timezone information will be set to local value
	 */
	public static final int SETLOCAL = 5;
	/**
	 * use timezone information if specified
	 */
	public static final int IF_PRESENT = 6;
	/**
	 * parsing mask for a fully optional date other than the year, which is
	 * required
	 */
	public static final String ISO_DATE_VALIDATION_FULL = Messages.Iso8601Date_ISO_DATE_VALIDATION_FULL;
	/**
	 * just the date, with optional month and day
	 */
	public static final String ISO_DATE_VALIDATION_DATE = Messages.Iso8601Date_ISO_DATE_VALIDATION_DATE;
	/**
	 * default format for writing
	 */
	public static final String DEF_OUTPUT_FORMAT = Messages.Iso8601Date_DEF_OUTPUT_FORMAT;

	// private fields

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	private int milli;
	private int tzHour;
	private int tzMinute;
	private Boolean tzNegative = null; // null means no timezone specified

	// administration
	public Iso8601Date() {
		super();
		reset(true);
	}

	private void reset(boolean timezoneToo) {
		year = -1;
		month = -1;
		day = -1;
		hour = -1;
		minute = -1;
		second = -1;
		milli = -1;
		if (timezoneToo) {
			tzHour = 0;
			tzMinute = 0;
			tzNegative = null;
		}
	}

	/**
	 * 
	 * @return a string describing any errors with the component field values
	 */
	public String validate(boolean checkYearReasonable) {
		StringBuffer s = new StringBuffer();

		// TODO use Units where possible
		boolean content = false;
		content = checkField(s, milli, content, 0, 999,
				Messages.Iso8601Date_MILIS);
		content = checkField(s, second, content, 0, 59,
				Messages.Iso8601Date_SECONDS);
		content = checkField(s, minute, content, 0, 59,
				Messages.Iso8601Date_MINUTES);
		content = checkField(s, hour, content, 0, 23,
				Messages.Iso8601Date_HOURS);
		content = checkField(s, day, content, 1, 31, Messages.Iso8601Date_DAYS);
		content = checkField(s, month, content, 1, 12,
				Messages.Iso8601Date_MONTHS);
		if (checkYearReasonable)
			content = checkField(s, year, content, 1000, 2500,
					Messages.Iso8601Date_YEARS);
		else
			content = checkField(s, year, content, Integer.MIN_VALUE,
					Integer.MAX_VALUE, "years"); //$NON-NLS-1$

		if (!content)
			s.append(Messages.Iso8601Date_NO_VALUES_SET);

		if (year != -1 && month != -1 && day != -1
				&& day > daysForMonth(month, year))
			s.append(Messages.Iso8601Date_THE_DAY
					+ Integer.toString(day)
					+ " is not valid for the month " + Integer.toString(month) + " in the year " + Integer.toString(year) + "\r\n"); //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		if (tzNegative != null) {
			content = checkField(s, tzMinute, content, 0, 59,
					Messages.Iso8601Date_TZ_MINUTES);
			content = checkField(s, tzHour, content, 0, 13,
					Messages.Iso8601Date_TZ_HOURS);
		}
		if (s.length() > 0)
			return s.toString();
		else
			return null;
	};

	private boolean checkField(StringBuffer s, int value, boolean required,
			int min, int max, String desc) {
		if (value == -1) {
			if (required)
				s.append(Messages.Iso8601Date_VALUE_MISSING + desc
						+ " is missing and required in this context"); //$NON-NLS-2$
			return required;
		} else {
			if (value < min || value > max)
				s.append(Messages.Iso8601Date_VALUE_INVALID
						+ desc
						+ " is invalid (is " + Integer.toString(value) + " should be " + Integer.toString(min) + " - " + Integer.toString(max) + ")"); //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			return true;
		}
	}

	// component field access

	/**
	 * @return the year. -1 means not set
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set. -1 means not set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the month. -1 means not set
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set. -1 means not set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the day. -1 means not set
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set. -1 means not set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return the hour. -1 means not set
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @param hour
	 *            the hour to set. -1 means not set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @return the minute. -1 means not set
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @param minute
	 *            the minute to set. -1 means not set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * @return the second. -1 means not set
	 */
	public int getSecond() {
		return second;
	}

	/**
	 * @param second
	 *            the second to set. -1 means not set
	 */
	public void setSecond(int second) {
		this.second = second;
	}

	/**
	 * @return the milliseconds. -1 means not set
	 */
	public int getMilli() {
		return milli;
	}

	/**
	 * @param milli
	 *            the milliseconds to set. -1 means not set
	 */
	public void setMilli(int milli) {
		this.milli = milli;
	}

	/**
	 * @return the timezone Hour. use tzNegative to see whether timezone is set
	 */
	public int getTzHour() {
		return tzHour;
	}

	/**
	 * @param tzHour
	 *            the timezone Hour to set. use tzNegative to see whether
	 *            timezone is set
	 */
	public void setTzHour(int tzHour) {
		this.tzHour = tzHour;
	}

	/**
	 * @return the timezone Minute. use tzNegative to see whether timezone is
	 *         set
	 */
	public int getTzMinute() {
		return tzMinute;
	}

	/**
	 * @param tzMinute
	 *            the timezone Minute to set. use tzNegative to see whether
	 *            timezone is set
	 */
	public void setTzMinute(int tzMinute) {
		this.tzMinute = tzMinute;
	}

	/**
	 * @return the tzNegative - true if the timezone is negative, false it it's
	 *         positive, and null if there's no timezone info
	 */
	public Boolean getTzNegative() {
		return tzNegative;
	}

	/**
	 * @param tzNegative
	 *            the tzNegative to set - true if the timezone is negative,
	 *            false it it's positive, and null if there's no timezone info
	 */
	public void setTzNegative(Boolean tzNegative) {
		this.tzNegative = tzNegative;
	}

	// Utilities
	public Iso8601Date correctForTZ() {
		Iso8601Date result = new Iso8601Date();
		result.year = year;
		result.month = month;
		result.day = day;
		result.hour = hour;
		result.minute = minute;
		result.second = second;
		result.milli = milli;

		if (tzNegative != null) {
			if (tzNegative.booleanValue()) {
				result.addMin(-(tzHour * 60 + tzMinute));
			} else {
				result.addMin((tzHour * 60 + tzMinute));
			}
		}
		return result;
	}

	// TODO: remove if nolonger used
	// private void addSec(int i) {
	// if (i != 0) {
	// if (second == -1)
	// addMin(i / 60);
	// else {
	// second = second + i;
	// int min = 0;
	// while (second < 0) {
	// second = second + 60;
	// min = min - 1;
	// }
	// while (second >= 60) {
	// second = second - 60;
	// min = min + 1;
	// }
	// addMin(min);
	// }
	// }
	// }

	private void addMin(int i) {
		if (i != 0) {
			if (minute == -1)
				addHour(i / 60);
			else {
				minute = minute + i;
				int hr = 0;
				while (minute < 0) {
					minute = minute + 60;
					hr = hr - 1;
				}
				while (minute >= 60) {
					minute = minute - 60;
					hr = hr + 1;
				}
				addHour(hr);
			}
		}
	}

	private void addHour(int i) {
		if (i != 0) {
			if (hour == -1)
				addDay(i / 24);
			else {
				hour = hour + i;
				int d = 0;
				while (hour < 0) {
					hour = hour + 24;
					d = d - 1;
				}
				while (hour >= 24) {
					hour = hour - 24;
					d = d + 1;
				}
				addDay(d);
			}
		}
	}

	private void addDay(int i) {
		if (i != 0 && day != -1) {
			day = day + i;
			while (day < 1 || day > daysForMonth(month, year)) {
				if (day < 1) {
					day = day + daysForMonth(month, year);
					month = month - 1;
					if (month == 0) {
						month = 12;
						year = year - 1;
					}
				}

				if (day > daysForMonth(month, year)) {
					month = month + 1;
					if (month == 13) {
						month = 1;
						year = year + 1;
					}
					day = day - daysForMonth(month, year);
				}
			}
		}
	}

	// Date access

	/**
	 * get the date specified as a Date.
	 * 
	 * @param timezone
	 *            - REQUIRED, IF_PRESENT or IGNORE
	 * @throws OHFException
	 */
	public Date getAsDate(int timezone) throws UOMoException {
		GregorianCalendar cal = new GregorianCalendar();
		cal.clear();
		if (year != -1)
			cal.set(Calendar.YEAR, year);
		if (month != -1)
			cal.set(Calendar.MONTH, month - 1); // Calendar month start with 0
		if (day != -1)
			cal.set(Calendar.DAY_OF_MONTH, day);
		if (hour != -1)
			cal.set(Calendar.HOUR_OF_DAY, hour);
		if (minute != -1)
			cal.set(Calendar.MINUTE, minute);
		if (second != -1)
			cal.set(Calendar.SECOND, second);
		if (milli != -1)
			cal.set(Calendar.MILLISECOND, milli);

		if (timezone == REQUIRED || timezone == IF_PRESENT
				|| timezone == OPTIONAL) {
			if (timezone == REQUIRED && tzNegative == null)
				throw new UOMoException(Messages.Iso8601Date_TZ_NOT_DEFINED);
			if (tzNegative != null) {
				TimeZone tzMsg = TimeZone.getTimeZone(Messages.Iso8601Date_GMT
						+ prepTimezone());
				cal.setTimeZone(tzMsg);
			}
		}
		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 *            the date to set the component fields to
	 * @param timezone
	 *            - what to do about timezone (CLEAR, SETLOCAL or IGNORE)
	 */
	public void setAsDate(Date date, int timezone) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		reset(timezone != IGNORE);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1; // java Month starts from 0, but
												// ours starts from 1
		day = cal.get(Calendar.DAY_OF_MONTH);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
		milli = cal.get(Calendar.MILLISECOND);

		if (timezone == CLEAR) {
			tzHour = 0;
			tzMinute = 0;
			tzNegative = null;
		} else if (timezone == SETLOCAL) {
			TimeZone tzLcl = TimeZone.getDefault();
			int offset = tzLcl.getOffset(date.getTime());
			if (offset < 0) {
				tzNegative = new Boolean(true);
				offset = -offset;
			} else {
				tzNegative = new Boolean(true);
			}
			tzHour = offset / (1000 * 60 * 60);
			tzMinute = (offset - (tzHour * 1000 * 60 * 60)) / (1000 * 60);
		}
	}

	// String format

	/**
	 * get the date as a formatted string
	 * 
	 * @param format
	 *            - the format to use. Must be consistent with the components
	 *            provided. Will be cross-checked
	 * @param timezone
	 *            - what to do about timezone (REQUIRED, IF_PRESENT, PROHIBITED,
	 *            IGNORE)
	 * @return
	 * @throws OHFException
	 *             if cross check fails
	 */
	public String render(String format, boolean checkValues, int timezone)
			throws UOMoException {

		String v = validate(false);
		if (v != null)
			throw new UOMoException(v);

		StringBuffer str = new StringBuffer();
		render(str,
				checkValues,
				format.length() >= 4
						&& Messages.Iso8601Date_yyyy.equalsIgnoreCase(format
								.substring(0, 4)), 4, year, format,
				Messages.Iso8601Date_year, null);
		render(str,
				checkValues,
				format.length() >= 6
						&& Messages.Iso8601Date_mm.equalsIgnoreCase(format
								.substring(4, 6)), 2, month, format,
				Messages.Iso8601Date_month, null);
		render(str,
				checkValues,
				format.length() >= 8
						&& Messages.Iso8601Date_dd.equalsIgnoreCase(format
								.substring(6, 8)), 2, day, format,
				Messages.Iso8601Date_day, null);
		render(str,
				checkValues,
				format.length() >= 10
						&& Messages.Iso8601Date_hh.equalsIgnoreCase(format
								.substring(8, 10)), 2, hour, format,
				Messages.Iso8601Date_hour, null);
		render(str,
				checkValues,
				format.length() >= 12
						&& Messages.Iso8601Date_nn.equalsIgnoreCase(format
								.substring(10, 12)), 2, minute, format,
				Messages.Iso8601Date_minute, null);
		render(str,
				checkValues,
				format.length() >= 14
						&& Messages.Iso8601Date_ss.equalsIgnoreCase(format
								.substring(12, 14)), 2, second, format,
				Messages.Iso8601Date_second, null);

		if (format.length() >= 18
				&& Messages.Iso8601Date_sss.equalsIgnoreCase(format.substring(
						15, 18))) {
			render(str, checkValues, true, 3, milli, format,
					Messages.Iso8601Date_milisecond, Messages.Iso8601Date_DOT);
		} else if (format.length() == 17
				&& "ss".equalsIgnoreCase(format.substring(15, 17))) { //$NON-NLS-1$
			render(str, checkValues, true, 2, milli / 10, format,
					"millisecond", Messages.Iso8601Date_DOT); //$NON-NLS-1$
		} else if (format.length() == 16
				&& Messages.Iso8601Date_s.equalsIgnoreCase(format.substring(15,
						16))) {
			render(str, checkValues, true, 1, milli / 100, format,
					"millisecond", "."); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (timezone == REQUIRED || timezone == IF_PRESENT) {
			if (timezone == REQUIRED && tzNegative == null)
				throw new UOMoException(
						Messages.Iso8601Date_TZ_REQUIRED_BUT_NOT_DEFINED);
			if (tzNegative != null) {
				if (tzNegative.booleanValue())
					str.append('-');
				else
					str.append('+');
				render(str, checkValues, true, 2, tzHour, format,
						Messages.Iso8601Date_tz_hour, null);
				render(str, checkValues, true, 2, tzMinute, format,
						Messages.Iso8601Date_tz_minutes, null);
			}
		}
		if (timezone == PROHIBITED && tzNegative != null)
			throw new UOMoException(
					Messages.Iso8601Date_TZ_PROHIBITED_BUT_DEFINED);

		return str.toString();
	}

	private void render(StringBuffer str, boolean checkValues, boolean render,
			int len, int value, String format, String desc, String prefix)
			throws UOMoException {
		if (render) {
			if (value != -1) {
				if (prefix != null)
					str.append(prefix);
				str.append(StringUtils.leftPad(Integer.toString(value), len,
						Messages.Iso8601Date_ZERO));
			} else if (checkValues)
				throw new UOMoException(
						Messages.Iso8601Date_NO_SUCH_VALUE_PROVIDED
								+ format
								+ "' specifies " + desc + " but no such value has been provided"); //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	/**
	 * Parse the value. no rules other than the base format rules
	 * 
	 * @param value
	 *            - the value to parse
	 */
	public void parse(String value) {
		parse(value, ISO_DATE_VALIDATION_FULL, OPTIONAL);
	}

	/**
	 * Parse the value. check that it conforms to the specified format and
	 * timezone rules
	 * 
	 * @param value
	 *            - the value to parse
	 * @param format
	 *            - the format rules to adhere to
	 * @param timezone
	 *            - rules for timezone (REQUIRED, OPTIONAL, PROHIBITED, IGNORE)
	 * @return - String description of parsing error, or null if no error
	 *         encountered
	 */
	public String parse(String value, String format, int timezone) {
		reset(timezone != IGNORE);
		String[] parts = { value };
		tzNegative = null;

		if (value.indexOf('+') > 0) {
			parts = StringUtils.split(value, "+", 2); //$NON-NLS-1$
			tzNegative = new Boolean(false);
		} else if (value.indexOf('-') > 0) {
			parts = StringUtils.split(value, "-", 2); //$NON-NLS-1$
			tzNegative = new Boolean(true);
		}

		String error = checkSections(parts[0], value, format);
		if (error != null)
			return error;

		if (parts.length > 1 && timezone != IGNORE)
			error = checkSections(parts[1], value, Messages.Iso8601Date_58);
		if (error != null)
			return error;

		if (timezone == REQUIRED) {
			if (parts.length < 2)
				return Messages.Iso8601Date_59;
		} else if (timezone == PROHIBITED && parts.length > 1)
			return Messages.Iso8601Date_60;
		return null;

	}

	/**
	 * Parse the value to a Date. check that it conforms to the specified format
	 * and timezone rules
	 * 
	 * @param value
	 *            - the value to parse
	 * @param format
	 *            - the format rules to adhere to
	 * @param timezone
	 *            - rules for timezone (REQUIRED, OPTIONAL, PROHIBITED, IGNORE)
	 * @return - Date (or throw exception if conversion not successful
	 */
	public static Date parseToDate(String value, String format, int timezone)
			throws UOMoException {
		Iso8601Date d = new Iso8601Date();
		String err = d.parse(value, format, timezone);
		if (err != null)
			throw new UOMoException(Messages.Iso8601Date_61 + value
					+ Messages.Iso8601Date_62 + err);
		return d.getAsDate(timezone);
	}

	public static String renderFromDate(Date date, String format, int timezone)
			throws UOMoException {
		Iso8601Date d = new Iso8601Date();
		d.setAsDate(date, timezone == REQUIRED ? SETLOCAL : CLEAR);
		return d.render(format, true, timezone);
	}

	// private parsing routines

	private boolean inFraction;

	private String checkSections(String content, String whole, String mask) {
		String workingMask = StringUtils.strip(mask, Messages.Iso8601Date_63);
		String[] parts = { "", workingMask }; //$NON-NLS-1$
		boolean first = true;
		inFraction = false;

		do {
			parts = StringUtils.splitPreserveAllTokens(parts[1],
					Messages.Iso8601Date_65, 2);
			String token = parts[0];
			if (token != null) { // support use of [ at first point to make
									// everything optional
				String section = content == null
						|| content.length() < token.length() ? null : content
						.substring(0, token.length()); // sSection =
														// copy(sContent, 1,
														// length(sToken));
				if (section == null) { // if sSection = '' then
					if (!first) {
						if (content != null
								&& content.length() < token.length())
							return Messages.Iso8601Date_66 + content
									+ Messages.Iso8601Date_67 + token;
						else
							return Messages.Iso8601Date_68 + token
									+ Messages.Iso8601Date_69 + mask
									+ Messages.Iso8601Date_70 + whole;
					}
				} else if (section.length() < token.length()) {
					return Messages.Iso8601Date_71 + token
							+ Messages.Iso8601Date_72 + mask
							+ Messages.Iso8601Date_73 + whole
							+ Messages.Iso8601Date_74 + section;
				} else {
					String error = checkSection(token, section);
					if (error != null)
						return error;
					else if (section.length() >= content.length())
						content = null;
					else
						content = content.substring(section.length());
				}
			}
			first = false;
		} while (parts.length > 1 && content != null); // until not result or
														// (sFormat = '') or
														// (sContent = '');
		if (content != null) {
			return Messages.Iso8601Date_75 + content + Messages.Iso8601Date_76
					+ whole + Messages.Iso8601Date_77 + mask;
		} else
			return null;
	}

	private boolean starts(String source, String test) {
		return source == null || source.length() < test.length() ? false
				: source.substring(0, test.length()).equals(test);
	}

	private String checkSection(String token, String content) {
		String error = null;

		if (starts(token, Messages.Iso8601Date_YYYY)) {
			error = checkYear(content.substring(0, 4));
			if (error != null)
				return error;
			token = token.substring(4, token.length());
			content = content.substring(4, content.length());
		}
		;

		if (starts(token, Messages.Iso8601Date_MM)) {
			error = checkMonth(content.substring(0, 2));
			if (error != null)
				return error;
			token = token.substring(2, token.length());
			content = content.substring(2, content.length());
		}
		;

		if (starts(token, Messages.Iso8601Date_DD)) {
			error = checkDay(content.substring(0, 2));
			if (error != null)
				return error;
			token = token.substring(2, token.length());
			content = content.substring(2, content.length());
		}
		;

		if (starts(token, Messages.Iso8601Date_HH)) {
			error = checkHour(content.substring(0, 2), false);
			if (error != null)
				return error;
			token = token.substring(2, token.length());
			content = content.substring(2, content.length());
		}
		;

		if (starts(token, Messages.Iso8601Date_TT)) {
			error = checkHour(content.substring(0, 2), true);
			if (error != null)
				return error;
			token = token.substring(2, token.length());
			content = content.substring(2, content.length());
		}
		;

		if (starts(token, Messages.Iso8601Date_NN)) {
			error = checkMinute(content.substring(0, 2), false);
			if (error != null)
				return error;
			token = token.substring(2, token.length());
			content = content.substring(2, content.length());
		}
		;

		if (starts(token, Messages.Iso8601Date_tt)) {
			error = checkMinute(content.substring(0, 2), true);
			if (error != null)
				return error;
			token = token.substring(2, token.length());
			content = content.substring(2, content.length());
		}
		;

		if (starts(token, Messages.Iso8601Date_SS) && !inFraction) {
			error = checkSecond(content.substring(0, 2));
			if (error != null)
				return error;
			token = token.substring(2, token.length());
			content = content.substring(2, content.length());
		}
		;

		if (starts(token, ".") && !inFraction) { //$NON-NLS-1$
			error = checkDot(content.substring(0, 1));
			if (error != null)
				return error;
			token = token.substring(1, token.length());
			content = content.substring(1, content.length());
		}
		;

		while (starts(token, Messages.Iso8601Date_U) && inFraction) {
			error = checkFraction(content.substring(0, 1));
			if (error != null)
				return error;
			token = token.substring(1, token.length());
			content = content.substring(1, content.length());
		}
		;

		if (!(token == null || token.equals(""))) //$NON-NLS-1$
			return Messages.Iso8601Date_DATE_FRAGMENT_NOT_KNOWN + token
					+ " is not known"; //$NON-NLS-2$
		return null;
	}

	private String checkYear(String value) {
		if (value.length() != 4)
			return "Year Value " + value + " is not 4 digits in length"; //$NON-NLS-1$ //$NON-NLS-2$
		else if (!StringUtils.isNumeric(value))
			return Messages.Iso8601Date_YEAR_VALUE_NOT_NUMERICAL + value
					+ " is not numerical"; //$NON-NLS-2$
		else {
			year = Integer.parseInt(value);
			if (year <= 0)
				return Messages.Iso8601Date_YEAR_VALUE_NEGATIVE_NOT_SUPPORTED
						+ value + ": negative numbers are not supported"; //$NON-NLS-2$
			else
				return null;
		}
	}

	private String checkMonth(String value) {
		if (value.length() != 2)
			return Messages.Iso8601Date_MONTH_VALUE_NOT_2_DIGITS + value
					+ " is not 2 digits in length"; //$NON-NLS-2$
		else if (!StringUtils.isNumeric(value))
			return Messages.Iso8601Date_MONTH_VALUE_NOT_NUMERICAL + value
					+ " is not numerical"; //$NON-NLS-2$
		else {
			month = Integer.parseInt(value);
			if (month <= 0 || month > 12)
				return Messages.Iso8601Date_MONTH_VALUE_MUST_BE_1_12 + value
						+ ": month must be 1 - 12"; //$NON-NLS-2$
			else
				return null;
		}
	}

	private String checkDay(String value) {
		if (value.length() != 2)
			return Messages.Iso8601Date_DAY_VALUE_NOT_2_DIGITS + value
					+ " is not 2 digits in length"; //$NON-NLS-2$
		else if (!StringUtils.isNumeric(value))
			return Messages.Iso8601Date_DAY_VALUE_NOT_NUMERICAL + value
					+ " is not numerical"; //$NON-NLS-2$
		else {
			day = Integer.parseInt(value);
			if (day <= 0)
				return Messages.Iso8601Date_DAY_VALUE_MUST_BE_POSITIVE + value
						+ ": Day must be >= 1"; //$NON-NLS-2$
			else if (month == 0)
				return Messages.Iso8601Date_DAY_VALUE_MONTH_MUST_BE_KNOWN
						+ value + ": Month must be known"; //$NON-NLS-2$
			else if (year == 0)
				return Messages.Iso8601Date_DAY_VALUE_YEAR_MUST_BE_KNOWN
						+ value + ": Year must be known"; //$NON-NLS-2$
			else if (day > daysForMonth(month, year))
				return Messages.Iso8601Date_DAY_VALUE_ILLEGAL_FOR_MONTH
						+ value
						+ ": is illegal for the month " + Integer.toString(month) + "-" + Integer.toString(year); //$NON-NLS-2$ //$NON-NLS-3$
			else
				return null;
		}
	}

	private int daysForMonth(int m, int y) {
		int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (m < 1 || m > 12)
			return 30;
		else if (m == 2 && new GregorianCalendar().isLeapYear(y))
			return 29;
		else
			return daysInMonth[m - 1];
	}

	private String checkHour(String value, boolean inTimezone) {
		if (value.length() != 2)
			return Messages.Iso8601Date_HOUR_VALUE_NOT_2_DIGITS + value
					+ " is not 2 digits in length"; //$NON-NLS-2$
		else if (!StringUtils.isNumeric(value))
			return Messages.Iso8601Date_HOUR_VALUE_NOT_NUMERICAL + value
					+ " is not numerical"; //$NON-NLS-2$
		else if (inTimezone) {
			tzHour = Integer.parseInt(value);
			if (tzHour < 0)
				return Messages.Iso8601Date_TZ_HOUR_VALUE_MUST_BE_0_OR_POSITIVE
						+ value + ": Hour must be >= 0"; //$NON-NLS-2$
			else if (tzHour > 12)
				return Messages.Iso8601Date_TZ_HOUR_VALUE_MUST_BE_12_OR_LESS
						+ value + ": Hour must be <= 12"; //$NON-NLS-2$
		} else {
			hour = Integer.parseInt(value);
			if (hour < 0)
				return Messages.Iso8601Date_HOUR_VALUE_MUST_BE_0_OR_POSITIVE
						+ value + ": Hour must be >= 0"; //$NON-NLS-2$
			else if (inTimezone && hour > 12)
				return Messages.Iso8601Date_HOUR_VALUE_MUST_BE_12_OR_LESS
						+ value + ": Hour must be <= 12"; //$NON-NLS-2$
			else if (hour > 23)
				return Messages.Iso8601Date_TZ_HOUR_VALUE_MUST_BE_23_OR_LESS
						+ value + ": Hour must be <= 23"; //$NON-NLS-2$
		}
		return null;
	}

	private String checkMinute(String value, boolean inTimezone) {
		if (value.length() != 2)
			return Messages.Iso8601Date_MINUTE_VALUE_NOT_2_DIGITS_LONG + value
					+ " is not 2 digits in length"; //$NON-NLS-2$
		else if (!StringUtils.isNumeric(value))
			return Messages.Iso8601Date_MINUTE_VALUE_NOT_NUMERICAL + value
					+ " is not numerical"; //$NON-NLS-2$
		else if (inTimezone) {
			tzMinute = Integer.parseInt(value);
			if (tzMinute != 0 && tzMinute != 30)
				return Messages.Iso8601Date_TZ_MINUTE_VALUE_MUST_BE_0_OR_30
						+ value + ": Minute must be 0 or 30"; //$NON-NLS-2$
		} else {
			minute = Integer.parseInt(value);
			if (minute < 0 || minute > 59)
				return Messages.Iso8601Date_MINUTE_VALUE_MUST_BE_0_AND_59
						+ value + ": Minute must be 0 and 59"; //$NON-NLS-2$
		}
		return null;
	}

	private String checkSecond(String value) {
		if (value.length() != 2)
			return Messages.Iso8601Date_SECOND_VALUE_NOT_2_DIGITS_LONG + value
					+ " is not 2 digits in length"; //$NON-NLS-2$
		else if (!StringUtils.isNumeric(value))
			return Messages.Iso8601Date_SECOND_VALUE_NOT_NUMERICAL + value
					+ " is not numerical"; //$NON-NLS-2$
		else {
			second = Integer.parseInt(value);
			if (second < 0 || second > 59)
				return NLS.bind(Messages.Iso8601Date_SECOND_VALUE_MUST_BE_0_AND_59, value);
			else
				return null;
		}
	}

	private String checkDot(String value) {
		if (value.equals(".")) { //$NON-NLS-1$
			inFraction = true;
			return null;
		} else
			return Messages.Iso8601Date_EXPECTED_DOT;
	}

	private String checkFraction(String value) {
		// TODO - read milliseconds
		if (!StringUtils.isNumeric(value))
			return Messages.Iso8601Date_FRACT_VAL_NOT_NUMERICAL + value
					+ " is not numerical"; //$NON-NLS-2$
		else
			return null;
	}

	private String prepTimezone() {
		return (tzNegative.booleanValue() ? Messages.Iso8601Date_MINUS
				: Messages.Iso8601Date_PLUS)
				+ StringUtils.leftPad(Integer.toString(tzHour), 2, '0')
				+ StringUtils.leftPad(Integer.toString(tzMinute), 2, '0');
	}

}
