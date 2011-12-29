package org.eclipse.uomo.examples.units.console.sandbox;

import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.SI;
import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.quantity.Mass;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.Unit;


public class KawaExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Unit<Length> m = SI.METRE;
		Unit<Mass> g = SI.GRAM;
		
		Unit<?> result = g.multiply(m);
		System.out.println(result);
		
		IMeasure<Length> q1 = new LengthAmount(1, m);
		System.out.println(q1);
		IMeasure<Mass> q2 = new MassAmount(1, g);
		System.out.println(q2);
		Quantity<?> q3 = q1.multiply(q2);
		System.out.println(q3);
//		Quantity<Length> q4 = q1.add(q2); // this fails at compile time, not at runtime
//		System.out.println(q4);
	}

}
