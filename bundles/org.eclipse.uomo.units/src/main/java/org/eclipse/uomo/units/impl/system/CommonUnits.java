package org.eclipse.uomo.units.impl.system;

import static org.eclipse.uomo.units.SI.METRES_PER_SECOND;

import org.eclipse.uomo.units.AbstractSystemOfUnits;
import javax.measure.quantity.Velocity;
import javax.measure.spi.SystemOfUnits;
import javax.measure.Unit;

public class CommonUnits extends AbstractSystemOfUnits {

	private CommonUnits() {
		
	}
	
	private static final CommonUnits INSTANCE = new CommonUnits();
	
	public String getName() {
		return "Common Units";
	}

	/**
	 * A unit of velocity expressing the number of international {@link #KILOMETRE
	 * kilometres} per {@link #HOUR hour} (abbreviation <code>kph</code>).
	 */
	public static final Unit<Velocity> KILOMETRES_PER_HOUR = addUnit(
			METRES_PER_SECOND.multiply(0.277778d)).asType(Velocity.class);
	
	/**
	 * Returns the unique instance of this class.
	 * 
	 * @return the Imperial instance.
	 */
	public static SystemOfUnits getInstance() {
		return INSTANCE;
	}
	
}
