/**
 * Copyright (c) 2012, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.types;

import org.eclipse.uomo.units.AbstractSystemOfUnits;
import org.eclipse.uomo.units.SI;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.unit.SystemOfUnits;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.0.1
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Obsolete_Polish_units_of_measurement">Wikipedia:
 *      Obsolete Polish Uits of Measurement</a>
 * @deprecated
 */
public class ObsoletePolish extends AbstractSystemOfUnits {

	/** The basic unit of length - the ell or łokieć in Polish - was set to 0.5955 metres 
	 *  
	 * */
	public static final  Unit<Length> ELL = SI.METRE.multiply(0.5955d);
	
	@Override
	public String getName() {
		return ObsoletePolish.class.getSimpleName();
	}

    /**
     * Default constructor (prevents this class from being instantiated).
     */
    private ObsoletePolish() {
    }
	
	/**
	 * The singleton instance of {@code Seismic}.
	 */
	private static final ObsoletePolish INSTANCE = new ObsoletePolish();

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the Seismic system instance.
	 */
	public static final SystemOfUnits getInstance() {
		return INSTANCE;
	}	
}
