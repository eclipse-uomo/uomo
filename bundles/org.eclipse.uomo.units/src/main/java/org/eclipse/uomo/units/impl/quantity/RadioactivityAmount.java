/**
 * Copyright (c) 2005, 2014, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl.quantity;

import org.eclipse.uomo.units.impl.BaseQuantity;
import org.unitsofmeasurement.quantity.RadioactiveActivity;
import org.unitsofmeasurement.unit.Unit;

/**
 * Represents radioactivity. The metric system unit for this quantity is "Bq"
 * (Becquerel).
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.2, $Date: 2014-03-03 $
 */
public class RadioactivityAmount extends BaseQuantity<RadioactiveActivity>
		implements RadioactiveActivity {

	public RadioactivityAmount(Number number, Unit<RadioactiveActivity> unit) {
		super(number, unit);
	}
}
