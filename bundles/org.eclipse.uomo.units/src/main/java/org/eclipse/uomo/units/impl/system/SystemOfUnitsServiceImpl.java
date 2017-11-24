/**
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units.impl.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.measure.spi.SystemOfUnits;
import javax.measure.spi.SystemOfUnitsService;

import org.eclipse.uomo.units.impl.system.Units;

/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.7.2, 2017-11-24
 */
public class SystemOfUnitsServiceImpl implements SystemOfUnitsService {
	
	final Map<String, SystemOfUnits> souMap = new HashMap<>();

	public SystemOfUnitsServiceImpl() {
		//souMap.put(SI.class.getSimpleName(), SI.getInstance());
		souMap.put(Units.class.getSimpleName(), Units.getInstance());
		//souMap.put(USCustomary.class.getSimpleName(), USCustomary.getInstance());
	}
	
	public Collection<SystemOfUnits> getAvailableSystemsOfUnits() {
		return souMap.values();
	}
	
	@Override
	public SystemOfUnits getSystemOfUnits() {
		return getSystemOfUnits(Units.class.getSimpleName());
	}

	@Override
	public SystemOfUnits getSystemOfUnits(String name) {
		return souMap.get(name);
	}

}
