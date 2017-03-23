/**
 * Copyright (c) 2005, 2014, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console.sandbox;

import org.eclipse.uomo.units.AbstractQuantity;
import org.eclipse.uomo.units.SI;
import org.eclipse.uomo.units.impl.quantity.TemperatureAmount;
import org.eclipse.uomo.units.impl.system.USCustomary;
import org.unitsofmeasurement.quantity.Temperature;

/**
 * @author Werner Keil
 * @version 1.1
 *
 */
public class TemperatureExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TemperatureAmount temp1 = new TemperatureAmount(0, SI.CELSIUS);
		AbstractQuantity<Temperature> temp2 = temp1.to(SI.KELVIN);
		AbstractQuantity<Temperature> temp3 = temp1.to(USCustomary.FAHRENHEIT);
		System.out.println(temp1 + " -> " + temp2 + " -> " + temp3);
	}

}
