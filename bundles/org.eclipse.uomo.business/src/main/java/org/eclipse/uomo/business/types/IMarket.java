package org.eclipse.uomo.business.types;

import java.util.*;

import org.eclipse.uomo.core.IName;
import org.eclipse.uomo.core.ISymbol;
import org.unitsofmeasurement.quantity.Time;
import org.unitsofmeasurement.unit.Unit;

/**
 * Insert the type's description here. Creation date: (9/20/00 2:35:47 PM)
 * @version $Revision$, Change date: ($Date$)
 * @author: paul.morrison, werner.keil
 */
public interface IMarket extends IBDType, IName, ISymbol {
	/**
	 * Insert the method's description here. Creation date: (9/26/00 4:55:31 PM)
	 * 
	 * @return com.sun.java.util.collections.HashMap
	 */
	Map getHolidays();

	/**
	 * Insert the method's description here. Creation date: (9/28/00 9:43:08 AM)
	 * 
	 * @return com.sun.java.util.collections.HashMap
	 */
	Map getReplHolidays();

	/**
	 * Insert the method's description here. Creation date: (9/25/00 10:25:18
	 * AM)
	 * 
	 * @return com.sun.java.util.collections.HashMap
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
	 *            com.sun.java.util.collections.HashMap
	 */
	void setHolidays(Map hm);

	/**
	 * Insert the method's description here. Creation date: (9/26/00 4:52:54 PM)
	 * 
	 * @param hm
	 *            com.sun.java.util.collections.HashMap
	 */
	void setReplHolidays(Map hm);

	/**
	 * Insert the method's description here. Creation date: (9/25/00 10:25:57
	 * AM)
	 * 
	 * @param hm
	 *            com.sun.java.util.collections.HashMap
	 */
	void setTimes(Map<String, List<Time>> hm);
}
