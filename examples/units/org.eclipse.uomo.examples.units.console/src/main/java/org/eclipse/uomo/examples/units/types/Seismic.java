/**
 * Copyright (c) 2005, 2017, Werner Keil and others.
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
import org.eclipse.uomo.units.impl.system.SI;
import javax.measure.quantity.Energy;
import javax.measure.spi.SystemOfUnits;
import javax.measure.Unit;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.4.1
 */
public class Seismic extends AbstractSystemOfUnits {

	// Richter scale.
	public static final  Unit<Energy> RICHTER_MAGNITUDE = (SI.JOULE.multiply(1.5d)).shift(4.4d);
	// e = 10 ^ (4.4 +3 M / 2)
		//SI.JOULE.divide(Math.pow(10, 4.4d)).transform(new LogConverter());
	
	@Override
	public String getName() {
		return Seismic.class.getSimpleName();
	}

    /**
     * Default constructor (prevents this class from being instantiated).
     */
    private Seismic() {
    }
	
	/**
	 * The singleton instance of {@code Seismic}.
	 */
	private static final Seismic INSTANCE = new Seismic();

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the Seismic system instance.
	 */
	public static final SystemOfUnits getInstance() {
		return INSTANCE;
	}	
}
