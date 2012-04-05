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
 * @version 0.7 ($Revision: 210 $), $Date: 2011-12-29 23:34:46 +0100 (Do, 29 Dec 2011) $
 */
public class Demo {

	private static IMeasure<Length> getSomeLength() {
        return new LengthAmount(20, METRE);
    }
	
	private static IMeasure<Length> getMoreLength() {
        return new LengthAmount(20, METRE);
    }
	
	private static IMeasure<Mass> getSomeMass() {
        return new MassAmount(30, KILOGRAM);
    }
	
	private static IMeasure<Mass> getMoreMass() {
		return new MassAmount(30, KILOGRAM);
	}
	
	private static IMeasure<Time> getTime() {
		return new TimeAmount(10, SECOND);
	}

    public static void main(String[] args) {
    	IMeasure<Length> someLength = getSomeLength();
        System.out.println("toString = " + someLength);
        IMeasure<Length> moreLength = getMoreLength();
        System.out.println("toString2 = " + moreLength);
        System.out.println();

        IMeasure<Mass> someMass = getSomeMass();
        System.out.println("toString = " + someMass);        
        IMeasure<Mass> moreMass = getMoreMass();
        System.out.println("toString2 = " + moreMass);
        System.out.println();
        
        IMeasure<Time> time = getTime();
        System.out.println("toString = " + time);
        
        IMeasure<?> result = moreLength.divide(time);
        System.out.println("toString3 = " + result);
        
        IMeasure<Length> convertedLength = moreLength.to(FOOT);
        System.out.println("converted = " + convertedLength);
        
        System.out.println();
        someLength = new LengthAmount(1, MILLI(METRE));
        System.out.println("len1 = " + someLength);
        IMeasure<Length> someMoreLength = new LengthAmount(400, KILO(METRE));
        someMass = new MassAmount(50, MILLI(GRAM));
        System.out.println("mass1 = " + someMass);
        System.out.println("len2 = " + someMoreLength);
    }
}
