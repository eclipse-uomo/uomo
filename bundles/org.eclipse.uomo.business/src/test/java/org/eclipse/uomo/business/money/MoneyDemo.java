/**
 * Copyright (c) 2005, 2010, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.business.money;

// Constants (Java 5 static import)
import static org.eclipse.uomo.units.SI.Prefix.KILO;
import static org.eclipse.uomo.units.SI.METRE;
import static org.eclipse.uomo.units.USCustomary.GALLON_LIQUID;
import static org.eclipse.uomo.units.USCustomary.LITER;
import static org.eclipse.uomo.units.USCustomary.MILE;
import static org.eclipse.uomo.business.money.CurrencyUnit.EUR;
import static org.eclipse.uomo.business.money.CurrencyUnit.USD;

import org.eclipse.uomo.business.money.CurrencyConverter;
import org.eclipse.uomo.business.money.Money;
import org.eclipse.uomo.units.Measurable;
import org.eclipse.uomo.units.impl.BaseAmount;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;

/**
 * @author Werner Keil
 * @version 0.9.5, $Date: 2010-09-11 23:59:41 +0200 (Sa, 11 Sep 2010) $
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

		// Use currency symbols instead of ISO-4217 codes.
		// UnitFormat.getInstance().label(USD, "$"); // Use "$" symbol instead
		// of currency code ("USD")
		// UnitFormat.getInstance().label(EUR, "€"); // Use "€" symbol
		// instead of currency code ("EUR")
		// UnitFormat.getInstance().label(GBP, "£"); // Use "£" symbol instead
		// of currency code ("GBP")
		// UnitFormat.getInstance().label(INR, "Rp"); // Use "Rp" instead of
		// currency code ("IRP")
		// MeasureFormat currFormat = MeasureFormat.getCurrencyFormat();
		// Sets exchange rates.
		// Currency.setReferenceCurrency(USD);
		// EUR.setExchangeRate(1.55); // 1.0 € = 1.4 $
		// GBP.setExchangeRate(2); // 1.0 £ = 2.0 $
		// INR.setExchangeRate(0.022); // 1.0Rp = ~0.022 $
		@SuppressWarnings("unused")
		CurrencyConverter converter1 = new CurrencyConverter(USD, EUR, 1.4);

		// Calculates trip cost.
		/*
		 * Measureable<?> carMileage = Measure.valueOf(20,
		 * MILE.divide(GALLON_LIQUID_US)); // 20 mi/gal. Measure<?> gazPrice =
		 * Measure.valueOf(1.2, EUR.divide(LITER)); // 1.2 €/L Measure<Length>
		 * tripDistance = Measure.valueOf(400, KILO(SI.METER)); // 400 km
		 * Measureable<Money> tripCost =
		 * tripDistance.divide(carMileage).times(gazPrice).to(USD);
		 */

		// Calculates trip cost.
		BaseAmount carMileage = new BaseAmount(20,
				MILE.divide(GALLON_LIQUID)); // 20 mi/gal.
		Measurable<?> gazPrice = new BaseAmount(1.2, EUR.divide(LITER));
		// // 1.2 EUR/L
		LengthAmount tripDistance = new LengthAmount(400, KILO(METRE)); // 400
		// km
		Measurable<Money> tripCost = (Measurable<Money>) tripDistance.divide(
				carMileage).times(gazPrice); // .to(USD);

		// Display trip.
		System.out.println(TestMessages.MoneyDemo_Car_mileage + carMileage);
		System.out.println(TestMessages.MoneyDemo_Trip_distance + tripDistance);

		// Display cost.
		System.out.print(TestMessages.MoneyDemo_Gas_price);
		System.out.println(gazPrice);
		// UFormat format = MeasureFormat.getCurrencyFormat();
		// System.out.println(format.format(gazPrice));
		// MoneyAmount mo = MoneyAmount.valueOf(100, EUR);
		// System.out.println(currFormat.format(mo));
		System.out.println(TestMessages.MoneyDemo_Trip_cost + tripCost); // + " (" +

//		System.out.println(TestMessages.MoneyDemo_Trip_cost
//				+ ((BaseAmount) tripCost).to(USD)); //$NON-NLS-1$

//		System.out.println(Messages.MoneyDemo_Trip_cost
//				+ ((BaseAmount) tripCost).to(EUR)); //$NON-NLS-1$
		// System.out.println("Trip cost = " + tripCost + " (" +
		// tripCost.to(GBP) + ")");
		// System.out.println("Trip cost = " + tripCost + " (" +
		// tripCost.to(INR) + ")");
	}
}
