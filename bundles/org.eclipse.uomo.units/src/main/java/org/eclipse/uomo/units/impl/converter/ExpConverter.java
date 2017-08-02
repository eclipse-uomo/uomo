/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl.converter;

import java.math.BigDecimal;
import java.math.MathContext;

import org.eclipse.uomo.units.AbstractConverter;

/**
 * <p>
 * This class represents a exponential converter of limited precision. Such
 * converter is typically used to create inverse of logarithmic unit.
 * 
 * <p>
 * Instances of this class are immutable.
 * </p>
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.1 ($Revision: 132 $), $Date: 2010-08-10 07:04:41 +0100 (Di, 10 Aug 2010) $
 */
public final class ExpConverter extends AbstractConverter {

	/** The serialVersionUID */
	private static final long serialVersionUID = -1862583888012861945L;

	/**
	 * Holds the logarithmic base.
	 */
	private final double base;

	/**
	 * Holds the natural logarithm of the base.
	 */
	private final double logOfBase;

	/**
	 * Creates a logarithmic converter having the specified base.
	 * 
	 * @param base
	 *            the logarithmic base (e.g. <code>Math.E</code> for the Natural
	 *            Logarithm).
	 */
	public ExpConverter(double base) {
		this.base = base;
		this.logOfBase = Math.log(base);
	}

	/**
	 * Returns the exponential base of this converter.
	 * 
	 * @return the exponential base (e.g. <code>Math.E</code> for the Natural
	 *         Exponential).
	 */
	public double getBase() {
		return base;
	}

	@Override
	public AbstractConverter inverse() {
		return new LogConverter(base);
	}

	@Override
	public final String toString() {
		return "ExpConverter(" + base + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ExpConverter))
			return false;
		ExpConverter that = (ExpConverter) obj;
		return this.base == that.base;
	}

	@Override
	public int hashCode() {
		long bits = Double.doubleToLongBits(base);
		return (int) (bits ^ (bits >>> 32));
	}

	@Override
	public double convert(double value) {
		return Math.exp(logOfBase * value);
	}

	public BigDecimal convert(BigDecimal value, MathContext ctx)
			throws ArithmeticException {
		return BigDecimal.valueOf(convert(value.doubleValue())); // Reverts to
																	// double
																	// conversion.
	}

	@Override
	public Number convert(Number value) {
		if (value instanceof BigDecimal) {
			return convert((BigDecimal) value, MathContext.DECIMAL128);
		} else {
			return convert(value.doubleValue());
		}
	}

	@Override
	public boolean isLinear() {
		return false;
	}
}
