/**
 * Copyright (c) 2005, 2011, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console;

import static org.eclipse.uomo.units.SI.Prefix.KILO;

import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.SI;
import org.eclipse.uomo.units.USCustomary;
import org.eclipse.uomo.units.impl.quantity.AreaAmount;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.eclipse.uomo.units.impl.quantity.TimeAmount;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;
import org.unitsofmeasurement.quantity.Acceleration;
import org.unitsofmeasurement.quantity.Area;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.quantity.Time;

public class HelloUnits {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LengthAmount length = new LengthAmount(10, SI.METRE);
//		LengthAmount length = new LengthAmount(10, SI.KILOGRAM); // this won't work ;-)
		
		System.out.println(length);
		Unit<Length> lenUnit =  length.unit();
		System.out.println(lenUnit);
		
		System.out.println(length.doubleValue(USCustomary.FOOT)); 
//		System.out.println(length.doubleValue(USCustomary.POUND)); // this won't work either.
		UnitConverter footConverter = lenUnit.getConverterTo(USCustomary.INCH);
		System.out.print(footConverter.convert(length.getNumber().doubleValue()));
		System.out.println(" " + USCustomary.FOOT);
		
		@SuppressWarnings("unchecked")
		AreaAmount area = new AreaAmount(length.getNumber().doubleValue() * length.getNumber().doubleValue(), 
				(Unit<Area>) length.unit().multiply(SI.METRE));
		System.out.println(area);
		
		// TODO Bug 338334 this could be a JUnit test, convert into after issue resolved.
		MassAmount mass = new MassAmount(1000, SI.GRAM);
		MassAmount mass2 = new MassAmount(1, SI.KILOGRAM);
		MassAmount mass3 = new MassAmount(1, KILO(SI.GRAM));
		System.out.println(mass.equals(mass2) + 
				"; " + mass.equals(mass3) +
				"; " + mass2.equals(mass3));
		
		// Equivalent to 
		IMeasure<Length> meters = new LengthAmount(5, SI.METRE);
		IMeasure<Time> secs = new TimeAmount(2, SI.SECOND);
		@SuppressWarnings("unchecked")
		IMeasure<Acceleration> speed = (IMeasure<Acceleration>) meters.divide(secs);
		System.out.println(meters + 
				"; " + secs +
				"; " + speed);
	}
}
