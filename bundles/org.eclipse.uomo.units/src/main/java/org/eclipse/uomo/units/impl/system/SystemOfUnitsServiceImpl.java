/**
 * Copyright (c) 2005, 2013, Werner Keil, JScience and others.
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

import org.eclipse.uomo.units.SI;
import javax.measure.service.SystemOfUnitsService;
import javax.measure.spi.SystemOfUnits;

/**
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.6.1, $Date$
 */
public class SystemOfUnitsServiceImpl implements SystemOfUnitsService {
	
	final Map<String, SystemOfUnits> souMap = new HashMap<String, SystemOfUnits>();

	public SystemOfUnitsServiceImpl() {
		souMap.put(SI.class.getSimpleName(), SI.getInstance());
		souMap.put(CommonUnits.class.getSimpleName(), CommonUnits.getInstance());
		souMap.put(USCustomary.class.getSimpleName(), USCustomary.getInstance());
	}
	
	public Collection<SystemOfUnits> getSystemsOfUnits() {
		return souMap.values();
	}
	
	@Override
	public SystemOfUnits getSystemOfUnits() {
		return getSystemOfUnits(SI.class.getSimpleName());
	}

	@Override
	public SystemOfUnits getSystemOfUnits(String name) {
		return souMap.get(name);
	}

}
