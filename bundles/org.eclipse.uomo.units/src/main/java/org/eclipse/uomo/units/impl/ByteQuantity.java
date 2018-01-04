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
package tec.units.indriya.quantity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.units.indriya.AbstractQuantity;
import tec.units.indriya.ComparableQuantity;

/**
 * An amount of quantity, consisting of a short and a Unit. ByteQuantity objects are immutable.
 * 
 * @see AbstractQuantity
 * @see Quantity
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @param <Q>
 *          The type of the quantity.
 * @version 0.1, $Date: 2017-05-28 $
 * @since 1.0.7
 */
final class ByteQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> {

  /**
     * 
     */
  private static final long serialVersionUID = 6325849816534488248L;

  final byte value;

  ByteQuantity(byte value, Unit<Q> unit) {
    super(unit);
    this.value = value;
  }

  @Override
  public Byte getValue() {
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

  @Override
  public boolean isBig() {
    return false;
  }

  @Override
  public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx) {
    return BigDecimal.valueOf(doubleValue(unit));
  }

  @Override
  public ComparableQuantity<Q> add(Quantity<Q> that) {
    final Quantity<Q> converted = that.to(getUnit());
    return NumberQuantity.of(value + converted.getValue().byteValue(), getUnit());
  }

  @Override
  public ComparableQuantity<Q> subtract(Quantity<Q> that) {
    final Quantity<Q> converted = that.to(getUnit());
    return NumberQuantity.of(value - converted.getValue().byteValue(), getUnit());
  }

  @Override
  public ComparableQuantity<?> divide(Quantity<?> that) {
    return NumberQuantity.of((short) value / that.getValue().byteValue(), getUnit().divide(that.getUnit()));
  }

  @Override
  public ComparableQuantity<Q> divide(Number that) {
    return NumberQuantity.of(value / that.byteValue(), getUnit());
  }

  @Override
  public ComparableQuantity<?> multiply(Quantity<?> multiplier) {
    return NumberQuantity.of(value * multiplier.getValue().byteValue(), getUnit().multiply(multiplier.getUnit()));
  }

  @Override
  public ComparableQuantity<Q> multiply(Number multiplier) {
    return NumberQuantity.of(value * multiplier.byteValue(), getUnit());
  }

  @Override
  public ComparableQuantity<?> inverse() {
    return NumberQuantity.of(1 / value, getUnit().inverse());
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