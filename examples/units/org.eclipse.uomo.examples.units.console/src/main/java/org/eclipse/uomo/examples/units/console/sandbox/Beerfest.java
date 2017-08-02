package org.eclipse.uomo.examples.units.console.sandbox;

import javax.measure.Quantity;
import org.eclipse.uomo.units.impl.system.Imperial;
import org.eclipse.uomo.units.impl.system.USCustomary;
import org.eclipse.uomo.units.impl.quantity.VolumeAmount;
import javax.measure.quantity.Volume;

public class Beerfest {

	public static void main(String[] args) {
		Quantity<Volume> v= new VolumeAmount(.5d, USCustomary.LITER);
		System.out.println(v);
		System.out.println(v.to(Imperial.PINT)); 
		System.out.println(v.to(USCustomary.OUNCE_LIQUID)); 
	}

}
