/*
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil 
 *    - initial API and implementation
 */
package org.eclipse.uomo.units;

import java.util.Comparator;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Dimensionless;

import org.eclipse.uomo.units.impl.NaturalOrder;
import org.eclipse.uomo.units.impl.NumberQuantity;
import org.eclipse.uomo.units.impl.format.QuantityFormat;

import tec.uom.lib.common.function.UnitSupplier;
import tec.uom.lib.common.function.ValueSupplier;

/**
 * <p>
 * This class represents the immutable result of a scalar measurement stated in a known unit.
 * </p>
 * 
 * <p>
 * <code>
 *         public static final Quantity&lt;Speed&gt; C = NumberQuantity.parse("299792458 m/s").asType(Speed.class);
 *         // Speed of Light (exact).
 *    </code>
 * </p>
 * 
 * <p>
 * Quantities can be converted to different units.<br>
 * <code>
 *         Quantity&lt;Speed&gt; milesPerHour = C.to(MILES_PER_HOUR); // Use double implementation (fast).
 *         System.out.println(milesPerHour);
 * 
 *         &gt; 670616629.3843951 m/h
 *     </code>
 * </p>
 * 
 * <p>
 * Applications may sub-class {@link AbstractQuantity} for particular quantity types.<br>
 * <code>
 *         // Quantity of type Mass based on double primitive types.<br>
 * public class MassAmount extends AbstractQuantity&lt;Mass&gt; {<br>
 * private final double kilograms; // Internal SI representation.<br>
 * private Mass(double kg) { kilograms = kg; }<br>
 * public static Mass of(double value, Unit&lt;Mass&gt; unit) {<br>
 * return new Mass(unit.getConverterTo(SI.KILOGRAM).convert(value));<br>
 * }<br>
 * public Unit&lt;Mass&gt; getUnit() { return SI.KILOGRAM; }<br>
 * public Double getValue() { return kilograms; }<br>
 * ...<br>
 * }<br>
 * <p>
 * // Complex numbers measurements.<br>
 * public class ComplexQuantity
 * &lt;Q extends Quantity&gt;extends AbstractQuantity
 * &lt;Q&gt;{<br>
 * public Complex getValue() { ... } // Assuming Complex is a Number.<br>
 * ...<br>
 * }<br>
 * <br>
 * // Specializations of complex numbers measurements.<br>
 * public final class Current extends ComplexQuantity&lt;ElectricCurrent&gt; {...}<br>
 * public final class Tension extends ComplexQuantity&lt;ElectricPotential&gt; {...} <br>
 * </code>
 * </p>
 * 
 * <p>
 * All instances of this class shall be immutable.
 * </p>
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.4, December 21, 2017
 * @since 0.6
 */
public abstract class AbstractQuantity<Q extends Quantity<Q>> implements Quantity<Q>, Comparable<Quantity<Q>>, UnitSupplier<Q>, ValueSupplier<Number> {

  /**
     * 
     */
  // private static final long serialVersionUID = -4993173119977931016L;

  private final Unit<Q> unit;

  /**
   * Holds a dimensionless quantity of none (exact).
   */
  public static final Quantity<Dimensionless> NONE = NumberQuantity.of(0, AbstractUnit.ONE);

  /**
   * Holds a dimensionless quantity of one (exact).
   */
  public static final Quantity<Dimensionless> ONE = NumberQuantity.of(1, AbstractUnit.ONE);

  /**
   * constructor.
   */
  protected AbstractQuantity(Unit<Q> unit) {
    this.unit = unit;
  }

  /**
   * Returns the measurement numeric value.
   *
   * @return the measurement value.
   */
  public abstract Number getValue();

  /**
   * Returns the measurement unit.
   *
   * @return the measurement unit.
   */
  public Unit<Q> getUnit() {
    return unit;
  }

  /**
   * Convenient method equivalent to {@link #to(javax.measure.unit.Unit) to(this.getUnit().toSI())}.
   *
   * @return this measure or a new measure equivalent to this measure but stated in SI units.
   * @throws ArithmeticException
   *           if the result is inexact and the quotient has a non-terminating decimal expansion.
   */
  public Quantity<Q> toSI() {
    return to(this.getUnit().getSystemUnit());
  }

  /**
   * Returns this measure after conversion to specified unit. The default implementation returns <code>Measure.valueOf(doubleValue(unit), unit)</code>
   * . If this measure is already stated in the specified unit, then this measure is returned and no conversion is performed.
   *
   * @param unit
   *          the unit in which the returned measure is stated.
   * @return this measure or a new measure equivalent to this measure but stated in the specified unit.
   * @throws ArithmeticException
   *           if the result is inexact and the quotient has a non-terminating decimal expansion.
   */
  public Quantity<Q> to(Unit<Q> unit) {
    if (unit.equals(this.getUnit())) {
      return this;
    }
    return NumberQuantity.of(doubleValue(unit), unit);
  }

  /**
   * Compares this measure to the specified Measurement quantity. The default implementation compares the {@link AbstractQuantity#doubleValue(Unit)}
   * of both this measure and the specified Measurement stated in the same unit (this measure's {@link #getUnit() unit}).
   *
   * @return a negative integer, zero, or a positive integer as this measure is less than, equal to, or greater than the specified Measurement
   *         quantity.
   * @see {@link NaturalOrder}
   */
  public int compareTo(Quantity<Q> that) {
    final Comparator<Quantity<Q>> comparator = new NaturalOrder<Q>();
    return comparator.compare(this, that);
  }

  /**
   * Compares this measure against the specified object for <b>strict</b> equality (same unit and same amount).
   *
   * <p>
   * Similarly to the {@link BigDecimal#equals} method which consider 2.0 and 2.00 as different objects because of different internal scales,
   * measurements such as <code>Measure.valueOf(3.0, KILOGRAM)</code> <code>Measure.valueOf(3, KILOGRAM)</code> and
   * <code>Quantities.getQuantity("3 kg")</code> might not be considered equals because of possible differences in their implementations.
   * </p>
   *
   * <p>
   * To compare measures stated using different units or using different amount implementations the {@link #compareTo compareTo} or
   * {@link #equals(javax.measure.Measurement, double, javax.measure.unit.Unit) equals(Measurement, epsilon, epsilonUnit)} methods should be used.
   * </p>
   *
   * @param obj
   *          the object to compare with.
   * @return <code>this.getUnit.equals(obj.getUnit())
   *         &amp;&amp; this.getValue().equals(obj.getValue())</code>
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AbstractQuantity<?>)) {
      return false;
    }
    AbstractQuantity<?> that = (AbstractQuantity<?>) obj;
    return this.getUnit().equals(that.getUnit()) && this.getValue().equals(that.getValue());
  }

  /**
   * Compares this measure and the specified Measurement to the given accuracy. Measurements are considered approximately equals if their absolute
   * differences when stated in the same specified unit is less than the specified epsilon.
   *
   * @param that
   *          the Measurement to compare with.
   * @param epsilon
   *          the absolute error stated in epsilonUnit.
   * @param epsilonUnit
   *          the epsilon unit.
   * @return <code>abs(this.doubleValue(epsilonUnit) - that.doubleValue(epsilonUnit)) &lt;= epsilon</code>
   */
  public boolean equals(AbstractQuantity<Q> that, double epsilon, Unit<Q> epsilonUnit) {
    return Math.abs(this.doubleValue(epsilonUnit) - that.doubleValue(epsilonUnit)) <= epsilon;
  }

  /**
   * Returns the hash code for this quantity.
   *
   * @return the hash code value.
   */
  @Override
  public int hashCode() {
    return getUnit().hashCode() + getValue().hashCode();
  }

  protected abstract boolean isBig();

  /**
   * Returns the <code>String</code> representation of this quantity. The string produced for a given quantity is always the same; it is not affected
   * by locale. This means that it can be used as a canonical string representation for exchanging quantity, or as a key for a Hashtable, etc.
   * Locale-sensitive quantity formatting and parsing is handled by the {@link QuantityFormat} class and its subclasses.
   *
   * @return <code>UnitFormat.getInternational().format(this)</code>
   */
  @Override
  public String toString() {
    // return MeasureFormat.getStandard().format(this); TODO improve
    // MeasureFormat
    // return String.valueOf(getValue()) + " " + String.valueOf(getUnit());
    return QuantityFormat.getInstance().format(this);
  }

  public abstract double doubleValue(Unit<Q> unit) throws ArithmeticException;

  protected long longValue(Unit<Q> unit) throws ArithmeticException {
    double result = doubleValue(unit);
    if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
      throw new ArithmeticException("Overflow (" + result + ")");
    }
    return (long) result;
  }

  protected float floatValue(Unit<Q> unit) {
    return (float) doubleValue(unit);
  }

  /**
   * Casts this quantity to a parameterized unit of specified nature or throw a <code>ClassCastException</code> if the dimension of the specified
   * quantity and this measure unit's dimension do not match. For example: <br>
   * <code>
   *     Measure&lt;Length&gt; length = Quantities.getQuantity("2 km").asType(Length.class);
   * </code>
   *
   * @param type
   *          the quantity class identifying the nature of the quantity.
   * @return this quantity parameterized with the specified type.
   * @throws ClassCastException
   *           if the dimension of this unit is different from the specified quantity dimension.
   * @throws UnsupportedOperationException
   *           if the specified quantity class does not have a public static field named "UNIT" holding the SI unit for the quantity.
   * @see Unit#asType(Class)
   */
  @SuppressWarnings("unchecked")
  public final <T extends Quantity<T>> Quantity<T> asType(Class<T> type) throws ClassCastException {
    this.getUnit().asType(type); // Raises ClassCastException is dimension
    // mismatches.
    return (Quantity<T>) this;
  }

  /**
   * Returns the quantity of unknown type corresponding to the specified representation. This method can be used to parse dimensionless quantities.<br/>
   * <code>
   *     Quantity<Dimensionless> proportion = AbstractQuantity.parse("0.234").asType(Dimensionless.class);
   * </code>
   *
   * <p>
   * Note: This method handles only {@link org.eclipse.uomo.units.SimpleUnitFormat.UnitFormat#getStandard standard} unit format.
   * </p>
   *
   * @param csq
   *          the decimal value and its unit (if any) separated by space(s).
   * @return <code>QuantityFormat.getInstance().parse(csq)</code>
   */
  public static Quantity<?> parse(CharSequence csq) {
    return QuantityFormat.getInstance().parse(csq);
  }

  /**
   * Utility class for number comparison and equality
   */
  protected static final class Equalizer {

    /**
     * Converts a number to {@link Double}
     *
     * @param value
     *          the value to be converted
     * @return the value converted
     */
    public static Double toDouble(Number value) {
      if (Double.class.isInstance(value)) {
        return Double.class.cast(value);
      }
      return value.doubleValue();
    }

    /**
     * Check if the both value has equality number, in other words, 1 is equals to 1.0000 and 1.0.
     * 
     * If the first value is a <type>Number</type> of either <type>Double</type>, <type>Float</type>, <type>Integer</type>, <type>Long</type>,
     * <type>Short</type> or <type>Byte</type> it is compared using the respective <code>*value()</code> method of <type>Number</type>. Otherwise it
     * is checked, if {@link Double#compareTo(Object)} is equal to zero.
     *
     * @param valueA
     *          the value a
     * @param valueB
     *          the value B
     * @return {@link Double#compareTo(Object)} == zero
     */
    public static boolean hasEquality(Number valueA, Number valueB) {
      Objects.requireNonNull(valueA);
      Objects.requireNonNull(valueB);

      if (valueA instanceof Double && valueB instanceof Double) {
        return valueA.doubleValue() == valueB.doubleValue();
      } else if (valueA instanceof Float && valueB instanceof Float) {
        return valueA.floatValue() == valueB.floatValue();
      } else if (valueA instanceof Integer && valueB instanceof Integer) {
        return valueA.intValue() == valueB.intValue();
      } else if (valueA instanceof Long && valueB instanceof Long) {
        return valueA.longValue() == valueB.longValue();
      } else if (valueA instanceof Short && valueB instanceof Short) {
        return valueA.shortValue() == valueB.shortValue();
      } else if (valueA instanceof Byte && valueB instanceof Byte) {
        return valueA.byteValue() == valueB.byteValue();
      }
      return toDouble(valueA).compareTo(toDouble(valueB)) == 0;
    }
  }
}