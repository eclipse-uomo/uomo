/**
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl.quantity;

import org.eclipse.uomo.units.impl.BaseQuantity;
import javax.measure.quantity.Radioactivity;
import javax.measure.Unit;

/**
 * Represents radioactivity. The metric system unit for this quantity is "Bq"
 * (Becquerel).
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.3, $Date: 2017-07-30 $
 */
public class RadioactivityAmount extends BaseQuantity<Radioactivity>
		implements Radioactivity {

	public RadioactivityAmount(Number number, Unit<Radioactivity> unit) {
		super(number, unit);
	}
}
