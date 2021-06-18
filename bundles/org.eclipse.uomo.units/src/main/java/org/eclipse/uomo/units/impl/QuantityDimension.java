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

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;

import org.eclipse.uomo.units.AbstractUnit;
import org.eclipse.uomo.units.impl.system.Units;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * This class represents a quantity dimension (dimension of a physical quantity).
 * </p>
 *
 * <p>
 * The dimension associated to any given quantity are given by the published {@link DimensionService} instances. For convenience, a static method
 * {@link QuantityDimension#of(Class)} aggregating the results of all {@link DimensionService} instances is provided.<br>
 * <br>
 * <code>
 * Dimension speedDimension = QuantityDimension.of(Speed.class);
 * </code>
 * </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.0.2, $Date: 2016-10-18 $
 * @since 1.0
 */
public final class QuantityDimension implements Dimension {
  private static final Logger logger = Logger.getLogger(QuantityDimension.class.getName());

  /**
	 * 
	 */
  // private static final long serialVersionUID = 123289037718650030L;

  /**
   * Holds dimensionless.
   */
  public static final Dimension NONE = new QuantityDimension(AbstractUnit.ONE);

  /**
   * Holds length dimension (L).
   */
  public static final Dimension LENGTH = new QuantityDimension('L');

  /**
   * Holds mass dimension (M).
   */
  public static final Dimension MASS = new QuantityDimension('M');

  /**
   * Holds time dimension (T).
   */
  public static final Dimension TIME = new QuantityDimension('T');

  /**
   * Holds electric current dimension (I).
   */
  public static final Dimension ELECTRIC_CURRENT = new QuantityDimension('I');

  /**
   * Holds temperature dimension (Θ).
   */
  public static final Dimension TEMPERATURE = new QuantityDimension('\u0398');

  /**
   * Holds amount of substance dimension (N).
   */
  public static final Dimension AMOUNT_OF_SUBSTANCE = new QuantityDimension('N');

  /**
   * Holds luminous intensity dimension (J).
   */
  public static final Dimension LUMINOUS_INTENSITY = new QuantityDimension('J');

  /**
   * Holds the pseudo unit associated to this dimension.
   */
  private final Unit<?> pseudoUnit;

  /**
   * Returns the physical dimension having the specified symbol.
   *
   * @param symbol
   *          the associated symbol.
   */
  @SuppressWarnings("rawtypes")
  QuantityDimension(char symbol) {
    pseudoUnit = new BaseUnit("[" + symbol + ']', NONE);
  }

  /**
   * Returns the dimension for the specified symbol.
   *
   * @param sambol
   *          the quantity symbol.
   * @return the dimension for the given symbol.
   * @since 1.0
   * @deprecated Use parse() instead
   */
  public static QuantityDimension getInstance(char symbol) {
    return new QuantityDimension(symbol);
  }

  /**
   * Returns the dimension for the specified quantity type by aggregating the results of {@link DimensionService} or <code>null</code> if the
   * specified quantity is unknown.
   *
   * @param quantityType
   *          the quantity type.
   * @return the dimension for the quantity type or <code>null</code>.
   * @since 1.0
   * @deprecated use of()
   */
  public static <Q extends Quantity<Q>> Dimension getInstance(Class<Q> quantityType) {
    return of(quantityType);
  }

  /**
   * Returns the dimension for the specified symbol.
   *
   * @param sambol
   *          the quantity symbol.
   * @return the dimension for the given symbol.
   * @since 1.0.2
   */
  public static Dimension parse(char symbol) {
    return new QuantityDimension(symbol);
  }

  /**
   * Returns the dimension for the specified quantity type by aggregating the results of {@link DimensionService} or <code>null</code> if the
   * specified quantity is unknown.
   *
   * @param quantityType
   *          the quantity type.
   * @return the dimension for the quantity type or <code>null</code>.
   * @since 1.0.2
   */
  public static <Q extends Quantity<Q>> Dimension of(Class<Q> quantityType) {
    // TODO: Track services and aggregate results (register custom
    // types)
    Unit<Q> siUnit = Units.getInstance().getUnit(quantityType);
    if (siUnit == null)
      logger.log(Level.FINER, "Quantity type: " + quantityType + " unknown"); // we're logging but probably FINER is
    // enough?
    return (siUnit != null) ? siUnit.getDimension() : null;
  }

  /**
   * Constructor from pseudo-unit (not visible).
   *
   * @param pseudoUnit
   *          the pseudo-unit.
   */
  private QuantityDimension(Unit<?> pseudoUnit) {
    this.pseudoUnit = pseudoUnit;
  }

  /**
   * Returns the product of this dimension with the one specified. If the specified dimension is not a physics dimension, then
   * <code>that.multiply(this)</code> is returned.
   *
   * @param that
   *          the dimension multiplicand.
   * @return <code>this * that</code>
   * @since 1.0
   */
  public Dimension multiply(Dimension that) {
    return (that instanceof QuantityDimension) ? this.multiply((QuantityDimension) that) : this.multiply(that);
  }

  /**
   * Returns the product of this dimension with the one specified.
   *
   * @param that
   *          the dimension multiplicand.
   * @return <code>this * that</code>
   * @since 1.0
   */
  public QuantityDimension multiply(QuantityDimension that) {
    return new QuantityDimension(this.pseudoUnit.multiply(that.pseudoUnit));
  }

  /**
   * Returns the quotient of this dimension with the one specified.
   *
   * @param that
   *          the dimension divisor.
   * @return <code>this.multiply(that.pow(-1))</code>
   * @since 1.0
   */
  public Dimension divide(Dimension that) {
    return this.multiply(that.pow(-1));
  }

  /**
   * Returns the quotient of this dimension with the one specified.
   *
   * @param that
   *          the dimension divisor.
   * @return <code>this.multiply(that.pow(-1))</code>
   * @since 1.0
   */
  public QuantityDimension divide(QuantityDimension that) {
    return this.multiply(that.pow(-1));
  }

  /**
   * Returns this dimension raised to an exponent.
   *
   * @param n
   *          the exponent.
   * @return the result of raising this dimension to the exponent.
   * @since 1.0
   */
  public final QuantityDimension pow(int n) {
    return new QuantityDimension(this.pseudoUnit.pow(n));
  }

  /**
   * Returns the given root of this dimension.
   *
   * @param n
   *          the root's order.
   * @return the result of taking the given root of this dimension.
   * @throws ArithmeticException
   *           if <code>n == 0</code>.
   * @since 1.0
   */
  public final QuantityDimension root(int n) {
    return new QuantityDimension(this.pseudoUnit.root(n));
  }

  /**
   * Returns the fundamental (base) dimensions and their exponent whose product is this dimension or <code>null</code> if this dimension is a
   * fundamental dimension.
   *
   * @return the mapping between the base dimensions and their exponent.
   * @since 1.0
   */
  @SuppressWarnings("rawtypes")
  public Map<? extends Dimension, Integer> getBaseDimensions() {
    Map<? extends Unit, Integer> pseudoUnits = pseudoUnit.getBaseUnits();
    if (pseudoUnits == null)
      return null;
    final Map<QuantityDimension, Integer> baseDimensions = new HashMap<QuantityDimension, Integer>();
    for (Map.Entry<? extends Unit, Integer> entry : pseudoUnits.entrySet()) {
      baseDimensions.put(new QuantityDimension(entry.getKey()), entry.getValue());
    }
    return baseDimensions;
  }

  @Override
  public String toString() {
    return pseudoUnit.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof QuantityDimension) {
      QuantityDimension other = (QuantityDimension) obj;
      return (pseudoUnit == other.pseudoUnit) || (pseudoUnit != null && pseudoUnit.equals(other.pseudoUnit));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return pseudoUnit == null ? 0 : pseudoUnit.hashCode();
  }
}
