/**
 * Copyright (c) 2005, 2010, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Jean-Marie Dautelle - initial API and implementation
 */
package org.eclipse.uomo.business.money;

import java.math.BigInteger;
import java.math.MathContext;
import java.text.FieldPosition;
import java.text.NumberFormat;

import org.eclipse.uomo.business.types.IMoney;
import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.IMeasure;
import org.eclipse.uomo.units.QuantityAmount;
import org.eclipse.uomo.units.impl.RationalConverter;
import org.unitsofmeasurement.unit.IncommensurableException;
import org.unitsofmeasurement.unit.UnconvertibleException;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.CurrencyAmount;

/**
 * This class represents an amount of money specified in a given
 * {@link Currency} (convenience method).
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.2 ($Revision: 206 $), $Date: 2010-09-11 23:59:41 +0200 (Sa, 11 Sep 2010) $
 */
public class MoneyAmount extends CurrencyAmount implements IMoney, Comparable<IMoney> {
    
    /**
     * Holds the base unit for money quantities (symbol "$").
     */
    public final static CurrencyUnit<IMoney> UNIT = new CurrencyUnit<IMoney>("$");

	/**
	 * Creates a money amount always on the heap independently from the current
	 * {@link javolution.context.AllocatorContext allocator context}. To allow
	 * for custom object allocation policies, static factory methods
	 * <code>valueOf(...)</code> are recommended.
	 * 
	 * @param value
	 *            the value stated in the specified currency.
	 * @param currency
	 *            the currency in which the value is stated.
	 */
	public MoneyAmount(Number value, Currency unit) {
		super(value, unit);
	}

	/**
	 * Returns the money amount corresponding to the specified BigDecimal value
	 * and currency.
	 * 
	 * @param value
	 *            the value stated in the specified currency.
	 * @param currency
	 *            the currency in which the value is stated.
	 * @return the corresponding amount.
	 */
	static MoneyAmount valueOfCurrency(Number value, Currency currency) {
		MoneyAmount amount = new MoneyAmount(value, currency);
		return amount;
	}

	/**
	 * Returns the money amount corresponding to the specified BigDecimal value
	 * and currency.
	 * 
	 * @param value
	 *            the value stated in the specified currency.
	 * @param currency
	 *            the currency in which the value is stated.
	 * @return the corresponding amount.
	 */
	public static MoneyAmount valueOf(Number value, Unit<?> currency) {
		MoneyAmount amount = new MoneyAmount(value, (Currency) currency);
		return amount;
	}

	/**
	 * Returns the money amount corresponding to the specified value and cents.
	 * 
	 * @param value
	 *            the integer value in the specified currency.
	 * @param cents
	 *            the cents value in the specified currency.
	 * @param currency
	 *            the currency in which the value and cents are stated.
	 * @return the corresponding amount.
	 */
	public static MoneyAmount valueOf(long value, int cents, Currency currency) {
		MoneyAmount amount = new MoneyAmount(BigDecimal.valueOf(value * 100
				+ cents, -2), currency);
		return amount;
	}

	/**
	 * Returns the money amount corresponding to the specified generic amount.
	 * 
	 * @param amount
	 *            the raw amount.
	 * @return the corresponding money amount stated in an existing
	 *         {@link Currency}.
	 * @throws ClassCastException
	 *             if the SI unit of the specified amount is not a
	 *             {@link Currency}.
	 */
	public static MoneyAmount valueOf(QuantityAmount<IMoney> amount) {
		// MoneyAmount amountSI = amount.toSI();
		return MoneyAmount.valueOf(BigDecimal.valueOf(amount.getNumber()
				.doubleValue()), amount.getQuantityUnit().getSystemUnit());
	}

	/**
	 * Overrides the default {@link #toString()} to
	 * show only the currency {@link Currency#getFractionDigits() fraction
	 * digits} of the associated currency (e.g. rounding to closest cents).
	 * 
	 * @return the string representation of this money amount.
	 */
	public String toStringLocale() {
		BigDecimal value = (BigDecimal) this.getNumber();
//		int digits = ((Currency) this.getUnit()).getDefaultFractionDigits();
		// int exponent = value.getExponent();
		// LargeInteger significand = value.getSignificand();
		// int scale = exponent + digits;
		// significand = significand.E(scale);
		// exponent -= scale;
		// value = BigDecimal.valueOf(significand, exponent);
		NumberFormat numberFormat = NumberFormat.getInstance();
		StringBuffer tmp = new StringBuffer();
		numberFormat.format(value, tmp, new FieldPosition(0));
		tmp.append(' ');
		tmp.append(this.getUnit().toString());
		return tmp.toString();
	}

	// public MoneyAmount opposite() {
	// return MoneyAmount.valueOf(_value.opposite(), getCurrency());
	// }

	protected MoneyAmount plus(MoneyAmount that) {
		// Measure<BigDecimal, ?> amount = that.to((Unit) getCurrency());
		return MoneyAmount.valueOfCurrency(this.getNumber().doubleValue()
				+ that.getNumber().doubleValue(), getCurrency());
	}

	protected MoneyAmount minus(MoneyAmount that) {
		// MoneyAmount amount = that.to((Unit) getCurrency());
		return MoneyAmount.valueOfCurrency(this.getNumber().doubleValue()
				- that.getNumber().doubleValue(), getCurrency());
	}

	public MoneyAmount multiply(long n) {
		return MoneyAmount.valueOfCurrency(getNumber().longValue() * n,
				getCurrency());
	}

	protected MoneyAmount multiply(MoneyAmount that) {
		Unit<?> unit = getQuantityUnit().multiply(that.getQuantityUnit());
		return MoneyAmount.valueOf(((BigDecimal) getNumber())
				.multiply((BigDecimal) that.getNumber()), unit);
	}

	public MoneyAmount pow(int exp) {
		return MoneyAmount.valueOf(
				((BigDecimal) getNumber()).pow(BigDecimal.valueOf(exp)),
				getQuantityUnit().pow(exp));
	}

//	 protected MoneyAmount inverse() {
//	 return MoneyAmount.valueOf(((BigDecimal) getNumber()).inverse(),
//	 getQuantityUnit().inverse());
//	 }

	public MoneyAmount divide(long n) {
		return MoneyAmount.valueOfCurrency(getNumber().longValue() / n,
				getCurrency());
	}

	protected MoneyAmount divide(MoneyAmount that) {
		Unit<?> unit = getQuantityUnit().divide(that.getQuantityUnit());
		return MoneyAmount.valueOf(((BigDecimal) getNumber())
				.divide((BigDecimal) that.getNumber()), unit);
	}

	public MoneyAmount copy() {
		return MoneyAmount.valueOfCurrency(getNumber(), getCurrency());
	}

	/**
	 * Get the unit (convenience to avoid cast).
	 * 
	 * @draft UOMo 0.5
	 * @provisional This API might change or be removed in a future release.
	 */
	@SuppressWarnings("unchecked")
	public Unit<IMoney> getQuantityUnit() {
		return (Unit<IMoney>) getCurrency();
	}

	public int compareTo(IMoney o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double doubleValue(Unit<IMoney> unit) {
	   	Unit<IMoney> myUnit = getQuantityUnit();
    	try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			return converter.convert(getNumber().doubleValue());
		} catch (UnconvertibleException e) {
			throw e;
		} catch (IncommensurableException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public long longValue(Unit<IMoney> unit) throws ArithmeticException {
	   	Unit<IMoney> myUnit = getQuantityUnit();
    	try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			return (converter.convert(BigDecimal.valueOf(super.getNumber().longValue())).longValue());
		} catch (UnconvertibleException e) {
			throw e;
		} catch (IncommensurableException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public IMeasure<IMoney> add(IMeasure<IMoney> that) {
		return plus((MoneyAmount)that);
	}

	public IMeasure<IMoney> substract(IMeasure<IMoney> that) {
		return minus((MoneyAmount)that);
	}

	public IMeasure<IMoney> divide(IMeasure<?> that) {
		return divide((MoneyAmount)that);
	}

	public IMeasure<IMoney> multiply(IMeasure<?> that) {
		return multiply((MoneyAmount)that);
	}
	
	public IMeasure<IMoney> to(Unit<IMoney> unit) {
		return to(unit, MathContext.DECIMAL32);
	}
	
    protected IMeasure<IMoney> to(Unit<IMoney> unit, MathContext ctx) {
        if (this.getUnit().equals(unit))
            return this;
        UnitConverter cvtr = this.getQuantityUnit().getConverterTo(unit);
        if (cvtr == AbstractConverter.IDENTITY)
            return (IMeasure<IMoney>) valueOf(this.getNumber(), unit);
        return (IMeasure<IMoney>) valueOf(convert(this.getNumber(), cvtr, ctx), unit);
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
            Number newValue = cvtr.convert(decimalValue.toBigDecimal(), ctx);
            return newValue;
//            if (((FieldNumber)value) instanceof Decimal)
//                return (N)((FieldNumber)Decimal.valueOf(newValue));
//            if (((FieldNumber)value) instanceof Float64)
//                return (N)((FieldNumber)Float64.valueOf(newValue.doubleValue()));
//            throw new ArithmeticException(
//                    "Generic amount conversion not implemented for amount of type " + value.getClass());
        }
    }

    /**
	 * Generate a 'preference neutral' string from Money value.
	 * 
	 * @return java.lang.String
	 */
	public String serialize() {
		return null;
	}
}