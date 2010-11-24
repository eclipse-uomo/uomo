/**
 * Copyright (c) 2006, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.business.money;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.uomo.util.IName;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.Dimension;
import org.unitsofmeasurement.unit.SystemOfUnits;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.3, $Date: 2010-10-13 16:45:05 +0200 (Mi, 13 Okt 2010) $
 */
public final class MonetaryUnits implements SystemOfUnits, IName {
	
    /**
     * Holds collection of Monetary units.
     */
    private static HashSet<Unit<?>> UNITS = new HashSet<Unit<?>>();

	// Use currency not defined as constant (Rupees).
	public static final Unit<Money> INR = monetary(MoneyAmount.UNIT);

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

	/* (non-Javadoc)
	 * @see javax.measure.unit.SystemOfUnits#getUnits()
	 */
	public Set<Unit<?>> getUnits() {
		return UNITS;
	}
	
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

	public Set<Unit<?>> getUnits(Dimension dimension) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Q extends Quantity<Q>> Unit<Q> getUnit(Class<Q> quantityType) {
		// TODO Auto-generated method stub
		return null;
	}
}
