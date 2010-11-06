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
package org.eclipse.uomo.units.impl;

import java.math.BigDecimal;
import java.math.MathContext;

import org.eclipse.uomo.units.AbstractConverter;
import org.unitsofmeasurement.unit.UnitConverter;

/**
 * <p> This class represents a converter multiplying numeric values by a
 *     constant scaling factor (<code>double</code> based).</p>
 *
 * <p> Instances of this class are immutable.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:jcp@catmedia.us">Werner Keil</a>
 * @version 1.5.1 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 */
public final class MultiplyConverter extends AbstractConverter {
    /** The serialVersionUID */
    private static final long serialVersionUID = 6497743504427978825L;

    /**
     * Holds the scale factor.
     */
    private final double factor;

    /**
     * Creates a multiply converter with the specified scale factor.
     *
     * @param  factor the scaling factor.
     * @throws IllegalArgumentException if coefficient is <code>1.0</code>
     *        (would result in identity converter)
     */
    public MultiplyConverter(double factor) {
        if (factor == 1.0)
            throw new IllegalArgumentException("Would result in identity converter");
        this.factor = factor;
    }

    /**
     * Returns the scale factor of this converter.
     *
     * @return the scale factor.
     */
    public double getFactor() {
        return factor;
    }

    @Override
    public UnitConverter inverse() {
        return new MultiplyConverter(1.0 / factor);
    }

    @Override
    public double convert(double value) {
        return value * factor;
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
          return value.multiply(BigDecimal.valueOf(factor), ctx);
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
    public final String toString() {
        return "MultiplyConverter("+ factor + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MultiplyConverter))
            return false;
        MultiplyConverter that = (MultiplyConverter) obj;
        return this.factor == that.factor;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(factor);
    return (int)(bits ^ (bits >>> 32));
    }

    @Override
    public boolean isLinear() {
        return true;
    }

	@Override
	public UnitConverter concatenate(UnitConverter converter) {
      if (converter instanceof MultiplyConverter) {
	      double newfactor = factor * ((MultiplyConverter) converter).factor;
	      return newfactor == 1.0 ? IDENTITY : new MultiplyConverter(newfactor);
      } else
    	  return super.concatenate(converter);
	}
}
