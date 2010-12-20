/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl;

import static org.eclipse.uomo.units.SI.*;

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
 * @version 0.4 ($Revision: 210 $), $Date: 2010-02-25 23:34:46 +0100 (Do, 25 Feb 2010) $
 */
public class Demo {

	private static Length getSomeLength() {
        return new LengthAmount(20, METRE);
    }

	private static Mass getSomeMass() {
        return new MassAmount(30, KILOGRAM);
    }
	
	private static IMeasure<Mass> getMoreMass() {
		return new MassAmount(30, KILOGRAM);
	}
	
	private static IMeasure<Time> getTime() {
		return new TimeAmount<Time>(10, SECOND);
	}

    public static void main(String[] args) {
        Length someLength = getSomeLength();
        System.out.println("toString = " + someLength);
        System.out.println();

        Mass someMass = getSomeMass();
        System.out.println("toString = " + someMass);
        
        IMeasure<?> moreMass = getMoreMass();
        System.out.println("toString2 = " + moreMass);
        System.out.println();
        
        IMeasure<Time> time = getTime();
        System.out.println("toString = " + time);
    }
}
