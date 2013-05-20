/**
 * Copyright (c) 2005, 2013, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.business.console.money;

// Constants (Java 5 static import)
import static org.eclipse.uomo.units.impl.system.USCustomary.GALLON_LIQUID;
import static org.eclipse.uomo.units.impl.system.USCustomary.LITER;
import static org.eclipse.uomo.units.impl.system.USCustomary.MILE;
import static org.eclipse.uomo.units.SI.Prefix.KILO;
import static org.eclipse.uomo.units.SI.METRE;
import static org.eclipse.uomo.business.money.MoneyUnit.EUR;
import static org.eclipse.uomo.business.money.MoneyUnit.GBP;
import static org.eclipse.uomo.business.money.MoneyUnit.USD;
import static org.eclipse.uomo.units.IndianPrefix.LAKH;

import org.eclipse.uomo.business.internal.CurrencyUnit;
import org.eclipse.uomo.business.internal.MonetaryAmount;
import org.eclipse.uomo.business.money.MoneyAmount;
import org.eclipse.uomo.business.money.MoneyConverter;
import org.eclipse.uomo.business.money.MoneyCurrency;
import org.eclipse.uomo.business.money.MoneyUnit;
import org.eclipse.uomo.business.types.IMoney;
import org.eclipse.uomo.examples.business.console.internal.DemoMessages;
import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.QuantityAmount;
import org.eclipse.uomo.units.impl.BaseAmount;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author Werner Keil
 * @version 0.9.7, $Date: 2013-03-08 19:29:41 +0200 $
 */
public class MoneyDemo {

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
		System.out.println(money.getCurrency());
	}
}
