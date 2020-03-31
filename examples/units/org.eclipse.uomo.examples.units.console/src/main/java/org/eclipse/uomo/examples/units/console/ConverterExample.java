/**
 * Copyright (c) 2005, 2020, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console;

import static si.uom.SI.*;
import static tech.units.indriya.unit.MetricPrefix.KILO;
import static systems.uom.common.USCustomary.FOOT;
import static systems.uom.common.USCustomary.INCH;
import static systems.uom.common.USCustomary.MILE;
import static systems.uom.common.USCustomary.OUNCE;

import java.math.BigDecimal;
import javax.measure.Quantity;
import si.uom.SI;

import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;

import javax.measure.quantity.Length;
import javax.measure.UnitConverter;

public class ConverterExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    // Conversion between units.
		UnitConverter conv = KILO(METRE).getConverterTo(MILE);
	    System.out.println(conv.convert(10.0));
	    // Retrieval of the system unit (identifies the measurement type).
	    System.out.println(REVOLUTION.divide(MINUTE).getSystemUnit());
	    // Dimension checking (allows/disallows conversions)
	    System.out.println(ELECTRON_VOLT.isCompatible(WATT.multiply(HOUR)));
	    // Retrieval of the unit dimension (depends upon the current model).
	    System.out.println(ELECTRON_VOLT.getDimension());
	    System.out.println(KILOGRAM.equals(KILO(GRAM)));
	    System.out.println(KILOGRAM.equals(KILO(OUNCE)));
	    
	    Quantity foot =  Quantities.getQuantity(1, FOOT); 
	    ComparableQuantity inches = Quantities.getQuantity(24, INCH);
	    double ratio = INCH.getConverterTo(FOOT).convert(24);
	    Quantity<Length> lRatio = inches.to(FOOT);
	    //long ratio = inches.longValue(FOOT);
	    //double ratio = foot.doubleValue(INCH);
	    System.out.println("Ratio: " + ratio);
	    @SuppressWarnings("unchecked")
		Quantity<Length> iRatio = (Quantity<Length>) foot.divide(inches);
	    System.out.println("Ratio2: " + iRatio);
	    System.out.println("Ratio3: " + lRatio);
	    //BaseAmount<Length> qaRatio = (BaseAmount<Length>)lRatio;
	    //System.out.println(qaRatio.getNumber());
	    
	    Quantity<Length> l1 = Quantities.getQuantity(1, MILE);
	    Quantity<Length> l2 = l1.to(FOOT);
	    System.out.println(l1 + " = " + l2);
	    
	    ComparableQuantity x = Quantities.getQuantity(BigDecimal.valueOf(1.0001d), KILO(SI.METRE));
	    Quantity<Length> xi = x.to(SI.METRE);
	    //Quantity<Length> xj = x.to(SI.METRE, MathContext.UNLIMITED);
	    System.out.println("x="+x+" xi="+xi); //+"+xj="+xj
	    // Results in: x=1.0001 km xi=1000.0 m
	}
}
