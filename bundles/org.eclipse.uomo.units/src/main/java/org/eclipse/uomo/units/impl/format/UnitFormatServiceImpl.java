/**
 * Copyright (c) 2005, 2010, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units.impl.format;

import java.util.Locale;

import org.eclipse.uomo.units.AbstractFormat;
import org.unitsofmeasurement.service.UnitFormatService;
import org.unitsofmeasurement.unit.UnitFormat;

import com.ibm.icu.util.ULocale;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.5 ($Revision: 308 $), $Date: 2010-11-02 15:56:52 +0000 (Di, 02 Nov 2010) $
 */
public class UnitFormatServiceImpl implements UnitFormatService {

	@Override
	public UnitFormat getUnitFormat() {
		return AbstractFormat.getUnitFormat();
	}

	@Override
	public UnitFormat getUnitFormat(String name) {
		return getUnitFormat();
	}

	@Override
	public UnitFormat getUnitFormat(Locale locale) {
		return AbstractFormat.getUnitFormat(ULocale.forLocale(locale));
	}

}
