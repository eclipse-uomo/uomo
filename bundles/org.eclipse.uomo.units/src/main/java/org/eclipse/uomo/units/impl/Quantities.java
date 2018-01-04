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

import static org.eclipse.uomo.units.impl.format.FormatBehavior.LOCALE_NEUTRAL;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParsePosition;
import java.util.Objects;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;

import org.eclipse.uomo.units.ComparableQuantity;
import org.eclipse.uomo.units.impl.format.QuantityFormat;

/**
 * Singleton class for accessing {@link Quantity} instances.
 * 
 * @author werner
 * @author otaviojava
 * @version 1.2, December 24, 2017
 * @since 0.7
 */
public final class Quantities {
	  /**
	   * Private singleton constructor.
	   */
	  private Quantities() {
	  }

	  /**
	   * Returns the {@link #valueOf(java.math.BigDecimal, javax.measure.unit.Unit) decimal} quantity of unknown type corresponding to the specified
	   * representation. This method can be used to parse dimensionless quantities.<br/>
	   * <code>
	   *     Quantity<Dimensionless> proportion = Quantities.getQuantity("0.234").asType(Dimensionless.class);
	   * </code>
	   *
	   * <p>
	   * Note: This method handles only Locale-neutral quantity formatting and parsing are handled by the {@link QuantityFormat} class and its subclasses.
	   * </p>
	   *
	   * @param csq
	   *          the decimal value and its unit (if any) separated by space(s).
	   * @return <code>QuantityFormat.getInstance(LOCALE_NEUTRAL).parse(csq, new ParsePosition(0))</code>
	   */
	  public static ComparableQuantity<?> getQuantity(CharSequence csq) {
	    try {
	      return QuantityFormat.getInstance(LOCALE_NEUTRAL).parse(csq, new ParsePosition(0));
	    } catch (ParserException e) {
	      throw new IllegalArgumentException(e.getParsedString());
	    }
	  }

	  /**
	   * Returns the scalar measurement. When the {@link Number} was {@link BigDecimal} or {@link BigInteger} will uses {@link DecimalQuantity}, when the
	   * {@link Number} was {@link Double} will {@link DoubleQuantity} otherwise will {@link NumberQuantity}. in the specified unit.
	   * 
	   * @param value
	   *          the measurement value.
	   * @param unit
	   *          the measurement unit.
	   * @return the corresponding <code>numeric</code> measurement.
	   * @throws NullPointerException
	   *           when value or unit were null
	   */
	  public static <Q extends Quantity<Q>> ComparableQuantity<Q> getQuantity(Number value, Unit<Q> unit) {
	    Objects.requireNonNull(value);
	    Objects.requireNonNull(unit);
	    if (Double.class.isInstance(value)) {
	      return new DoubleQuantity<>(Double.class.cast(value), unit);
	    } else if (BigDecimal.class.isInstance(value)) {
	      return new DecimalQuantity<>(BigDecimal.class.cast(value), unit);
	    } else if (BigInteger.class.isInstance(value)) {
	      return new DecimalQuantity<>(new BigDecimal(BigInteger.class.cast(value)), unit);
	    }
	    return new NumberQuantity<>(value, unit);
	  }
	}