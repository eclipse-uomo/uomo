/*
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl.format;

import java.io.IOException;
import java.text.ParsePosition;

import javax.measure.MeasurementException;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;

import org.eclipse.uomo.units.AbstractQuantity;
import org.eclipse.uomo.units.AbstractUnit;
import org.eclipse.uomo.units.impl.NumberQuantity;

import tec.uom.lib.common.function.Parser;

/**
 * <p>
 * This class provides the interface for formatting and parsing {@link Quantity quantities}.
 * </p>
 * 
 * <p>
 * Instances of this class should be able to format quantities stated in {@link CompoundUnit}. See {@link #formatCompound formatCompound(...)}.
 * </p>
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.1, $Date: 2017-07-30 $
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public abstract class QuantityFormat implements Parser<CharSequence, Quantity> {

  /**
   * 
   */
  // private static final long serialVersionUID = -4628006924354248662L;

  /**
   * Holds the default format instance.
   */
  private static final QuantityFormat DEFAULT = new Standard();

  /**
   * Holds the Number-Space-Unit format instance.
   */
  // private static final QuantityFormat NUM_SPACE = new NumberSpaceUnit(NumberFormat.getInstance(), SimpleUnitFormat.getInstance());

  // TODO use it as an option (after fixing parse())

  /**
   * Returns the quantity format for the default locale. The default format assumes the quantity is composed of a decimal number and a {@link Unit}
   * separated by whitespace(s).
   * 
   * @return <code>MeasureFormat.getInstance(NumberFormat.getInstance(), UnitFormat.getInstance())</code>
   */
  public static QuantityFormat getInstance() {
    return DEFAULT;
  }

  /**
   * Formats the specified quantity into an <code>Appendable</code>.
   * 
   * @param quantity
   *          the quantity to format.
   * @param dest
   *          the appendable destination.
   * @return the specified <code>Appendable</code>.
   * @throws IOException
   *           if an I/O exception occurs.
   */
  public abstract Appendable format(Quantity<?> quantity, Appendable dest) throws IOException;

  /**
   * Parses a portion of the specified <code>CharSequence</code> from the specified position to produce an object. If parsing succeeds, then the index
   * of the <code>cursor</code> argument is updated to the index after the last character used.
   * 
   * @param csq
   *          the <code>CharSequence</code> to parse.
   * @param index
   *          the current parsing index.
   * @return the object parsed from the specified character sub-sequence.
   * @throws IllegalArgumentException
   *           if any problem occurs while parsing the specified character sequence (e.g. illegal syntax).
   */
  abstract Quantity<?> parse(CharSequence csq, int index) throws IllegalArgumentException, ParserException;

  /**
   * Convenience method equivalent to {@link #format(AbstractQuantity, Appendable)} except it does not raise an IOException.
   * 
   * @param q
   *          the quantity to format.
   * @param dest
   *          the appendable destination.
   * @return the specified <code>StringBuilder</code>.
   */
  public final StringBuilder format(Quantity<?> q, StringBuilder dest) {
    try {
      return (StringBuilder) this.format(q, (Appendable) dest);
    } catch (IOException ex) {
      throw new MeasurementException(ex); // Should not happen.
    }
  }

  /**
   * Formats an object to produce a string. This is equivalent to <blockquote> {@link #format(Unit, StringBuilder) format}<code>(unit,
   *         new StringBuilder()).toString();</code> </blockquote>
   *
   * @param obj
   *          The object to format
   * @return Formatted string.
   * @exception IllegalArgumentException
   *              if the Format cannot format the given object
   */
  public final String format(Quantity q) {
    if (q instanceof AbstractQuantity) {
      return format((AbstractQuantity<?>) q, new StringBuilder()).toString();
    } else {
      return (this.format(q, new StringBuilder())).toString();
    }
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

  // Holds standard implementation.
  private static final class Standard extends QuantityFormat {

    /**
     * 
     */
    // private static final long serialVersionUID = 2758248665095734058L;

    @Override
    public Appendable format(Quantity q, Appendable dest) throws IOException {
      Unit unit = q.getUnit();
      // if (unit instanceof CompoundUnit)
      // return formatCompound(q.doubleValue(unit),
      // (CompoundUnit) unit, dest);
      // else {

      Number number = q.getValue();
      dest.append(number.toString());
      // }
      if (q.getUnit().equals(AbstractUnit.ONE))
        return dest;
      dest.append(' ');
      return SimpleUnitFormat.getInstance().format(unit, dest);
      // }
    }

    @SuppressWarnings("unchecked")
    @Override
    Quantity<?> parse(CharSequence csq, int index) throws ParserException {
      int startDecimal = index; // cursor.getIndex();
      while ((startDecimal < csq.length()) && Character.isWhitespace(csq.charAt(startDecimal))) {
        startDecimal++;
      }
      int endDecimal = startDecimal + 1;
      while ((endDecimal < csq.length()) && !Character.isWhitespace(csq.charAt(endDecimal))) {
        endDecimal++;
      }
      Double decimal = new Double(csq.subSequence(startDecimal, endDecimal).toString());
      // cursor.setIndex(endDecimal + 1);
      int startUnit = endDecimal + 1;// csq.toString().indexOf(' ') + 1;
      Unit unit = SimpleUnitFormat.getInstance().parse(csq, new ParsePosition(startUnit));
      return NumberQuantity.of(decimal.doubleValue(), unit);
    }

    public Quantity<?> parse(CharSequence csq) throws ParserException {
      return parse(csq, 0);
    }
  }
}
