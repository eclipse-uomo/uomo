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
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.unit.Unit;

/**
 * Represents the extent of something along its greatest
 * dimension or the extent of space between two objects or places.
 * The metric system unit for this quantity is "m" (metre).
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.5, $Date: 2013-05-21 $
 */
public final class LengthAmount extends BaseQuantity<Length> implements Length {

	public LengthAmount(Number number, Unit<Length> unit) {
		super(number, unit);
	}
}
