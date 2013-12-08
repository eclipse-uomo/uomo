/**
 * Copyright (c) 2013, Werner Keil and others.
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
import static org.eclipse.uomo.units.SI.Prefix.KILO;
import static org.eclipse.uomo.examples.units.types.SolarSystem.G;

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
 * @version 1.0
 * 
 * This <type>enum</type> is inspired by Josh Bloch's example in <a href="http://www.oracle.com/technetwork/java/effectivejava-136174.html">Effective Java Second Edition</a>
 * 
 * <p>
 * Suppose you want to add data and behavior to an enum. 
 * For example consider <a href="http://en.wikipedia.org/wiki/Dwarf_planet">dwarf planets</a> of the <a href="http://en.wikipedia.org/wiki/Solar_System">solar system</a>. 
 * Each planet knows its mass and radius, and can calculate its surface gravity and the weight of an object on the planet. 
 * Here is how it looks:
 * </p>
 */
public enum DwarfPlanet {
	CERES(newMass(9.43e+20, KILOGRAM), newLength(0.4873e6, METRE)),
    PLUTO(newMass(1.305e+22, KILOGRAM), newLength(1.153e6, METRE)),
    HAUMEA(newMass(4.006e+21, KILOGRAM), newLength(620, KILO(METRE))),
    MAKEMAKE(newMass(3e+21, KILOGRAM), newLength(715, KILO(METRE))),
    ERIS(newMass(1.67e+22, KILOGRAM), newLength(1163, KILO(METRE)));

    private final IMeasure<Mass> mass;   // in kilograms

    private final IMeasure<Length> radius; // in meters

    DwarfPlanet(IMeasure<Mass> mass, IMeasure<Length> radius) {
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
		sb.append("Radius: ");
		sb.append(getRadius());
		sb.append("; ");
		sb.append("Surface Gravity: ");
		sb.append(surfaceGravity());
		return sb.toString();
	}
}