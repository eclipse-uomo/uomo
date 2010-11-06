/*******************************************************************************
 * Crown Copyright (c) 2006, 2010, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *******************************************************************************/
package org.eclipse.uomo.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Grahame Grieve
 * @author Werner Keil
 *
 * TODO add method to return UOM TimeUnit, too
 */
public final class XMLDateUtil {
	
	/** Utility class, don't instanciate */
	private XMLDateUtil() {}
	
	/** 
	 * Returns a java.util.Date for a given xml xs:dateTime, ignoring the timezone. Needs to be
	 * updated to include the numerous xs:dateTime year, timezone, etc. specifications.
	 * 
	 * @param xmlDate Date string formatted as xs:dateTime
	 * @return java.util.Date for given xmlDate
	 * @throws ParseException
	 */
	public static Date parseXMLDate(String xmlDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return formatter.parse(xmlDate);
	}
}
