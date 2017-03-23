/**
 * Copyright (c) 2005, 2011, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console.sandbox;

import static org.eclipse.uomo.units.SI.Prefix.MILLI;
import static org.eclipse.uomo.units.SI.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.impl.quantity.IonizingRadiationAmount;
import org.unitsofmeasurement.quantity.IonizingRadiation;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * 
 * @see <a
 *      href="http://www.nema.ne.gov/technological/dose-limits.html">NEMA:
 *      Radiological Emergency Preparedness</a>
 */
public class RadiologicalEmergencyPreparedness {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map <IMeasure<IonizingRadiation>, String> repMap = new HashMap<IMeasure<IonizingRadiation>, String>();
		IonizingRadiationAmount ira = new IonizingRadiationAmount(100, MILLI(ROENTGEN));
		
		repMap.put(ira, SandboxMessages.REP_100mR);
		ira = new IonizingRadiationAmount(1, ROENTGEN);
		repMap.put(ira, SandboxMessages.REP_1R);
		ira = new IonizingRadiationAmount(2.5, ROENTGEN);
		repMap.put(ira, SandboxMessages.REP_2dot5R);
		
		for (IMeasure<IonizingRadiation> dosimeterLimit : repMap.keySet()) {			
			System.out.println(dosimeterLimit + " :: " + repMap.get(dosimeterLimit));
		}
		
	}

}
