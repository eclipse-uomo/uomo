/**
 * Copyright (c) 2005, 2022, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Area;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;

import si.uom.SI;
import systems.uom.common.USCustomary;
import tech.units.indriya.quantity.Quantities;

/**
 * A 'Hello World!' style example showing some basic units and operations.
 * @author Werner Keil
 * @version 1.0
 */
public class HelloUnits {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Quantity<Length> length = Quantities.getQuantity(10, SI.METRE);
//		LengthAmount length = new LengthAmount(10, SI.KILOGRAM); // this won't work ;-)
		
		System.out.println(length);
		Unit<Length> lenUnit =  length.getUnit();
		
		//System.out.print(length.doubleValue(USCustomary.FOOT)); 
		System.out.println(" " + USCustomary.FOOT);
//		System.out.println(length.doubleValue(USCustomary.POUND)); // this won't work either.
		UnitConverter inchConverter = lenUnit.getConverterTo(USCustomary.INCH);
		System.out.print(inchConverter.convert(length.getValue().doubleValue()));		
		System.out.println(" " + USCustomary.INCH);
		
		@SuppressWarnings("unchecked")
		Quantity<Area> area = Quantities.getQuantity(length.getValue().doubleValue() * length.getValue().doubleValue(), 
				(Unit<Area>) length.getUnit().multiply(SI.METRE));
		System.out.println(area);
		
		// Equivalent to 
		Quantity<Length> meters = Quantities.getQuantity(5, SI.METRE);
		Quantity<Time> secs = Quantities.getQuantity(2, SI.SECOND);
		@SuppressWarnings("unchecked")
		Quantity<Speed> speed = (Quantity<Speed>) meters.divide(secs);
		System.out.println(meters + 
				"; " + secs +
				"; " + speed);
	}
}
