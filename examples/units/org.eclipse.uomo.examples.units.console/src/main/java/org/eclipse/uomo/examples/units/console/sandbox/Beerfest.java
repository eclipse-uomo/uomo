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
		IMeasure<Volume> v2 = v.to(Imperial.PINT);
		System.out.println(v2);
		IMeasure<Volume> v3 = v.to(USCustomary.OUNCE_LIQUID);
		System.out.println(v3);
	}

}
