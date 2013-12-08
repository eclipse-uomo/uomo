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

import static org.eclipse.uomo.core.impl.OutputHelper.println;
import static org.junit.Assert.assertEquals;

import org.eclipse.uomo.examples.units.types.Planet;
import org.junit.Test;


/**
 * @author Werner Keil
 * @version 1.0.1
 */
public class PlanetsTest {

	@Test
	public void testPlanets() {
		Planet[] planets = Planet.values();
		
		for (Planet planet : planets) {
			println(planet);
		}
		
		assertEquals(8, planets.length);
	}
	
	@Test
	public void testDwarfPlanets() {
		DwarfPlanet[] dwarfPlanets = DwarfPlanet.values();
		
		for (DwarfPlanet planet : dwarfPlanets) {
			println(planet);
		}
		
		assertEquals(5, dwarfPlanets.length);
	}
}
