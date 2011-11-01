package org.eclipse.uomo.units.impl.system;

import static org.eclipse.uomo.units.SI.METRES_PER_SECOND;

import org.eclipse.uomo.units.AbstractSystemOfUnits;
import org.unitsofmeasurement.quantity.Velocity;
import org.unitsofmeasurement.unit.Unit;

public class CommonUnits extends AbstractSystemOfUnits {

	public String getName() {
		return "Common Units";
	}

	/**
	 * A unit of velocity expressing the number of international {@link #KILOMETRE
	 * kilometres} per {@link #HOUR hour} (abbreviation <code>kph</code>).
	 */
	public static final Unit<Velocity> KILOMETRES_PER_HOUR = addUnit(
			METRES_PER_SECOND.multiply(0.277778d)).asType(Velocity.class);
	
	
	
}
