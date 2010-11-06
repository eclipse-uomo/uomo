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
package org.eclipse.uomo.units.impl.quantity;

import java.math.BigDecimal;
import java.math.MathContext;

import org.eclipse.uomo.units.Measurable;
import org.unitsofmeasurement.quantity.Time;
import org.unitsofmeasurement.unit.IncommensurableException;
import org.unitsofmeasurement.unit.UnconvertibleException;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

import com.ibm.icu.util.TimeUnit;
import com.ibm.icu.util.TimeUnitAmount;

/**
 * Represents a period of existence or persistence. The metric system unit for
 * this quantity is "s" (second).
 * 
 * @author <a href="mailto:jcp@catmedia.us">Werner Keil</a>
 * @version 1.3.1 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 */
public class TimeAmount extends TimeUnitAmount implements Measurable<Time> {

	public TimeAmount(Number number, Unit<Time> unit) {
		super(number, (TimeUnit) unit);
	}

	public TimeAmount(double number, Unit<Time> unit) {
		super(number, (TimeUnit) unit);
	}

	/**
	 * Get the unit (convenience to avoid cast).
	 * 
	 * @draft UOMo 0.5
	 * @provisional This API might change or be removed in a future release.
	 */
	@SuppressWarnings("unchecked")
	public Unit<Time> getQuantityUnit() {
		return (Unit<Time>) getUnit();
	}

	/**
	 * Returns the value of this quantity as <code>double</code> stated in the
	 * specified unit. This method is recommended over <code>
	 * q.getUnit().getConverterTo(unit).convert(q.getNumber()).doubleValue()</code>
	 * 
	 * @param unit
	 *            the unit in which the returned value is stated.
	 * @return the value of this quantity when stated in the specified unit.
	 */
	@Override
	public double doubleValue(Unit<Time> unit) {
		Unit<Time> myUnit = getQuantityUnit();
		try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			return converter.convert(getNumber().doubleValue());
		} catch (UnconvertibleException e) {
			throw e;
		} catch (IncommensurableException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Returns the value of this quantity as <code>long</code> stated in the
	 * specified unit. This method is recommended over <code>
	 * q.getUnit().getConverterTo(unit).convert(q.getNumber()).longValue()</code>
	 * 
	 * @param unit
	 *            the unit in which the returned value is stated.
	 * @return the value of this quantity when stated in the specified unit.
	 */
	@Override
	public long longValue(Unit<Time> unit) {
		Unit<Time> myUnit = getQuantityUnit();
		try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			return (converter.convert(
					BigDecimal.valueOf(getNumber().longValue()),
					MathContext.DECIMAL128)).longValue();
		} catch (UnconvertibleException e) {
			throw e;
		} catch (IncommensurableException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public Measurable<Time> plus(Measurable<Time> that) {
		return new TimeAmount(super.getNumber().doubleValue()
				+ ((TimeUnitAmount) that).getNumber().doubleValue(),
				that.getQuantityUnit());
	}

	@Override
	public Measurable<Time> minus(Measurable<Time> that) {
		return new TimeAmount(super.getNumber().doubleValue()
				- ((TimeUnitAmount) that).getNumber().doubleValue(),
				that.getQuantityUnit());
	}
	
	@Override
	public Measurable<Time> divide(Measurable<Time> that) {
		@SuppressWarnings("unchecked")
		Unit<Time> unit = (Unit<Time>) getQuantityUnit().divide(that.getQuantityUnit());
		
		// FIXME include number division
//		return new TimeAmount((BigDecimal) getNumber())
//				.divide((BigDecimal) ((Measure)that).getNumber()), unit);
		return new TimeAmount(getNumber(), unit);
	}

	@Override
	public Measurable<?> times(Measurable<?> that) {
		@SuppressWarnings("unchecked")
		Unit<Time> unit = (Unit<Time>) getQuantityUnit().multiply(that.getQuantityUnit());
		
		// FIXME include number division
//		return new TimeAmount((BigDecimal) getNumber())
//				.divide((BigDecimal) ((Measure)that).getNumber()), unit);
		return new TimeAmount(getNumber(), unit);
	}
}
