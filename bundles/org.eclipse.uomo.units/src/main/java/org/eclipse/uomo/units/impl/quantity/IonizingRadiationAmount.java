/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units.impl.quantity;

import org.eclipse.uomo.units.impl.BaseQuantity;
import org.unitsofmeasurement.quantity.IonizingRadiation;
import org.unitsofmeasurement.unit.Unit;

/**
 * Represents the quantity of subatomic particles or electromagnetic waves that
 * are energetic enough to detach electrons from atoms or molecules, ionizing
 * them. The system unit for this quantity is "C/kg ("coulomb per kilogram).
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.1, $Date: 2013-05-21 $
 */
public final class IonizingRadiationAmount extends BaseQuantity<IonizingRadiation>
		implements IonizingRadiation {

	public IonizingRadiationAmount(Number number, Unit<IonizingRadiation> unit) {
		super(number, unit);
	}
}
