/**
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console.sandbox;

import org.eclipse.uomo.units.impl.quantity.TemperatureAmount;
import org.eclipse.uomo.units.impl.system.SI;
import org.eclipse.uomo.units.impl.system.USCustomary;

import javax.measure.Quantity;
import javax.measure.quantity.Temperature;

/**
 * @author Werner Keil
 * @version 1.2
 *
 */
public class TemperatureExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TemperatureAmount temp1 = new TemperatureAmount(0, SI.CELSIUS);
		Quantity<Temperature> temp2 = temp1.to(SI.KELVIN);
		Quantity<Temperature> temp3 = temp1.to(USCustomary.FAHRENHEIT);
		System.out.println(temp1 + " -> " + temp2 + " -> " + temp3);
	}
}
