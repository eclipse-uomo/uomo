package org.eclipse.uomo.examples.units.console;

import static org.eclipse.uomo.units.impl.system.SI.*;

import javax.measure.quantity.Acceleration;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;
import javax.measure.Unit;

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
