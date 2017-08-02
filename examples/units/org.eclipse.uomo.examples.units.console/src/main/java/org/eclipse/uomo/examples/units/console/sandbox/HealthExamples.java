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
package org.eclipse.uomo.examples.units.console.sandbox;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uomo.examples.units.types.Health;
import org.eclipse.uomo.examples.units.types.HeartRate;
import org.eclipse.uomo.examples.units.types.HeartRateAmount;
import javax.measure.Quantity;

/**
 * @author Werner Keil
 * @version 0.1
 */
public class HealthExamples {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Quantity<HeartRate>> rates = new LinkedList<Quantity<HeartRate>>();
		int value = 0;
		for (int i=0; i<50; i++) {
	        value = (int) (Math.random() * 40 + 50);
		
			Quantity<HeartRate> rate = new HeartRateAmount(value, Health.BPM);
			rates.add(rate);
		}
		
		for (Quantity<HeartRate> r : rates) {
			System.out.println("Rate: " + r);
		}
	}

}
