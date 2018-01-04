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
package org.eclipse.uomo.units.impl.format;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;

import org.eclipse.uomo.units.AbstractUnit;
import org.eclipse.uomo.units.ComparableQuantity;
import org.eclipse.uomo.units.impl.Quantities;

@SuppressWarnings({ "rawtypes", "unchecked" })
class NumberSpaceQuantityFormat extends QuantityFormat {

  private final NumberFormat numberFormat;

  private final UnitFormat unitFormat;

  NumberSpaceQuantityFormat(NumberFormat numberFormat, UnitFormat unitFormat) {
    this.numberFormat = numberFormat;
    this.unitFormat = unitFormat;
  }

  static int getFractionDigitsCount(double d) {
    if (d >= 1) { // we only need the fraction digits
      d = d - (long) d;
    }
    if (d == 0) { // nothing to count
      return 0;
    }
    d *= 10; // shifts 1 digit to left
    int count = 1;
    while (d - (long) d != 0) { // keeps shifting until there are no more
      // fractions
      d *= 10;
      count++;
    }
    return count;
  }

  @Override
  public Appendable format(Quantity<?> quantity, Appendable dest) throws IOException {
    // dest.append(numberFormat.format(quantity.getValue()));
    // if (quantity.getUnit().equals(AbstractUnit.ONE))
    // return dest;
    // dest.append(' ');
    // return unitFormat.format(quantity.getUnit(), dest);
    int fract = 0;
    if (quantity != null && quantity.getValue() != null) {
      fract = getFractionDigitsCount(quantity.getValue().doubleValue());
    }
    if (fract > 1) {
      numberFormat.setMaximumFractionDigits(fract + 1);
    }
    dest.append(numberFormat.format(quantity.getValue()));
    if (quantity.getUnit().equals(AbstractUnit.ONE))
      return dest;
    dest.append(' ');
    return unitFormat.format(quantity.getUnit(), dest);
  }

  @Override
  public ComparableQuantity<?> parse(CharSequence csq, ParsePosition cursor) throws IllegalArgumentException, ParserException {
    String str = csq.toString();
    Number number = numberFormat.parse(str, cursor);
    if (number == null)
      throw new IllegalArgumentException("Number cannot be parsed");

    Unit unit = unitFormat.parse(csq);
    return Quantities.getQuantity(number.longValue(), unit);
  }

  @Override
  ComparableQuantity<?> parse(CharSequence csq, int index) throws IllegalArgumentException, ParserException {
    return parse(csq, new ParsePosition(index));
  }

  @Override
  public ComparableQuantity<?> parse(CharSequence csq) throws IllegalArgumentException, ParserException {
    return parse(csq, 0);
  }

  private static final long serialVersionUID = 1L;

}