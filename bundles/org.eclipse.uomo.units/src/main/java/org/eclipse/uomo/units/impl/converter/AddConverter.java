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
import org.unitsofmeasurement.unit.UnitConverter;


/**
 * <p> This class represents a converter adding a constant offset
 *     to numeric values (<code>double</code> based).</p>
 *
 * <p> Instances of this class are immutable.</p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 *
 * @version 1.2 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 */
public final class AddConverter extends AbstractConverter {

    /**
     * For cross-version compatibility.
     */
    private static final long serialVersionUID = 8088797685241019815L;

    /**
     * Holds the offset.
     */
    private final double offset;

    /**
     * Creates an add converter with the specified offset.
     *
     * @param  offset the offset value.
     * @throws IllegalArgumentException if offset is <code>0.0</code>
     *         (would result in identity converter).
     */
    public AddConverter(double offset) {
        if (offset == 0.0) {
            throw new IllegalArgumentException("Would result in identity converter");
        }
        this.offset = offset;
    }

    /**
     * Returns the offset value for this add converter.
     *
     * @return the offset value.
     */
    public double getOffset() {
        return offset;
    }

    @Override
    public UnitConverter concatenate(UnitConverter converter) {
        if (converter instanceof AddConverter) {
            double newOffset = offset + ((AddConverter) converter).offset;
            return newOffset == 0.0 ? IDENTITY : new AddConverter(newOffset);
        } else {
            return super.concatenate((AbstractConverter)converter);
        }
    }

    @Override
    public AddConverter inverse() {
        return new AddConverter(-offset);
    }

    @Override
    public double convert(double value) {
        return value + offset;
    }

    @Override
    public BigDecimal convert(BigDecimal value, MathContext ctx) throws ArithmeticException {
         return value.add(BigDecimal.valueOf(offset), ctx);
    }

	@Override
	public Number convert(Number value) {
		return BigDecimal.valueOf(value.doubleValue()).add(BigDecimal.valueOf(offset));
	}

    @Override
    public final String toString() {
        return "AddConverter(" + offset + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AddConverter)) {
            return false;
        }
        AddConverter that = (AddConverter) obj;
        return this.offset == that.offset;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(offset);
        return (int) (bits ^ (bits >>> 32));
    }

    @Override
    public boolean isLinear() {
        return false;
    }
}
