package org.eclipse.uomo.icu.types;

import java.util.*;

import tec.uom.lib.common.function.Nameable;

import tec.uom.lib.common.function.SymbolSupplier;
import javax.measure.quantity.Time;
import javax.measure.Unit;

import com.ibm.icu.util.Holiday;

/**
 * Insert the type's description here. Creation date: (9/20/00 2:35:47 PM)
 * @version $Revision$, Change date: ($Date$)
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public interface IMarket extends IBDType, Nameable, SymbolSupplier {
	/**
	 * Insert the method's description here. Creation date: (9/26/00 4:55:31 PM)
	 * 
	 * @return java.util.HashMap
	 */
	Map<Date, Holiday> getHolidays();

	/**
	 * Insert the method's description here. Creation date: (9/28/00 9:43:08 AM)
	 * 
	 * @return java.util.HashMap
	 */
	Map<Date, Holiday> getReplHolidays();

	/**
	 * Insert the method's description here. Creation date: (9/25/00 10:25:18
	 * AM)
	 * 
	 * @return java.util.HashMap
	 */
	Map<String, List<Time>> getTimes();

	/**
	 * Insert the method's description here. Creation date: (9/25/00 4:36:09 PM)
	 * 
	 * @return java.lang.String
	 */
	String getTimeZone();

	/**
	 * Returns true if market is open for specified date and FI Type
	 * 
	 * @return boolean
	 */
	public boolean isOpen(Date date, String fiType);

	/**
	 * Returns true if market is open for specified time and FI Type
	 * 
	 * @return boolean
	 */
	public boolean isOpen(Unit<Time> ts, String fiType);

	/**
	 * Returns true if market is open right now for specified FIType
	 * 
	 * @return boolean
	 */
	public boolean isOpen(String fiType);

	/**
	 * Insert the method's description here. Creation date: (9/26/00 4:52:54 PM)
	 * 
	 * @param hm
	 *            java.util.HashMap
	 */
	void setHolidays(Map<Date, Holiday> hm);

	/**
	 * Insert the method's description here. Creation date: (9/26/00 4:52:54 PM)
	 * 
	 * @param hm
	 *            java.util.HashMap
	 */
	void setReplHolidays(Map<Date, Holiday> hm);

	/**
	 * Insert the method's description here. Creation date: (9/25/00 10:25:57
	 * AM)
	 * 
	 * @param hm
	 *            java.util.HashMap
	 */
	void setTimes(Map<String, List<Time>> hm);
}
