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
package org.eclipse.uomo.units;

import static org.eclipse.uomo.core.impl.OutputHelper.print;
import static org.eclipse.uomo.core.impl.OutputHelper.println;
import static org.eclipse.uomo.units.SI.GRAM;
import static org.eclipse.uomo.units.SI.KILOGRAM;
import static org.eclipse.uomo.units.SI.METRE;
import static org.eclipse.uomo.units.SI.Prefix.MILLI;
import static org.eclipse.uomo.units.USCustomary.LITER;
import static org.eclipse.uomo.units.USCustomary.METER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitsofmeasurement.quantity.Mass;
import org.unitsofmeasurement.quantity.Volume;
import org.unitsofmeasurement.unit.SystemOfUnits;
import org.unitsofmeasurement.unit.Unit;

/**
 * Unit test for class org.eclipse.uomo.units.SI
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.3 ($Revision: 172 $), $Date: 2010-02-21 20:02:14 +0100 (So, 21
 *          Feb 2010) $
 */
public class SITest {
	SystemOfUnits result;

	@Before
	public void setUp() throws Exception {
		result = SI.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		result = null;
	}

	//
	// public static void main(String[] args) {
	// new SITest("").testGetInstance();
	// }
	/**
	 * Test of getInstance method, of class SI.
	 */
	@Test
	public void testGetInstance() {
		// print("getInstance: " + NonSI.GALLON_UK.divide(8) + " (" +
		// NonSI.GALLON_UK.divide(8).getDimension().toString() + ")");

		// Checks SI contains the 7 SI base units.
		assertTrue(result.getUnits().contains(AbstractUnit.valueOf("m")));
		assertTrue(result.getUnits().contains(SI.KILOGRAM)); // TODO check
																// parsing: "kg"
		assertTrue(result.getUnits().contains(AbstractUnit.valueOf("s")));
		assertTrue(result.getUnits().contains(AbstractUnit.valueOf("mol")));
		assertTrue(result.getUnits().contains(AbstractUnit.valueOf("K")));
		assertTrue(result.getUnits().contains(AbstractUnit.valueOf("cd")));
		assertTrue(result.getUnits().contains(AbstractUnit.valueOf("A")));

		print(AbstractUnit.valueOf("m"));
		println(AbstractUnit.valueOf("m").getDimension().toString());
	}

	@Test
	public void testMass() {
		assertTrue(GRAM.isCompatible(KILOGRAM));
		Unit<Mass> MILLIGRAM = MILLI(GRAM);
		println(MILLIGRAM);
	}

	@Test
	public void testVolume() {
		// print("ML: ");
		Unit<Volume> MILLILITER = MILLI(LITER);
		println(MILLILITER);
	}

	@Test
	public void testSIvsUS() {
		assertEquals(METRE, METER);
	}
}
