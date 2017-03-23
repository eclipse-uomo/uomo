package org.eclipse.uomo.examples.units.console.sandbox;

import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.impl.system.Imperial;
import org.eclipse.uomo.units.impl.system.USCustomary;
import org.eclipse.uomo.units.impl.quantity.VolumeAmount;
import org.unitsofmeasurement.quantity.Volume;

public class Beerfest {

	public static void main(String[] args) {
		IMeasure<Volume> v= new VolumeAmount(.5d, USCustomary.LITER);
		System.out.println(v);
		System.out.print(v.doubleValue(Imperial.PINT)); 
		System.out.println(" " + Imperial.PINT);
		System.out.print(v.doubleValue(USCustomary.OUNCE_LIQUID)); 
		System.out.println(" " + USCustomary.OUNCE_LIQUID);
	}

}
