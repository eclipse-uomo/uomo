/**
 * Copyright (c) 2005, 2011, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units;

import java.util.HashSet;
import java.util.Set;

import org.unitsofmeasurement.unit.Dimension;
import org.unitsofmeasurement.unit.SystemOfUnits;
import org.unitsofmeasurement.unit.Unit;

abstract class AbstractSystemOfUnits implements SystemOfUnits {

	static class Helper {
		static Set<Unit<?>> getUnitsOfDimension(final Set<Unit<?>> units, 
				Dimension dimension) {
			if (dimension != null) {
				Set<Unit<?>>dimSet = new HashSet<Unit<?>>();
				for (Unit<?> u : units) {
					if (dimension.equals(u.getDimension())) {
						dimSet.add(u);
					}
				}
			}
			return null;
		}
	}
}
