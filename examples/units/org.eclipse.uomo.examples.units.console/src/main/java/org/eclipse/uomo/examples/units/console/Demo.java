/**
 * Copyright (c) 2005, 2011, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Martin Desruisseaux - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console;

import static org.eclipse.uomo.units.SI.*;
import static org.eclipse.uomo.units.SI.Prefix.*;
import static org.eclipse.uomo.units.impl.system.USCustomary.FOOT;
import static org.eclipse.uomo.units.impl.system.USCustomary.INCH;
import static org.eclipse.uomo.units.impl.system.USCustomary.POUND;
import static org.eclipse.uomo.examples.units.types.PolishObsolete.*;

import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.eclipse.uomo.units.impl.quantity.TimeAmount;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.quantity.Mass;
import org.unitsofmeasurement.quantity.Time;

/**
 * @author <a href="mailto:desruisseaux@users.sourceforge.net">Martin Desruisseaux</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.8, $Date: 2013-11-19 $
 */
public class Demo {

	private static IMeasure<Length> getSomeLength() {
        return new LengthAmount(20, METRE);
    }
	
	private static IMeasure<Length> getMoreLength() {
        return new LengthAmount(30, INCH);
    }
	
	private static IMeasure<Mass> getSomeMass() {
        return new MassAmount(40, KILOGRAM);
    }
	
	private static IMeasure<Mass> getMoreMass() {
		return new MassAmount(50, POUND);
	}
	
	private static IMeasure<Time> getTime() {
		return new TimeAmount(10, SECOND);
	}

    public static void main(String[] args) {
    	IMeasure<Length> someLength = getSomeLength();
        System.out.println("len = " + someLength);
        IMeasure<Length> moreLength = getMoreLength();
        System.out.println("len2 = " + moreLength);
        System.out.println();

        IMeasure<Mass> someMass = getSomeMass();
        System.out.println("mass = " + someMass);        
        IMeasure<Mass> moreMass = getMoreMass();
        System.out.println("mass2 = " + moreMass);
        System.out.println();
        
        IMeasure<Time> time = getTime();
        System.out.println("time = " + time);
        
        IMeasure<?> result = someLength.divide(time);
        System.out.println("speed = " + result);
        result = moreLength.divide(time);
        System.out.println("speed2 = " + result);
        System.out.println();
        
        IMeasure<Length> convertedLength = moreLength.to(FOOT);
        System.out.println("len2 converted = " + convertedLength);
        
        IMeasure<Length> convertedLengthPL = moreLength.to(ELL);
        System.out.println("len2 converted (PL) = " + convertedLengthPL);
        
        
        System.out.println();
        someLength = new LengthAmount(1, MILLI(METRE));
        System.out.println("len3 = " + someLength);
        IMeasure<Length> someMoreLength = new LengthAmount(400, KILO(METRE));
        someMass = new MassAmount(60, MILLI(GRAM));
        System.out.println("mass3 = " + someMass);
        System.out.println("len4 = " + someMoreLength);
    }
}
