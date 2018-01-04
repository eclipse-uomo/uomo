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
import org.eclipse.uomo.units.internal.MeasureAmount;
import javax.measure.quantity.Dimensionless;
import javax.measure.Quantity;
import javax.measure.Unit;
import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;

/**
 * An amount of quantity, consisting of a Number and a Unit. QuantityAmount
 * objects are immutable.
 * 
 * @see java.lang.Number
 * @see MeasureUnit
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @param <Q>
 *            The type of the quantity.
 * @version 1.3.4 ($Revision: 212 $), $Date: 2011-09-12 01:20:44 +0200 (Mo, 12
 *          Sep 2011) $ XXX rename to Amount, AbstractAmount or MeasureAmount?
 *          FIXME Bug 338334 overwrite equals()
 * @deprecated use AbstractQuantity
 */
public abstract class QuantityAmount<Q extends Quantity<Q>>
		implements IMeasure<Q> {
	
	private final Measure measure;
	private final Unit<Q> unit;
	
	/**
	 * Holds a dimensionless measure of one (exact).
	 */
	public static final Quantity<Dimensionless> ONE =
			QuantityFactoryImpl.getInstance(Dimensionless.class).create(
					BigDecimal.ONE, AbstractUnit.ONE);
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((measure == null) ? 0 : measure.hashCode());
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
		if (measure == null) {
			if (other.measure != null)
				return false;
		} else if (!measure.equals(other.measure))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(measure);
	}

	/**
	 * Indicates if this measure is exact.
	 */
	private boolean isExact;

	/**
	 * Holds the exact value (when exact) stated in this measure unit.
	 */
	// private long _exactValue;

	/**
	 * Holds the minimum value stated in this measure unit. For inexact
	 * measures: _minimum < _maximum
	 */
	// private double _minimum;

	/**
	 * Holds the maximum value stated in this measure unit. For inexact
	 * measures: _maximum > _minimum
	 */
	// private double _maximum;

	protected QuantityAmount(Number number, Unit unit, MeasureUnit mUnit) {
		this.unit = unit;
		measure = MeasureAmount.of(number, mUnit);
	}
	
	protected QuantityAmount(Number number, Unit unit) {
		this(number, unit, null);
	}

	/**
	 * Returns the <b>ICU4J</b> <type>Measure</type> object.
	 * @return the backing measure.
	 */
	public Measure getMeasure() {
		return measure;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.uomo.units.IMeasure#doubleValue(javax.measure.unit
	 * .Unit)
	 */
	@Override
    public double doubleValue(Unit<Q> unit) {
        return (internalUnit().equals(unit)) ? value().doubleValue() : unit().getConverterTo(unit).convert(value().doubleValue());
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.uomo.units.IMeasure#longValue(javax.measure.unit
	 * .Unit)
	 */
	@Override
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
	 * @see javax.measure.Quantity#unit()
	 */
	@Override
	public Unit<Q> unit() {
		return unit;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.measure.Quantity#value()
	 */
	@Override
	public Number value() {
		return getNumber();
	}

	/**
	 * Indicates if this measure amount is exact. An exact amount is guarantee
	 * exact only when stated in this measure unit (e.g.
	 * <code>this.longValue()</code>); stating the amount in any other unit may
	 * introduce conversion errors.
	 * 
	 * @return <code>true</code> if this measure is exact; <code>false</code>
	 *         otherwise.
	 */
	public boolean isExact() {
		return isExact;
	}
	
	public Number getValue() {
		return getNumber();
	}
	
	public Unit<Q> getUnit() {
		return unit();
	}

	/**
	 * Get the unit (convenience to avoid cast).
	 * 
	 * @provisional This API might change or be removed in a future release.
	 */
	private final MeasureUnit internalUnit() {
		return measure.getUnit();
	}
	
	protected final Number getNumber() {
		return measure.getNumber();
	}
}
