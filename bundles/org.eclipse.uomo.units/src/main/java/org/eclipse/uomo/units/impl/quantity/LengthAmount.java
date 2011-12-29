/**
 * Copyright (c) 2005, 2011, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl.quantity;

import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.impl.BaseAmount;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.unit.Unit;

/**
 * Represents the extent of something along its greatest
 * dimension or the extent of space between two objects or places.
 * The metric system unit for this quantity is "m" (metre).
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.4 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 */
public class LengthAmount extends BaseAmount<Length> implements IMeasure<Length> {

	public LengthAmount(Number number, Unit<Length> unit) {
		super(number, unit);
	}
}
