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
package org.eclipse.uomo.units;

import java.io.Serializable;

import javax.measure.Quantity;
import javax.measure.Unit;

/**
 * Unit specialized for the Java SE platform. It extends {@link Unit} with {@linkplain Comparable} and {@linkplain Serializable }
 * 
 * @see {@link Unit}
 * @author werner
 * @param <Q>
 * @since 0.8
 */
public interface ComparableUnit<Q extends Quantity<Q>> extends Unit<Q>, Comparable<Unit<Q>>, Serializable {

  /**
   * Compares two instances of {@link Unit <Q>}, doing the conversion of unit if necessary.
   *
   * @param that
   *          the {@code Unit<Q>} to be compared with this instance.
   * @return {@code true} if {@code that < this}.
   * @throws NullPointerException
   *           if the unit is null
   */
  boolean isEquivalentOf(Unit<Q> that);

  /**
   * Indicates if this unit belongs to the set of coherent SI units (unscaled SI units).
   * 
   * The base and coherent derived units of the SI form a coherent set, designated the set of coherent SI units. The word coherent is used here in the
   * following sense: when coherent units are used, equations between the numerical values of quantities take exactly the same form as the equations
   * between the quantities themselves. Thus if only units from a coherent set are used, conversion factors between units are never required.
   * 
   * @return <code>equals(toSystemUnit())</code>
   */
  boolean isSystemUnit();
}
