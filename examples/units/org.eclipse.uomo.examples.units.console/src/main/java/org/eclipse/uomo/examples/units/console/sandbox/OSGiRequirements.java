package org.eclipse.uomo.examples.units.console.sandbox;

import org.eclipse.uomo.units.impl.system.Imperial;
import org.eclipse.uomo.units.impl.system.SI;
import org.eclipse.uomo.util.Parser;
import javax.measure.quantity.Length;
import javax.measure.spi.SystemOfUnits;
import javax.measure.Unit;
import javax.measure.UnitConverter;

/**
 * 
 * @author Werner Keil
 * @version 1.1
 * <p>
 * Thanks to Barry for his input and inspiration.
 * </p>
 */
public class OSGiRequirements {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		SystemOfUnits system = SI.getInstance();
		Unit<Length> km = SI.METRE.multiply(1000);
		Unit<Length> foot = Imperial.INCH.multiply(12);
		UnitConverter converter = km.getConverterTo(foot);
		Parser<String, Unit<?>> p = null;
		Unit<?> userDefinedUnit = (p == null) ? null : p.parse("m/s^2"); // this is not a valid UCUM expression;-)
		System.out.println("0.1 km in feet = " + converter.convert(0.1));
		System.out.println(system.getName());
		System.out.println(userDefinedUnit);
	}

}
