/**
 * Copyright (c) 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial implementation
 */
package org.eclipse.uomo.examples.units.console.sandbox;

import javax.measure.Quantity;

import org.eclipse.uomo.units.impl.quantity.AngleAmount;
import org.eclipse.uomo.units.impl.system.SI;
import javax.measure.quantity.Angle;

/**
 * @author Werner
 *
 */
public class SandboxPlayground {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Angle a = new AngleAmount(12,SI.RADIAN); 
		System.out.println(a);
		Quantity<Angle> m = new AngleAmount(12,SI.RADIAN);
		System.out.println(m);
	}

}
