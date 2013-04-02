package org.eclipse.uomo.examples.units.console.sandbox;

import org.eclipse.uomo.units.SI;
import org.eclipse.uomo.units.impl.system.Imperial;
import org.eclipse.uomo.util.Parser;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

public class Sample {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// SystemOfUnits system = SI.getInstance();
		Unit<Length> km = SI.METRE.multiply(1000);
		Unit<Length> foot = Imperial.INCH.multiply(12);
		UnitConverter converter = km.getConverterTo(foot);
		Parser<String, Unit<?>> p = null;
		Unit<?> userDefinedUnit = (p == null) ? null : p.parse("m/s^2");
		System.out.println("0.1 km in feet = " + converter.convert(0.1));
		System.out.println(userDefinedUnit);
	}

}
