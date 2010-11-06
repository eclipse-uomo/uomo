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

import static org.eclipse.uomo.core.impl.OutputHelper.println;

import org.junit.Test;

/**
 * @author Werner Keil
 * @version 1.3 ($Revision: 90 $), $Date: 2010-07-28 18:39:26 +0100 (Mi, 28 Jul 2010) $
 */
public class PlanetTest {

	@Test
	public void testPlanets() {
		Planet[] solarSystem = Planet.values();
		
		for (Planet planet : solarSystem) {
			println(planet);
		}
	}
}
