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
package org.eclipse.uomo.units.impl.converter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.uomo.units.AbstractConverter;

import tec.uom.lib.common.function.ValueSupplier;

/**
 * <p>
 * This class represents a converter multiplying numeric values by π (Pi).
 * </p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Pi"> Wikipedia: Pi</a>
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.0.2, July 30, 2017
 * @since 0.7
 */
public final class PiMultiplierConverter extends AbstractConverter implements ValueSupplier<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5763262154104962367L;

	private static final Logger logger = Logger.getLogger(PiMultiplierConverter.class.getName());

	/**
	 * Creates a Pi multiplier converter.
	 */
	public PiMultiplierConverter() {
	}

	@Override
	public double convert(double value) {
		return value * PI;
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
		BigDecimal pi = Pi.pi(nbrDigits);
		return value.multiply(pi, ctx).scaleByPowerOfTen(1 - nbrDigits);
	}

	@Override
	public AbstractConverter inverse() {
		return new PiDivisorConverter();
	}

	@Override
	public final String toString() {
		return "(π)";
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof PiMultiplierConverter);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean isLinear() {
		return true;
	}

	/**
	 * Pi calculation with Machin's formula.
	 * 
	 * @see <a href=
	 *      "http://en.literateprograms.org/Pi_with_Machin's_formula_(Java)" >Pi
	 *      with Machin's formula</a>
	 * 
	 */
	static final class Pi {

		private Pi() {
		}

		public static BigDecimal pi(int numDigits) {
			int calcDigits = numDigits + 10;
			return FOUR
					.multiply((FOUR.multiply(arccot(FIVE, calcDigits))).subtract(arccot(TWO_THIRTY_NINE, calcDigits)))
					.setScale(numDigits, RoundingMode.DOWN);
		}

		/*
		 * private static BigDecimal compute(int numDigits, boolean verbose) {
		 * int calcDigits = numDigits + 10;
		 * 
		 * return FOUR .multiply((FOUR.multiply(arccot(FIVE,
		 * calcDigits))).subtract(arccot(TWO_THIRTY_NINE, calcDigits)))
		 * .setScale(numDigits, RoundingMode.DOWN); }
		 */
		/** Compute arccot via the Taylor series expansion. */
		private static BigDecimal arccot(BigDecimal x, int numDigits) {
			BigDecimal unity = BigDecimal.ONE.setScale(numDigits, RoundingMode.DOWN);
			BigDecimal sum = unity.divide(x, RoundingMode.DOWN);
			BigDecimal xpower = new BigDecimal(sum.toString());
			BigDecimal term = null;
			int nTerms = 0;

			BigDecimal nearZero = BigDecimal.ONE.scaleByPowerOfTen(-numDigits);
			logger.log(Level.FINER, "arccot: ARGUMENT=" + x + " (nearZero=" + nearZero + ")");
			boolean add = false;
			// Add one term of Taylor series each time thru loop. Stop looping
			// when _term_
			// gets very close to zero.
			for (BigDecimal n = THREE; term == null || !term.equals(BigDecimal.ZERO); n = n.add(TWO)) {
				if (term != null && term.compareTo(nearZero) < 0)
					break;
				xpower = xpower.divide(x.pow(2), RoundingMode.DOWN);
				term = xpower.divide(n, RoundingMode.DOWN);
				sum = add ? sum.add(term) : sum.subtract(term);
				add = !add;
				// System.out.println("arccot: xpower=" + xpower + ", term=" +
				// term);
				logger.log(Level.FINEST, "arccot: term=" + term);
				nTerms++;
			}
			logger.log(Level.FINER, "arccot: done. nTerms=" + nTerms);
			return sum;
		}
	}

	private static final BigDecimal TWO = new BigDecimal("2");

	private static final BigDecimal THREE = new BigDecimal("3");

	private static final BigDecimal FOUR = new BigDecimal("4");

	private static final BigDecimal FIVE = new BigDecimal("5");

	private static final BigDecimal TWO_THIRTY_NINE = new BigDecimal("239");

	@Override
	public String getValue() {
		return toString();
	}
}
