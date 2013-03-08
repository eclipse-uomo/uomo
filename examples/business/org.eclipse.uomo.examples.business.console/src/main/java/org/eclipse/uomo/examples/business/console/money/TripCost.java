/*
 * Copyright (c) 2005, 2013, Werner Keil and others.
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
import static org.eclipse.uomo.business.money.MonetaryUnits.INR;
import static org.eclipse.uomo.business.money.MoneyUnit.EUR;
import static org.eclipse.uomo.business.money.MoneyUnit.GBP;
import static org.eclipse.uomo.business.money.MoneyUnit.USD;

import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.unit.UnitFormat;

import org.eclipse.uomo.business.money.MoneyUnit;
import org.eclipse.uomo.business.types.IMoney;
import org.eclipse.uomo.units.QuantityAmount;
import org.eclipse.uomo.units.impl.BaseAmount;
import org.eclipse.uomo.units.impl.format.LocalUnitFormatImpl;

/**
 * @author Werner Keil
 * @version 0.9.1, 2013-03-08
 */
public class TripCost {
  
	
	/**
	 * @param args The application arguments if required.
	 */
	public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////////
        // Calculates the cost of a car trip in Europe for an American tourist.
        ///////////////////////////////////////////////////////////////////////
		
        // Use currency symbols instead of ISO-4217 codes.
//		LocalUnitFormatImpl.getInstance().label(USD, "$"); // Use "$" symbol instead of currency code ("USD")
//		LocalUnitFormatImpl.getInstance().label(EUR, "€"); // Use "€" symbol instead of currency code ("EUR")
//		LocalUnitFormatImpl.getInstance().label(GBP, "£"); // Use "£" symbol instead of currency code ("GBP")
//		LocalUnitFormatImpl.getInstance().label(INR, "Rp"); // Use "Rp" instead of currency code ("IRP")
        
        // Sets exchange rates.
//        Currency.setReferenceCurrency(USD);
//        EUR.setExchangeRate(1.55); // 1.0 € = 1.4 $
//        GBP.setExchangeRate(2); // 1.0 £ = 2.0 $
        //INR.setExchangeRate(0.022); // 1.0Rp = ~0.022 $
        
        //java.util.Currency utilCurr = java.util.Currency.getInstance("USD");
        
        // Calculates trip cost.
        /*
        IMeasure<?> carMileage        = Measure.valueOf(20, MILE.divide(GALLON_LIQUID_US)); // 20 mi/gal.
        IMeasure<?> gazPrice          = Measure.valueOf(1.2, EUR.divide(LITER)); // 1.2 €/L
        IMeasure<Length> tripDistance = Measure.valueOf(400, KILO(SI.METER)); // 400 km
        IMeasure<Money>  tripCost     = tripDistance.divide(carMileage).times(gazPrice).to(USD);
		*/
        
        // Calculates trip cost.
        QuantityAmount<?> carMileage        = BaseAmount.valueOf(20, MILE.divide(GALLON_LIQUID)); // 20 mi/gal.
        QuantityAmount<?> gazPrice          = BaseAmount.valueOf(1.2, EUR.divide(LITER)); // 1.2 €/L
        QuantityAmount<Length> tripDistance = BaseAmount.valueOf(400, KILO(METRE)); // 400 km
//        QuantityAmount<IMoney>  tripCost    = tripDistance.divide(carMileage).multiply(gazPrice).to(USD);

        
        // Display units.
        System.out.println("Mileage: " + carMileage);
        
        // Displays cost.
//        System.out.println("Trip cost = " + tripCost + " (" + tripCost.to(EUR) + ")");
//        System.out.println("Trip cost = " + tripCost + " (" + tripCost.to(GBP) + ")");
//        System.out.println("Trip cost = " + tripCost + " (" + tripCost.to(INR) + ")");
        
        //System.out.println(utilCurr.getSymbol());
	}

	/*
	 1.00 CZK=0.0640254 USD
	Czech Republic Koruny 	  	United States Dollars
	1 CZK = 0.0640254 USD 	  	1 USD = 15.6188 CZK
	 */
}
