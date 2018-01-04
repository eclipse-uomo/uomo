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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;

import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.AbstractQuantity;
import org.eclipse.uomo.units.ComparableQuantity;

/**
 * An amount of quantity, implementation of {@link ComparableQuantity} that uses {@link Double} as implementation of {@link Number}, this object is
 * immutable. Note: all operations which involves {@link Number}, this implementation will convert to {@link Double}.
 *
 * @param <Q>
 *          The type of the quantity.
 * @param <Q>
 *          The type of the quantity.
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author Otavio de Santana
 * @version 0.4, $Date: 2017-05-28 $
 * @see AbstractQuantity
 * @see Quantity
 * @see ComparableQuantity
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
final class DoubleQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> implements Serializable {

  private static final long serialVersionUID = 8660843078156312278L;

  final double value;

  public DoubleQuantity(double value, Unit<Q> unit) {
    super(unit);
    this.value = value;
  }

  @Override
  public Double getValue() {
    return value;
  }

  @Override
  public double doubleValue(Unit<Q> unit) {
    return (super.getUnit().equals(unit)) ? value : super.getUnit().getConverterTo(unit).convert(value);
  }

  @Override
  public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx) throws ArithmeticException {
    BigDecimal decimal = BigDecimal.valueOf(value); // TODO check value if
    // it is a BD, otherwise
    // use different
    // converter
    return (super.getUnit().equals(unit)) ? decimal : ((AbstractConverter) super.getUnit().getConverterTo(unit)).convert(decimal, ctx);
  }

  @Override
  public long longValue(Unit<Q> unit) {
    double result = doubleValue(unit);
    if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
      throw new ArithmeticException("Overflow (" + result + ")");
    }
    return (long) result;
  }

  @Override
  public ComparableQuantity<Q> add(Quantity<Q> that) {
    if (getUnit().equals(that.getUnit())) {
      return Quantities.getQuantity(value + that.getValue().doubleValue(), getUnit());
    }
    Quantity<Q> converted = that.to(getUnit());
    return Quantities.getQuantity(value + converted.getValue().doubleValue(), getUnit());
  }

  @Override
  public ComparableQuantity<Q> subtract(Quantity<Q> that) {
    if (getUnit().equals(that.getUnit())) {
      return Quantities.getQuantity(value - that.getValue().doubleValue(), getUnit());
    }
    Quantity<Q> converted = that.to(getUnit());
    return Quantities.getQuantity(value - converted.getValue().doubleValue(), getUnit());
  }

  @Override
  public ComparableQuantity<?> multiply(Quantity<?> that) {
    return new DoubleQuantity(value * that.getValue().doubleValue(), getUnit().multiply(that.getUnit()));
  }

  @Override
  public ComparableQuantity<Q> multiply(Number that) {
    return Quantities.getQuantity(value * that.doubleValue(), getUnit());
  }

  @Override
  public ComparableQuantity<?> divide(Quantity<?> that) {
    return new DoubleQuantity(value / that.getValue().doubleValue(), getUnit().divide(that.getUnit()));
  }

  @Override
  public ComparableQuantity<Q> divide(Number that) {
    return Quantities.getQuantity(value / that.doubleValue(), getUnit());
  }

  @Override
  public AbstractQuantity<Q> inverse() {
    return (AbstractQuantity<Q>) Quantities.getQuantity(1d / value, getUnit().inverse());
  }

  @Override
  public boolean isBig() {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof Quantity<?>) {
      Quantity<?> that = (Quantity<?>) obj;
      return Objects.equals(getUnit(), that.getUnit()) && Equalizer.hasEquality(value, that.getValue());
    }
    return false;
  }
}
