/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Josh Bloch, Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units.impl;

import static org.eclipse.uomo.units.SI.*;

import org.eclipse.uomo.units.impl.quantity.AccelerationAmount;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.unitsofmeasurement.quantity.*;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Josh Bloch
 * @version 1.0.5, $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 * 
 *          This <type>enum</type> is based on Josh Bloch's example in <a
 *          href="http://java.sun.com/docs/books/effective/">Effective Java
 *          Second Edition</a>
 * 
 *          <p>
 *          Suppose you want to add data and behavior to an enum. For example
 *          consider the planets of the solar system. Each planet knows its mass
 *          and radius, and can calculate its surface gravity and the weight of
 *          an object on the planet. Here is how it looks:
 *          </p>
 */
enum Planet {

    MERCURY(newMass(3.303e+23, KILOGRAM), newLength(2.4397e6, METRE)),
    VENUS(newMass(4.869e+24, KILOGRAM), newLength(6.0518e6, METRE)),
    EARTH(newMass(5.976e+24, KILOGRAM), newLength(6.37814e6, METRE)),
    MARS(newMass(6.421e+23, KILOGRAM), newLength(3.3972e6, METRE)),
    JUPITER(newMass(1.9e+27, KILOGRAM), newLength(7.1492e7, METRE)),
    SATURN(newMass(5.688e+26, KILOGRAM), newLength(6.0268e7, METRE)),
    URANUS(newMass(8.686e+25, KILOGRAM), newLength(2.5559e7, METRE)),
    NEPTUNE(newMass(1.024e+26, KILOGRAM), newLength(2.4746e7, METRE)),
    PLUTO(newMass(1.27e+22, KILOGRAM), newLength(1.137e6, METRE));

    private final MassAmount mass;   // in kilograms

    private final LengthAmount radius; // in meters

    Planet(MassAmount mass, LengthAmount radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public MassAmount getMass() {
        return mass;
    }

    public LengthAmount getRadius() {
        return radius;
    }

    // universal gravitational constant  (m3 kg-1 s-2)
    private static final double G = 6.67300E-11;

	public AccelerationAmount surfaceGravity() {
        double m = mass.doubleValue(KILOGRAM);
        double r = radius.doubleValue(METRE);
        return new AccelerationAmount(
                G * m / (r * r), METRES_PER_SQUARE_SECOND);
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
		sb.append("; ");
	sb.append(Mass.class.getSimpleName());
		sb.append(": ");
		sb.append(getMass());
		sb.append("; ");
		sb.append("Radius: ");
		sb.append(getRadius());
		sb.append("; ");
		sb.append("Surface Gravity: ");
		sb.append(surfaceGravity());
		return sb.toString();
	}
}
