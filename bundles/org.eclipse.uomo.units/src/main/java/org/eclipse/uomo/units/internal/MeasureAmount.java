/**
 * Copyright (c) 2005, 2014, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units.internal;

import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;

/**
 * Wrapper for ICU4J Measure type
 * @author Werner Keil
 *
 */
public class MeasureAmount extends Measure {

	protected MeasureAmount(Number number, MeasureUnit unit) {
		super(number, unit);
	}
	
	/**
	 * Returns a new <type>MeasureAmount</type> with the given number and unit.
	 * @param number the given number
	 * @param unit the given unit
	 * @return a new instance of <type>MeasureAmount</type>
	 */
	public static Measure of(Number number, MeasureUnit unit) {
		return new MeasureAmount(number, unit);
	}
}
