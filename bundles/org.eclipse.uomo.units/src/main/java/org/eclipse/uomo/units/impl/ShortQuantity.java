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

import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;

import org.eclipse.uomo.units.AbstractQuantity;

/**
 * An amount of quantity, consisting of a short and a Unit. ShortQuantity objects are immutable.
 * 
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @param <Q>
 *          The type of the quantity.
 * @version 0.3, $Date: 2017-07-30 $
 * @since 1.0
 */
final class ShortQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> {

  final short value;

  ShortQuantity(short value, Unit<Q> unit) {
    super(unit);
    this.value = value;
  }

  @Override
  public Short getValue() {
    return value;
  }

  public double doubleValue(Unit<Q> unit) {
    return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
  }

  @Override
  public long longValue(Unit<Q> unit) {
    double result = doubleValue(unit);
    if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
      throw new ArithmeticException("Overflow (" + result + ")");
    }
    return (long) result;
  }

  public Quantity<Q> add(Quantity<Q> that) {
    final Quantity<Q> converted = that.to(getUnit());
    return NumberQuantity.of(value + converted.getValue().shortValue(), getUnit());
  }

  public Quantity<Q> subtract(Quantity<Q> that) {
    final Quantity<Q> converted = that.to(getUnit());
    return NumberQuantity.of(value - converted.getValue().shortValue(), getUnit());
  }

  public Quantity<?> multiply(Quantity<?> that) {
    return NumberQuantity.of(value * that.getValue().shortValue(), getUnit().multiply(that.getUnit()));
  }

  public Quantity<Q> multiply(Number that) {
    return NumberQuantity.of(value * that.shortValue(), getUnit());
  }

  public Quantity<?> divide(Quantity<?> that) {
    return NumberQuantity.of((short) value / that.getValue().shortValue(), getUnit().divide(that.getUnit()));
  }

  @SuppressWarnings("unchecked")
  public AbstractQuantity<Q> inverse() {
    return (AbstractQuantity<Q>) NumberQuantity.of(1 / value, getUnit().inverse());
  }

  public Quantity<Q> divide(Number that) {
    return NumberQuantity.of(value / that.shortValue(), getUnit());
  }

  /*
   * (non-Javadoc)
   * 
   * @see AbstractQuantity#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (obj instanceof Quantity<?>) {
      Quantity<?> that = (Quantity<?>) obj;
      return Objects.equals(getUnit(), that.getUnit()) && Equalizer.hasEquality(value, that.getValue());
    }
    return false;
  }

}