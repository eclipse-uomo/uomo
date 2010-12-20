package org.eclipse.uomo.examples.units.console;

import org.unitsofmeasurement.quantity.Acceleration;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.quantity.Time;
import org.unitsofmeasurement.unit.Unit;

import static org.eclipse.uomo.units.SI.*;

public class UnitsExample {
	  public static void main(String[] args) {
	    Unit<Length> distance = METRE.multiply(5);
	    Unit<Time> time = SECOND.multiply(10);
	    @SuppressWarnings("unchecked")
		Unit<Acceleration> speed = (Unit<Acceleration>) distance.divide(time);

	    System.out.println("Speed = " + speed);

//	    Unit<?> nonsense = distance.add(time);
	  }
}
