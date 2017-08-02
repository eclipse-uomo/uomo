/**
 * Copyright (c) 2005, 2014, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Martin Desruisseaux - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console;

import static org.eclipse.uomo.units.impl.system.SI.*;
import static org.eclipse.uomo.units.impl.system.SI.Prefix.*;
import static org.eclipse.uomo.units.impl.system.USCustomary.FOOT;
import static org.eclipse.uomo.units.impl.system.USCustomary.INCH;
import static org.eclipse.uomo.units.impl.system.USCustomary.POUND;
import static org.eclipse.uomo.examples.units.types.PolishObsolete.*;

import javax.measure.Quantity;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.eclipse.uomo.units.impl.quantity.TimeAmount;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Time;

/**
 * @author <a href="mailto:desruisseaux@users.sourceforge.net">Martin Desruisseaux</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.8.1, $Date: 2014-04-03 $
 */
public class Demo {

	private static Quantity<Length> getSomeLength() {
        return new LengthAmount(20, METRE);
    }
	
	private static Quantity<Length> getMoreLength() {
        return new LengthAmount(30, INCH);
    }
	
	private static Quantity<Mass> getSomeMass() {
        return new MassAmount(40, KILOGRAM);
    }
	
	private static Quantity<Mass> getMoreMass() {
		return new MassAmount(50, POUND);
	}
	
	private static Quantity<Time> getTime() {
		return new TimeAmount(10, SECOND);
	}

    public static void main(String[] args) {
    	Quantity<Length> someLength = getSomeLength();
        System.out.println(Messages.Demo_0 + someLength);
        Quantity<Length> moreLength = getMoreLength();
        System.out.println(Messages.Demo_1 + moreLength);
        System.out.println();

        Quantity<Mass> someMass = getSomeMass();
        System.out.println(Messages.Demo_2 + someMass);        
        Quantity<Mass> moreMass = getMoreMass();
        System.out.println(Messages.Demo_3 + moreMass);
        System.out.println();
        
        Quantity<Time> time = getTime();
        System.out.println(Messages.Demo_4 + time);
        
        Quantity<?> result = someLength.divide(time);
        System.out.println(Messages.Demo_5 + result);
        result = moreLength.divide(time);
        System.out.println(Messages.Demo_6 + result);
        System.out.println();
        
        Quantity<Length> convertedLength = moreLength.to(FOOT);
        System.out.println(Messages.Demo_7 + convertedLength);
        
        Quantity<Length> convertedLengthPL = moreLength.to(ELL);
        System.out.println(Messages.Demo_8 + convertedLengthPL);
        
        
        System.out.println();
        someLength = new LengthAmount(1, MILLI(METRE));
        System.out.println(Messages.Demo_9 + someLength);
        Quantity<Length> someMoreLength = new LengthAmount(400, KILO(METRE));
        someMass = new MassAmount(60, MILLI(GRAM));
        System.out.println(Messages.Demo_10 + someMass);
        System.out.println(Messages.Demo_11 + someMoreLength);
    }
}
