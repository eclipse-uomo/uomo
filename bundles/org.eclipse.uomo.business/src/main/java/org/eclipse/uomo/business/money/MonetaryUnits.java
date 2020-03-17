/**
 * Copyright (c) 2006, 2020, Werner Keil and others.
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
import tech.units.indriya.AbstractSystemOfUnits;
import javax.measure.spi.SystemOfUnits;
import javax.measure.Unit;

import com.ibm.icu.util.Currency;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.6, $Date: 2013-05-20 $
 */
public final class MonetaryUnits extends AbstractSystemOfUnits {
	/**
	 * The predefined name space for ISO 4217 currencies, similar to
	 * {@link Currency}.
	 */
	public static final String ISO_NAMESPACE = "ISO-4217";
	
	/**
	 * The Australian Dollar currency unit.
	 */
	public static final MoneyUnit<IMoney> AUD = monetary(new MoneyUnit<IMoney>("AUD")); //$NON-NLS-1$
	
	// Use currency not defined as constant (Rupees).
	public static final Unit<IMoney> INR = monetary(new MoneyUnit<IMoney>("INR")); //$NON-NLS-1$

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
    private final static <U extends Unit<?>> U monetary(U unit) {
        INSTANCE.add(unit);
        return unit;
    }

	public String getName() {
		return getClass().getSimpleName();
	}
}
