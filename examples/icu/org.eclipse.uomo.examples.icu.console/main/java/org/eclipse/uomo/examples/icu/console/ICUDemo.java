/**
 * Copyright (c) 2005, 2020, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.icu.console;

//Constants (Java 5 static import)
import static org.eclipse.uomo.business.money.MoneyUnit.EUR;
import static org.eclipse.uomo.business.money.MoneyUnit.USD;

import org.eclipse.uomo.business.internal.CurrencyUnit;
import org.eclipse.uomo.business.internal.MonetaryAmount;
import org.eclipse.uomo.business.money.MoneyAmount;
import org.eclipse.uomo.business.money.MoneyConverter;
import org.eclipse.uomo.business.money.MoneyUnit;

/**
 * @author Werner Keil
 * @version 0.9.8, $Date: 2013-05-20 $
 */
public class ICUDemo {

	/**
	 * @param args
	 *            The application arguments if required.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		// /////////////////////////////////////////////////////////////////////
		// Calculates the cost of a car trip in Europe for an American tourist.
		// /////////////////////////////////////////////////////////////////////

		@SuppressWarnings("unused")
		MoneyConverter converter = new MoneyConverter(USD, EUR, 1.4);
		CurrencyUnit currency = MoneyUnit.of("CHF");
		MonetaryAmount money = MoneyAmount.of(100, currency);
		
		System.out.println(money);
//		System.out.println(money.getCurrency());
		System.out.println(converter.convert(34.6d));
	}
}
