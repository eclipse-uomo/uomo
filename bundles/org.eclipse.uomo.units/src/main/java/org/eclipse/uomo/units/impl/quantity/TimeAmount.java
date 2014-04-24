/**
 * Copyright (c) 2005, 2011, Werner Keil, Ikayzo and others.
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
import java.math.BigInteger;
import java.math.MathContext;

import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.AbstractUnit;
import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.impl.BaseQuantity;
import org.eclipse.uomo.units.impl.converter.RationalConverter;
import org.unitsofmeasurement.quantity.Time;
import org.unitsofmeasurement.unit.IncommensurableException;
import org.unitsofmeasurement.unit.UnconvertibleException;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

/**
 * Represents a period of existence or persistence. The metric system unit for
 * this quantity is "s" (second).
 * 
 * Due to an incompatible private constructor in ICU4J TimeUnit, this uses BaseUnit.
 * TODO add conversions with ICU4J TimeUnitAmount where necessary
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.6 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 */
public final class TimeAmount extends BaseQuantity<Time> implements Time {

	public TimeAmount(Number number, Unit<Time> unit) {
		super(number, unit);
	}

	/**
	 * Get the unit (convenience to avoid cast).
	 * 
	 * @draft UOMo 0.5
	 * @provisional This API might change or be removed in a future release.
	 */
	public AbstractUnit<Time> unit() {
		return (AbstractUnit<Time>) super.unit();
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
	public double doubleValue(Unit<Time> unit) {
		Unit<Time> myUnit = unit();
		try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			return converter.convert(getValue().doubleValue());
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
	public long longValue(Unit<Time> unit) {
		Unit<Time> myUnit = unit();
		try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			return (converter.convert(
					BigDecimal.valueOf(getValue().longValue()),
					MathContext.DECIMAL128)).longValue();
		} catch (UnconvertibleException e) {
			throw e;
		} catch (IncommensurableException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public TimeAmount add(IMeasure<Time> that) {
		return new TimeAmount(super.getValue().doubleValue()
				+ ((BaseQuantity<Time>) that).getValue().doubleValue(),
				that.unit());
	}

	public TimeAmount substract(IMeasure<Time> that) {
		return new TimeAmount(super.getValue().doubleValue()
				- ((BaseQuantity<Time>) that).getValue().doubleValue(),
				that.unit());
	}
	
//	public IMeasure<Time> divide(IMeasure<?> that) {
//		@SuppressWarnings("unchecked")
//		Unit<Time> unit = (Unit<Time>) unit().divide(that.unit());
		
		// FIXME include number division
//		return new TimeAmount((BigDecimal) getNumber())
//				.divide((BigDecimal) ((Measure)that).getNumber()), unit);
//		return new TimeAmount(getNumber(), unit);
//	}

	@SuppressWarnings({ "unchecked" })
	public TimeAmount multiply(IMeasure<?> that) {
		Unit<Time> unit = (Unit<Time>) unit().multiply(that.unit());
		
		// FIXME include number division
//		return new TimeAmount((BigDecimal) getNumber())
//				.divide((BigDecimal) ((Measure)that).getNumber()), unit);
		return new TimeAmount(getValue(), unit);
	}

	/**
	 * Returns the amount corresponding to the specified value
	 * and unit.
	 * 
	 * @param value
	 *            the value stated in the specified unit.
	 * @param unit
	 *            the unit in which the value is stated.
	 * @return the corresponding amount.
	 */
	public static TimeAmount valueOf(Number value, AbstractUnit<Time> unit) {
		TimeAmount amount = new TimeAmount(value, unit);
		return amount;
	}
	
	public TimeAmount to(Unit<Time> unit) {
		return to(unit, MathContext.DECIMAL32);
	}
	
	public TimeAmount to(Unit<Time> unit, MathContext ctx) {
        if (this.unit().equals(unit))
            return this;
        UnitConverter cvtr = this.unit().getConverterTo(unit);
        if (cvtr == AbstractConverter.IDENTITY)
            return (TimeAmount) of(this.getValue().intValue(), unit);
        return (TimeAmount) of(convert(this.getValue(), cvtr, ctx).intValue(), unit);
    }

    // Try to convert the specified value.
    private static Number convert(Number value, UnitConverter cvtr, MathContext ctx) {
        if (cvtr instanceof RationalConverter) { // Try converting through Field methods.
            RationalConverter rCvtr = (RationalConverter) cvtr;
            BigInteger dividend = rCvtr.getDividend();
            BigInteger divisor = rCvtr.getDivisor();
            if (dividend.abs().compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)
                throw new ArithmeticException("Multiplier overflow");
            if (divisor.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0)
                throw new ArithmeticException("Divisor overflow");
            return (value.longValue() * dividend.longValue()) / (divisor.longValue());
        } else if (cvtr instanceof AbstractConverter.Compound && cvtr.isLinear()) { // Do it in two parts.
            AbstractConverter.Compound compound = (AbstractConverter.Compound) cvtr;
            Number firstConversion = convert(value, compound.getRight(), ctx);
            Number secondConversion = convert(firstConversion, compound.getLeft(), ctx);
            return secondConversion;
        } else { // Try using BigDecimal as intermediate.
            BigDecimal decimalValue = BigDecimal.valueOf(value.doubleValue());
            BigDecimal newValue = cvtr.convert(decimalValue, ctx);
            return newValue;
//            if (((FieldNumber)value) instanceof Decimal)
//                return (N)((FieldNumber)Decimal.valueOf(newValue));
//            if (((FieldNumber)value) instanceof Float64)
//                return (N)((FieldNumber)Float64.valueOf(newValue.doubleValue()));
//            throw new ArithmeticException(
//                    "Generic amount conversion not implemented for amount of type " + value.getClass());
        }
    }
}
