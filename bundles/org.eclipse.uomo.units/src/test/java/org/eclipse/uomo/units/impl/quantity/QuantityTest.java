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
package org.eclipse.uomo.units.impl.quantity;

import static org.eclipse.uomo.core.impl.OutputHelper.*;
import static org.eclipse.uomo.units.SI.AMPERE_TURN;
import static org.eclipse.uomo.units.SI.KILOGRAM;
import static org.eclipse.uomo.units.SI.METRE;
import static org.eclipse.uomo.units.SI.METRES_PER_SQUARE_SECOND;
import static org.eclipse.uomo.units.SI.Prefix.KILO;
import static org.junit.Assert.assertEquals;

import org.eclipse.uomo.units.impl.quantity.AccelerationAmount;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MagnetomotiveForceAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version $Revision$, $Date$
 */
public class QuantityTest {
	LengthAmount length;
	AccelerationAmount accel;
	MagnetomotiveForceAmount magneto;
	
    // universal gravitational constant  (m3 kg-1 s-2)
    private static final double G = 6.67300E-11;
	
	private MassAmount mass;   // in kilograms
	private LengthAmount radius; // in meters

	
	@Before
	public void setUp() throws Exception {
		length  = new LengthAmount(4.0, METRE);
		accel   = new AccelerationAmount(30, METRES_PER_SQUARE_SECOND);
		magneto = new MagnetomotiveForceAmount(50, AMPERE_TURN);
		
		mass 	= new MassAmount(30, KILOGRAM);
		radius  = new LengthAmount(400, KILO(METRE));
	}

	@After
	public void tearDown() throws Exception {
		length = null;
	}

	@Test
	public void testLength() {
		LengthAmount len2 = new LengthAmount(4.0, METRE);
		assertEquals(length, len2);
	}
	
	private AccelerationAmount surfaceGravity() {
        double m = mass.doubleValue(KILOGRAM);
        double r = radius.doubleValue(METRE);
        return new AccelerationAmount(
                G * m / (r * r), METRES_PER_SQUARE_SECOND);
    }
	
	@Test
	public void testAccelo1() {
		AccelerationAmount ac1 = surfaceGravity();
		print("Gravity: ");
		println(ac1);
	}
}
