package org.eclipse.uomo.examples.units.console;

import javax.measure.quantity.Speed;
import javax.measure.quantity.Length;
import javax.measure.quantity.Time;
import javax.measure.Unit;

import static si.uom.SI.*;

public class UnitsExample {
	  public static void main(String[] args) {
	    Unit<Length> distance = METRE.multiply(5);
	    Unit<Time> time = SECOND.multiply(10);
	    @SuppressWarnings("unchecked")
		Unit<Speed> speed = (Unit<Speed>) distance.divide(time);

	    System.out.println("Speed = " + speed);

//	    Unit<?> nonsense = distance.add(time);
	  }
}
