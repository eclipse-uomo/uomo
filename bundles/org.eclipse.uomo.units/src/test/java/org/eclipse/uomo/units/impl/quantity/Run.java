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


import static org.eclipse.uomo.units.SI.Prefix.KILO;

import org.eclipse.uomo.core.IName;
import org.eclipse.uomo.units.AbstractUnit;
import org.eclipse.uomo.units.SI;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.quantity.Mass;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author paul.morrison
 * @author werner.keil
 */

public class Run {

	public static void main(String[] argv) {

		// final Unit<Length> klik = new Unit("kilometre",
		// Distance.km, 1.0);
		final Unit<Length> klik = KILO(SI.METRE);

		final Unit<Mass> klik2 = SI.KILOGRAM;

		Trip trip = new Trip(2); // specify number of legs

		trip.addLeg(0, new TripLeg("YKK", "NYC", new LengthAmount(1, klik)));

		trip.addLeg(1, new TripLeg("NYC", "LAX", new LengthAmount(0, klik)));

		LengthAmount totDist = new LengthAmount(0, klik);

		for (int i = 0; i < trip.tripleg.length; i++) {

			// totDist = totDist.add(trip.tripleg[i].getDist()); FIXME
			totDist = new LengthAmount(totDist.getNumber().doubleValue()
					+ trip.tripleg[i].getDist().getNumber().doubleValue(), klik);
		}

		System.out.println(totDist);
		// System.out.println(totDist.showInUnits(klik,2));
		System.out.println(((AbstractUnit<?>) totDist.getQuantityUnit())
				.getName());
		// Distance nextDist = new Distance(0, klik2); // this willl fail!

		System.out.println(((IName) klik2).getName());
	}
}
