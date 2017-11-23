/**
 * Copyright (c) 2005, 2014, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units;

import java.math.BigDecimal;

import org.eclipse.uomo.units.impl.QuantityFactoryImpl;
import javax.measure.quantity.Dimensionless;
import javax.measure.Quantity;
import javax.measure.Unit;

/**
 * An amount of quantity, consisting of a Number and a Unit. QuantityAmount
 * objects are immutable.
 * 
 * @see java.lang.Number
 * @see numberUnit
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @param <Q>
 *            The type of the quantity.
 * @version 1.3.4 ($Revision: 212 $), $Date: 2011-09-12 01:20:44 +0200 (Mo, 12
 *          Sep 2011) $ XXX rename to Amount, AbstractAmount or numberAmount?
 *          FIXME Bug 338334 overwrite equals()
 * @deprecated use AbstractQuantity
 */
public abstract class QuantityAmount<Q extends Quantity<Q>>
		implements Quantity<Q> {
	
	private final Number number;
	private final Unit<Q> unit;
	
	/**
	 * Holds a dimensionless number of one (exact).
	 */
	public static final Quantity<Dimensionless> ONE =
			QuantityFactoryImpl.getInstance(Dimensionless.class).create(
					BigDecimal.ONE, AbstractUnit.ONE);
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		QuantityAmount<Q> other = (QuantityAmount<Q>) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(number);
	}

	/**
	 * Indicates if this number is exact.
	 */
	private boolean isExact;

	/**
	 * Holds the exact value (when exact) stated in this number unit.
	 */
	// private long _exactValue;

	/**
	 * Holds the minimum value stated in this number unit. For inexact
	 * numbers: _minimum < _maximum
	 */
	// private double _minimum;

	/**
	 * Holds the maximum value stated in this number unit. For inexact
	 * numbers: _maximum > _minimum
	 */
	// private double _maximum;

	protected QuantityAmount(Number number, Unit unit) {
		this.unit = unit;
		this.number = number;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.measure.Quantity#doubleValue(org.unitsofnumberment.unit
	 * .Unit)
	 */
    public double doubleValue(Unit<Q> unit) {
        return (getUnit().equals(unit)) ? getValue().doubleValue() : getUnit().getConverterTo(unit).convert(getValue().doubleValue());
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.measure.Quantity#longValue(org.unitsofnumberment.unit
	 * .Unit)
	 */
	public long longValue(Unit<Q> unit) {
        double result = doubleValue(unit);
        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
            throw new ArithmeticException("Overflow (" + result + ")");
        }
        return (long) result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.measure.quantity.Quantity#unit()
	 */
	@Override
	public Unit<Q> getUnit() {
		return unit;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.measure.quantity.Quantity#value()
	 */
	@Override
	public Number getValue() {
		return number;
	}

	/**
	 * Indicates if this number amount is exact. An exact amount is guarantee
	 * exact only when stated in this number unit (e.g.
	 * <code>this.longValue()</code>); stating the amount in any other unit may
	 * introduce conversion errors.
	 * 
	 * @return <code>true</code> if this number is exact; <code>false</code>
	 *         otherwise.
	 */
	public boolean isExact() {
		return isExact;
	}
}
