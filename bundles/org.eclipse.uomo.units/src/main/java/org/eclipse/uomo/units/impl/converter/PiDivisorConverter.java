/*
 * Copyright (c) 2005, 2010, Werner Keil and others.
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

import javax.measure.UnitConverter;

import org.eclipse.uomo.units.AbstractConverter;

import tec.uom.lib.common.function.ValueSupplier;

/**
 * <p>
 * This class represents a converter dividing numeric values by π (Pi).
 * </p>
 *
 * <p>
 * This class is package private, instances are created using the
 * {@link PiMultiplierConverter#inverse()} method.
 * </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.0, October 11, 2016
 * @since 0.7
 */
final class PiDivisorConverter extends AbstractConverter implements ValueSupplier<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5052794216568914141L;

	/**
	 * Creates a Pi multiplier converter.
	 */
	public PiDivisorConverter() {
	}

	@Override
	public double convert(double value) {
		return value / PI;
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
	public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
		int nbrDigits = ctx.getPrecision();
		if (nbrDigits == 0)
			throw new ArithmeticException("Pi multiplication with unlimited precision");
		BigDecimal pi = PiMultiplierConverter.Pi.pi(nbrDigits);
		return value.divide(pi, ctx).scaleByPowerOfTen(nbrDigits - 1);
	}

	@Override
	public UnitConverter inverse() {
		return new PiMultiplierConverter();
	}

	@Override
	public final String toString() {
		return "(1/π)";
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof PiDivisorConverter);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean isLinear() {
		return true;
	}

	@Override
	public String getValue() {
		return toString();
	}
}
