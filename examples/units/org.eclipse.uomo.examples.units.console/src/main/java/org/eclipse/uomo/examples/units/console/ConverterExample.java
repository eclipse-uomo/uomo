package org.eclipse.uomo.examples.units.console;

import static org.eclipse.uomo.units.SI.*;
import static org.eclipse.uomo.units.SI.Prefix.KILO;
import static org.eclipse.uomo.units.USCustomary.*;

import org.unitsofmeasurement.unit.UnitConverter;

public class ConverterExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    // Conversion between units.
		UnitConverter conv = KILO(METRE).getConverterTo(MILE);
	    System.out.println(conv.convert(10.0));
	    // Retrieval of the system unit (identifies the measurement type).
	    System.out.println(REVOLUTION.divide(MINUTE).getSystemUnit());
	    // Dimension checking (allows/disallows conversions)
	    System.out.println(ELECTRON_VOLT.isCompatible(WATT.multiply(HOUR)));
	    // Retrieval of the unit dimension (depends upon the current model).
	    System.out.println(ELECTRON_VOLT.getDimension());
	    System.out.println(KILOGRAM.equals(KILO(GRAM)));
	    System.out.println(KILOGRAM.equals(KILO(OUNCE)));
	}

}
