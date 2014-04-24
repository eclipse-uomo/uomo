/**
 * Copyright (c) 2005, 2014, Werner Keil and others.
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
import org.unitsofmeasurement.quantity.MagnetomotiveForce;
import org.unitsofmeasurement.unit.Unit;

/**
 * Represents an amount of force that produces magnetic flux. The metric system
 * unit for this quantity is "At" (ampere-turn).
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.5, $Date: 2013-05-21 $
 * @see <a href="http://en.wikipedia.org/wiki/Magnetomotive_force">Wikipedia's
 *      Magnetomotive Force</a>
 */
public final class MagnetomotiveForceAmount extends
		BaseQuantity<MagnetomotiveForce> implements MagnetomotiveForce {

	public MagnetomotiveForceAmount(Number number, Unit<MagnetomotiveForce> unit) {
		super(number, unit);
	}
}
