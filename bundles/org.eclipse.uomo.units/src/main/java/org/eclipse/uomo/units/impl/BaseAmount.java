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
import java.math.BigInteger;
import java.math.MathContext;

import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.QuantityAmount;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;

/**
 * Represents a generic quantity amount.
 * 
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.2, ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 */
public class BaseAmount<Q extends Quantity<Q>> extends QuantityAmount<Q> implements Comparable<BaseAmount<Q>>{
	
	public BaseAmount(Number number, Unit<Q> unit) {
		super(number, (MeasureUnit) unit);
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
	public static BaseAmount<?> valueOf(Number value, Unit<?> unit) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		BaseAmount<?> amount = new BaseAmount(value, unit);
		return amount;
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public IMeasure<Q> add(IMeasure<Q> that) {
		// Measure<BigDecimal, ?> amount = that.to((Unit) getCurrency());
		return new BaseAmount(this.getNumber().doubleValue()
				+ ((Measure)that).getNumber().doubleValue(), getQuantityUnit());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IMeasure<Q> substract(IMeasure<Q> that) {
		return new BaseAmount(this.getNumber().doubleValue()
				- ((Measure)that).getNumber().doubleValue(), getQuantityUnit());

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IMeasure<?> multiply(IMeasure<?> that) {
		Unit<?> unit = getQuantityUnit().multiply(that.getQuantityUnit());
		return (IMeasure<Q>)valueOf((getNumber().doubleValue() *
				((Measure)that).getNumber().doubleValue()), unit);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IMeasure<?> divide(IMeasure<?> that) {
		Unit<?> unit = getQuantityUnit().divide(that.getQuantityUnit());
		return (IMeasure<Q>) valueOf((getNumber().doubleValue() /
				((Measure)that).getNumber().doubleValue()), unit);
	}
	
    public IMeasure<Q> to(Unit<Q> unit) {
        return to(unit, MathContext.DECIMAL32);
    }

    @SuppressWarnings("unchecked")
	public IMeasure<Q> to(Unit<Q> unit, MathContext ctx) {
        if (this.getUnit().equals(unit))
            return this;
        UnitConverter cvtr = this.getQuantityUnit().getConverterTo(unit);
        if (cvtr == AbstractConverter.IDENTITY)
            return (IMeasure<Q>) valueOf(this.getNumber(), unit);
        return (IMeasure<Q>) valueOf(convert(this.getNumber(), cvtr, ctx), unit);
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

	@Override
	public int compareTo(BaseAmount<Q> o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
