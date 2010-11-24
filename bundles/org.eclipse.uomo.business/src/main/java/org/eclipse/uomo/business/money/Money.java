/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Jean-Marie Dautelle - initial API and implementation
 */
package org.eclipse.uomo.business.money;

import static org.eclipse.uomo.business.money.MoneyAmount.UNIT;

import org.eclipse.uomo.units.Measurable;
import org.unitsofmeasurement.unit.Dimension;
import org.unitsofmeasurement.unit.Unit;

/**
 * This interface represents something generally accepted as a medium of
 * exchange, a measure of value, or a means of payment. The units for money
 * quantities is of type {@link Currency}.
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:jcp@catmedia.us">Werner Keil</a>
 * @version 3.1.2 ($Revision: 227 $), $Date: 2010-10-01 00:54:55 +0200 (Fr, 01 Okt 2010) $
 * TODO rename, could be Monetary instead?
 */
public interface Money extends Measurable<Money> {

    /**
     * Holds the dimension for money quantities (dimension [$]).
     */
    public static final Dimension DIMENSION = UNIT.getDimension();

    public Unit<Money> getQuantityUnit();
}
