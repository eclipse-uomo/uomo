/*
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units.impl;

import java.util.Comparator;

import javax.measure.Quantity;

/**
 * Comparator to sort by natural order, looking both the unit and the value.
 * 
 * @author <a href="mailto:werner@uom.technology">Werner Keil</a>
 * @author <a href="mailto:otaviopolianasantana@gmail.com">Otavio Santana</a>
 * @version 1.0.1
 * 
 * @return <b>Given:</b>
 *         <p>
 *         Quantity&lt;Time&gt; day = Quantities.getQuantity(1, Units.DAY);
 *         </p>
 *         <p>
 *         Quantity&lt;Time&gt; hours = Quantities.getQuantity(18, Units.HOUR);
 *         </p>
 *         <p>
 *         Quantity&lt;Time&gt; minutes = Quantities.getQuantity(15, Units.HOUR);
 *         </p>
 *         <p>
 *         Quantity&lt;Time&gt; seconds = Quantities.getQuantity(100, Units.HOUR);
 *         </p>
 *         will return: seconds, minutes, hours, day
 * @since 0.7
 */
public class NaturalOrder<T extends Quantity<T>> implements Comparator<Quantity<T>> {

  @Override
  public int compare(Quantity<T> q1, Quantity<T> q2) {
    if (q1.getUnit().equals(q2.getUnit())) {
      return Double.compare(q1.getValue().doubleValue(), q2.getValue().doubleValue());
    }
    return Double.compare(q1.getValue().doubleValue(), q2.to(q1.getUnit()).getValue().doubleValue());
  }
}