/**
 * Copyright (c) 2006, 2011, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.business.money;

import org.eclipse.uomo.business.types.IMoney;
import org.eclipse.uomo.units.AbstractSystemOfUnits;
import org.unitsofmeasurement.unit.SystemOfUnits;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.5, $Date: 2010-09-11 14:45:05 +0200 (So, 11 Sep 2011) $
 */
public final class MonetaryUnits extends AbstractSystemOfUnits {

	// Use currency not defined as constant (Rupees).
	public static final Unit<IMoney> INR = monetary(MoneyAmount.UNIT);

    /**
     * Default constructor (prevents this class from being instantiated).
     */
    private MonetaryUnits() {
    }
	
    /**
     * Returns the unique instance of this class.
     *
     * @return the MonetaryUnits instance.
     */
    public static SystemOfUnits getInstance() {
        return INSTANCE;
    }
    private static final MonetaryUnits INSTANCE = new MonetaryUnits();
	
	/**
     * Adds a new unit to the collection.
     *
     * @param  unit the unit being added.
     * @return <code>unit</code>.
     */
    private static <U extends Unit<?>> U monetary(U unit) {
        UNITS.add(unit);
        return unit;
    }

	public String getName() {
		return getClass().getSimpleName();
	}
}
