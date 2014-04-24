/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Jean-Marie Dautelle - initial API and implementation
 */
package org.eclipse.uomo.units.impl.quantity;

import org.eclipse.uomo.units.impl.BaseQuantity;
import org.unitsofmeasurement.quantity.LuminousIntensity;
import org.unitsofmeasurement.unit.Unit;

/**
 * Represents the luminous flux density per solid angle as measured in a given
 * direction relative to the emitting source. The metric system unit for this
 * quantity is "cd" (candela).
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.2, $Date: 2013-05-21 $
 */
public final class LuminousIntensityAmount extends
		BaseQuantity<LuminousIntensity> implements LuminousIntensity {

	public LuminousIntensityAmount(Number number, Unit<LuminousIntensity> unit) {
		super(number, unit);
	}
}