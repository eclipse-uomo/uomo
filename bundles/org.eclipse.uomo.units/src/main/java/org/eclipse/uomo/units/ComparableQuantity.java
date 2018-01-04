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
 * Quantity specialized for the Java SE platform. It extends {@link Quantity} with {@linkplain Comparable} and {@linkplain Serializable }
 * 
 * @see {@link Quantity}
 * @author otaviojava
 * @author werner
 * @param <Q>
 * @version 1.0.1, August 7, 2017
 * @since 0.7
 */
public interface ComparableQuantity<Q extends Quantity<Q>> extends Quantity<Q>, Comparable<Quantity<Q>>, Serializable {

  /**
   * @see Quantity#add(Quantity)
   */
  ComparableQuantity<Q> add(Quantity<Q> that);

  /**
   * @see Quantity#subtract(Quantity)
   */
  ComparableQuantity<Q> subtract(Quantity<Q> that);

  /**
   * @see Quantity#divide(Quantity)
   */
  ComparableQuantity<?> divide(Quantity<?> that);

  /**
   * @see Quantity#divide(Number)
   */
  ComparableQuantity<Q> divide(Number that);

  /**
   * @see Quantity#multiply(Quantity)
   */
  ComparableQuantity<?> multiply(Quantity<?> multiplier);

  /**
   * @see Quantity#multiply(Number)
   */
  ComparableQuantity<Q> multiply(Number multiplier);

  /**
   * @see Quantity#inverse()
   */
  ComparableQuantity<?> inverse();

  /**
   * invert and already cast to defined quantityClass
   * 
   * @param quantityClass
   *          Quantity to be converted
   * @see Quantity#inverse()
   * @see Quantity#asType(Class)
   */
  <T extends Quantity<T>> ComparableQuantity<T> inverse(Class<T> quantityClass);

  /**
   * @see Quantity#to(Unit)
   */
  ComparableQuantity<Q> to(Unit<Q> unit);

  /**
   * @see Quantity#asType(Class)
   */
  <T extends Quantity<T>> ComparableQuantity<T> asType(Class<T> type) throws ClassCastException;

  /**
   * Compares two instances of {@link Quantity <Q>}. Conversion of unit can happen if necessary
   *
   * @param that
   *          the {@code quantity<Q>} to be compared with this instance.
   * @return {@code true} if {@code that > this}.
   * @throws NullPointerException
   *           if the that is null
   */
  boolean isGreaterThan(Quantity<Q> that);

  /**
   * Compares two instances of {@link Quantity <Q>}, doing the conversion of unit if necessary.
   *
   * @param that
   *          the {@code quantity<Q>} to be compared with this instance.
   * @return {@code true} if {@code that >= this}.
   * @throws NullPointerException
   *           if the that is null
   */
  boolean isGreaterThanOrEqualTo(Quantity<Q> that);

  /**
   * Compares two instances of {@link Quantity <Q>}, doing the conversion of unit if necessary.
   *
   * @param that
   *          the {@code quantity<Q>} to be compared with this instance.
   * @return {@code true} if {@code that < this}.
   * @throws NullPointerException
   *           if the quantity is null
   */
  boolean isLessThan(Quantity<Q> that);

  /**
   * Compares two instances of {@link Quantity <Q>}, doing the conversion of unit if necessary.
   *
   * @param that
   *          the {@code quantity<Q>} to be compared with this instance.
   * @return {@code true} if {@code that < this}.
   * @throws NullPointerException
   *           if the quantity is null
   */
  boolean isLessThanOrEqualTo(Quantity<Q> that);

  /**
   * Compares two instances of {@link Quantity <Q>}, doing the conversion of unit if necessary.
   *
   * @param that
   *          the {@code quantity<Q>} to be compared with this instance.
   * @return {@code true} if {@code that < this}.
   * @throws NullPointerException
   *           if the quantity is null
   */
  boolean isEquivalentOf(Quantity<Q> that);

  /**
   * Multiply and cast the {@link ComparableQuantity}
   * 
   * @param that
   *          quantity to be multiplied
   * @param asTypeQuantity
   *          quantity to be converted
   * @return the QuantityOperations multiplied and converted
   * @see Quantity#divide(Quantity)
   * @see Quantity#asType(Class)
   * @exception NullPointerException
   */
  <T extends Quantity<T>, E extends Quantity<E>> ComparableQuantity<E> divide(Quantity<T> that, Class<E> asTypeQuantity);

  /**
   * Divide and cast the {@link ComparableQuantity}
   * 
   * @param that
   *          quantity to be divided
   * @param asTypeQuantity
   *          quantity to be converted
   * @return the QuantityOperations multiplied and converted
   * @see QuantityOperations
   * @see QuantityOperations#of(Quantity, Class)
   * @see Quantity#asType(Class)
   * @see Quantity#multiply(Quantity)
   * @exception NullPointerException
   */
  <T extends Quantity<T>, E extends Quantity<E>> ComparableQuantity<E> multiply(Quantity<T> that, Class<E> asTypeQuantity);
}
