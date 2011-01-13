/**
 * Copyright (c) 2005, 2011, Werner Keil, Paul Morrison and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Paul Morrison - initial API and implementation
 */
package org.eclipse.uomo.examples.units.types;

import org.eclipse.uomo.units.impl.quantity.LengthAmount;

/**
 * @author paul.morrison
 * @author werner.keil
 */
public class Trip {

	/**
	 * @author paul.morrison
	 */
	public static class Leg {

		String fromAirport;
		String toAirport;
		LengthAmount distance;

		public Leg(String fA, String tA, LengthAmount dist) {
			fromAirport = fA;
			toAirport = tA;
			distance = dist;
		}

		public LengthAmount getDist() {
			return distance;
		}

	}

	Leg tripleg[];

	public Leg[] getLegs() {
		return tripleg;
	}

	public Trip(int no) {

		tripleg = new Leg[no];

	}

	public void addLeg(int no, Leg t) {

		tripleg[no] = t;
	}

	public Leg getLeg(int no) {

		return tripleg[no];
	}

	public LengthAmount getTotalDist() {

		LengthAmount x = tripleg[0].getDist();

		for (int i = 1; i < tripleg.length - 1; i++) {

			// x = x.add(tripleg[i].getDist());
		}
		return x;
	}

}
