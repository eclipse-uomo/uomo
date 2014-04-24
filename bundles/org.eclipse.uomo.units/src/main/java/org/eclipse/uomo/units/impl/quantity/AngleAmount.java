/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, JScience - initial API and implementation
 */
package org.eclipse.uomo.units.impl.quantity;

import org.eclipse.uomo.units.impl.BaseQuantity;
import org.unitsofmeasurement.quantity.Angle;
import org.unitsofmeasurement.unit.Unit;


/**
 * Represents the figure formed by two lines diverging from a common point.
 * The metric system unit for this quantity is "rad" (radian).
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.3.1, $Date: 2013-11-22 $
 */
public final class AngleAmount extends BaseQuantity<Angle> implements Angle {
	
	public AngleAmount(Number number, Unit<Angle> unit) {
		super(number, unit);
	}
}
