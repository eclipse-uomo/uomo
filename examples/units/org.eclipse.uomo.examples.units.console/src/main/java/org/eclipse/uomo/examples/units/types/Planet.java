/**
 * Copyright (c) 2005, 2011, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Josh Bloch, Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.types;

import static org.eclipse.uomo.units.SI.*;
import static org.eclipse.uomo.examples.units.Messages.*;

import org.eclipse.uomo.units.AbstractUnit;
import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.impl.quantity.AccelerationAmount;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.unitsofmeasurement.quantity.*;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @author Josh Bloch
 * @version 1.0.7, $Date: 2011-03-06 $
 * 
 *          This <type>enum</type> is based on Josh Bloch's example in <a
 *          href="http://java.sun.com/docs/books/effective/">Effective Java
 *          Second Edition</a>
 * 
 * 			Added <a
 * href="http://download.oracle.com/javase/tutorial/java/javaOO/enum.html">Oracle tutorial on Enum Types</a> 
 *          <p>
 *          Suppose you want to add data and behavior to an enum. For example
 *          consider the planets of the solar system. Each planet knows its mass
 *          and radius, and can calculate its surface gravity and the weight of
 *          an object on the planet. Here is how it looks:
 *          </p>
 */
public enum Planet {
	MERCURY(newMass(3.303e+23, KILOGRAM), newLength(2.4397e6, METRE)), 
	VENUS(newMass(4.869e+24, KILOGRAM), newLength(6.0518e6, METRE)), 
	EARTH(newMass(5.976e+24, KILOGRAM), newLength(6.37814e6, METRE)), 
	MARS(newMass(6.421e+23, KILOGRAM), newLength(3.3972e6, METRE)), 
	JUPITER(newMass(1.9e+27, KILOGRAM), newLength(7.1492e7, METRE)), 
	SATURN(newMass(5.688e+26, KILOGRAM), newLength(6.0268e7, METRE)), 
	URANUS(newMass(8.686e+25, KILOGRAM), newLength(2.5559e7, METRE)), 
	NEPTUNE(newMass(1.024e+26, KILOGRAM), newLength(2.4746e7, METRE)), 
	PLUTO(newMass(1.27e+22, KILOGRAM), newLength(1.137e6, METRE));

	private final MassAmount mass; // in kilograms
	private final LengthAmount radius; // in meters

	Planet(MassAmount mass, LengthAmount radius) {
		this.mass = mass;
		this.radius = radius;
	}

	public Mass getMass() {
		return mass;
	}

	public Length getRadius() {
		return radius;
	}

	// universal gravitational constant (m3 kg-1 s-2)
	private static final double G = 6.67300E-11;

	public AccelerationAmount surfaceGravity() {
		double m = mass.doubleValue(KILOGRAM);
		double r = radius.doubleValue(METRE);
		return new AccelerationAmount(G * m / (r * r), METRES_PER_SQUARE_SECOND);
	}

	private static MassAmount newMass(double value, Unit<Mass> unit) {
		return new MassAmount(value, unit);
	}

	private static LengthAmount newLength(double value, Unit<Length> unit) {
		return new LengthAmount(value, unit);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("; "); //$NON-NLS-1$
		sb.append(Mass.class.getSimpleName());
		sb.append(": "); //$NON-NLS-1$
		sb.append(getMass());
		sb.append("; "); //$NON-NLS-1$
		sb.append(Planet_Radius);
		sb.append(getRadius());
		sb.append("; "); //$NON-NLS-1$
		sb.append(Planet_SurfaceGravity);
		sb.append(surfaceGravity());
		return sb.toString();
	}
	
    IMeasure<Mass> surfaceWeight(IMeasure<Mass> otherMass) {
        return (IMeasure<Mass>) otherMass.multiply((AccelerationAmount)surfaceGravity());
    }
	
	public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println(Planet_Usage);
            System.exit(-1);
        }
        Unit<Mass> weightUnit = KILOGRAM;
        if (args.length > 1 && args[1].length() > 0) {
			weightUnit = (Unit<Mass>) AbstractUnit.valueOf(args[1]);
        }
        MassAmount earthWeight = new MassAmount(Double.parseDouble(args[0]), 
        		(Unit<Mass>) weightUnit);
        IMeasure<Mass> mass = (IMeasure<Mass>) earthWeight.divide(EARTH.surfaceGravity());
        for (Planet p : Planet.values())
           System.out.printf(Planet_SurfaceWeight,
                             p.name(), p.surfaceWeight(mass));
    }
}
