/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.types;

import static org.eclipse.uomo.units.SI.*;
import static org.eclipse.uomo.examples.units.types.SolarSystem.G;

import org.eclipse.uomo.examples.units.Messages;
import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.impl.quantity.AccelerationAmount;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.unitsofmeasurement.quantity.Acceleration;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.quantity.Mass;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.0.2
 * 
 * This <type>enum</type> is inspired by Josh Bloch's example in <a href="http://www.oracle.com/technetwork/java/effectivejava-136174.html">Effective Java Second Edition</a>
 * 
 * <p>
 * Suppose you want to add data and behavior to an enum. 
 * For example consider <a href="http://en.wikipedia.org/wiki/Planet">planets</a> of the <a href="http://en.wikipedia.org/wiki/Solar_System">solar system</a>.  
 * Each planet knows its mass and radius, and can calculate its surface gravity and the weight of an object on the planet. 
 * Here is how it looks:
 * </p>
 */
public enum Planet {

    MERCURY(newMass(3.303e+23, KILOGRAM), newLength(2.4397e6, METRE)),
    VENUS(newMass(4.869e+24, KILOGRAM), newLength(6.0518e6, METRE)),
    EARTH(newMass(5.976e+24, KILOGRAM), newLength(6.37814e6, METRE)),
    MARS(newMass(6.421e+23, KILOGRAM), newLength(3.3972e6, METRE)),
    JUPITER(newMass(1.9e+27, KILOGRAM), newLength(7.1492e7, METRE)),
    SATURN(newMass(5.688e+26, KILOGRAM), newLength(6.0268e7, METRE)),
    URANUS(newMass(8.686e+25, KILOGRAM), newLength(2.5559e7, METRE)),
    NEPTUNE(newMass(1.024e+26, KILOGRAM), newLength(2.4746e7, METRE));

    private final IMeasure<Mass> mass;   // in kilograms

    private final IMeasure<Length> radius; // in meters

    Planet(IMeasure<Mass> mass, IMeasure<Length> radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public IMeasure<Mass> getMass() {
        return mass;
    }

    public IMeasure<Length> getRadius() {
        return radius;
    }

    public Acceleration surfaceGravity() {
        double m = mass.doubleValue(KILOGRAM);
        double r = radius.doubleValue(METRE);
        return new AccelerationAmount(
                G * m / (r * r), METRES_PER_SQUARE_SECOND);
    }

    private static IMeasure<Mass> newMass(double value, Unit<Mass> unit) {
        return new MassAmount(value, unit);
    }

    private static IMeasure<Length> newLength(double value, Unit<Length> unit) {
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
		sb.append(Messages.Planet_Radius);
		sb.append(getRadius());
		sb.append("; ");
		sb.append("Surface Gravity: ");
		sb.append(surfaceGravity());
		return sb.toString();
	}
}