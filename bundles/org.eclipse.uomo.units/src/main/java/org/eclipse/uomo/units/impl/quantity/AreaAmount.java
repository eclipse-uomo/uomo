/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, JScience and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl.quantity;

import org.eclipse.uomo.units.impl.BaseQuantity;
import org.unitsofmeasurement.quantity.Area;
import org.unitsofmeasurement.unit.Unit;


/**
 * Represents the extent of a planar region or of the surface of
 * a solid measured in square units.
 * The metric system unit for this quantity is "m²" (square metre).
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.4.1, $Date: 2013-11-22 $
 */
public final class AreaAmount extends BaseQuantity<Area> implements Area {

	public AreaAmount(Number number, Unit<Area> unit) {
		super(number, unit);
	}
}
