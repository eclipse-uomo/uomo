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
package org.eclipse.uomo.units.impl.quantity;

import org.eclipse.uomo.units.impl.BaseQuantity;
import org.unitsofmeasurement.quantity.AmountOfSubstance;
import org.unitsofmeasurement.unit.Unit;

/**
 * Represents the number of elementary entities (molecules, for example) of a substance.
 * The metric system unit for this quantity is "mol" (mole).
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.4 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 */
public class SubstanceAmount extends BaseQuantity<AmountOfSubstance> {
	
	public SubstanceAmount(Number number, Unit<AmountOfSubstance> unit) {
		super(number, unit);
	}
}
