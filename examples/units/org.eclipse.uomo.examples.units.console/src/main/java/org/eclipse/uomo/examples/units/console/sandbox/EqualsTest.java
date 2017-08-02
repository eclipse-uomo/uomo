/**
 * Copyright (c) 2012, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console.sandbox;

import static org.eclipse.uomo.units.impl.system.SI.Prefix.KILO;

import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.eclipse.uomo.units.impl.system.SI;

public class EqualsTest {
	public static void main(String[] args) {
		// TODO Bug 338334 this could be a JUnit test, convert into after issue
		// resolved.
		MassAmount mass = new MassAmount(1000, SI.GRAM);
		MassAmount mass2 = new MassAmount(1, SI.KILOGRAM);
		MassAmount mass3 = new MassAmount(1, KILO(SI.GRAM));
		System.out.println(mass.equals(mass2) + "; " + mass.equals(mass3)
				+ "; " + mass2.equals(mass3));
	}
}
