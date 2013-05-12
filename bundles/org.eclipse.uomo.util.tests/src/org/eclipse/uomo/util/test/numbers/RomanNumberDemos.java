/**
 * Copyright (c) 1996, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util.test.numbers;

import org.eclipse.uomo.util.numbers.ISpeller;
import org.eclipse.uomo.util.numbers.impl.RomanNumberSpeller;

public class RomanNumberDemos {

	public static void main(String args[]) throws Exception {
		System.out.println("Display massage number to Text!");
		final ISpeller num = RomanNumberSpeller.of();
		System.out.println("Spelling: " + num.spell(10) + ".");
		System.out.println("Spelling: " + num.spell(15) + ".");
		System.out.println("Spelling: " + num.spell(50) + ".");
		System.out.println("Spelling: " + num.spell(99) + ".");
		System.out.println("Spelling: " + num.spell(150) + ".");
		System.out.println("Spelling: " + num.spell(234) + ".");
		System.out.println("Spelling: " + num.spell(250) + ".");
		System.out.println("Spelling: " + num.spell(2500) + ".");
		System.out.println("Spelling: " + num.spell(3900) + ".");
		// System.out.println("Spelling: " + num.spell(4000) + ".");
	}
}