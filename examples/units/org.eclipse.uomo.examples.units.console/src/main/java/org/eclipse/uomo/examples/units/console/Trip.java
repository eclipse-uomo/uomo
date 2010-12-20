/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console;

import org.eclipse.uomo.units.impl.quantity.LengthAmount;

/**
 * @author paul.morrison
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * @param <L>
 */
class Trip {
	
	TripLeg tripleg[];
		
	public Trip(int no) {
		
		tripleg = new TripLeg[no];		
		
	}
	
	public void addLeg(int no, TripLeg t) {
		
		tripleg[no] = t;
	}
		
	public TripLeg getLeg(int no) {
		
		return tripleg[no];
	}	
	
	public LengthAmount getTotalDist(){
		
	LengthAmount x = tripleg[0].getDist();
	
	for (int i = 1; i < tripleg.length - 1; i++) {
		
//		x = x.add(tripleg[i].getDist());
	   } 
	return x;   
	}
	
}

	/**
	 * @author paul.morrison
	 *
	 * To change this generated comment edit the template variable "typecomment":
	 * Window>Preferences>Java>Templates.
	 * To enable and disable the creation of type comments go to
	 * Window>Preferences>Java>Code Generation.
	 */
	class TripLeg {
		
		String fromAirport;	
	    String toAirport;
	    LengthAmount distance; 

	    public TripLeg(String fA, String tA, LengthAmount dist) {
	    	fromAirport = fA;
	    	toAirport = tA;
	    	distance = dist;
	    }
	    
	    public LengthAmount getDist() {
	    	return distance;
	    }


}
