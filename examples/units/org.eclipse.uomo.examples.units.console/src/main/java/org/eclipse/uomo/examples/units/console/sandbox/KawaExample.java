package org.eclipse.uomo.examples.units.console.sandbox;

import javax.measure.Quantity;

import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.eclipse.uomo.units.impl.system.SI;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.Quantity;
import javax.measure.Unit;


public class KawaExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Unit<Length> m = SI.METRE;
		Unit<Mass> g = SI.GRAM;
		
		Unit<?> result = g.multiply(m);
		System.out.println(result);
		
		Quantity<Length> q1 = new LengthAmount(1, m);
		System.out.println(q1);
		Quantity<Mass> q2 = new MassAmount(1, g);
		System.out.println(q2);
		Quantity<?> q3 = q1.multiply(q2);
		System.out.println(q3);
//		Quantity<Length> q4 = q1.add(q2); // this fails at compile time, not at runtime
//		System.out.println(q4);
	}

}
