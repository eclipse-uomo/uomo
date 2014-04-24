/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2013-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
 *  contributors by the @author tag.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.eclipse.uomo.units.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.AbstractQuantity;
import org.eclipse.uomo.units.IMeasure;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.IncommensurableException;
import org.unitsofmeasurement.unit.UnconvertibleException;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

/**
 * An amount of quantity, consisting of a Number and a Unit. BaseMeasurement
 * objects are immutable.
 * 
 * @see java.lang.Number
 * @see MeasureUnit
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @param <Q>
 *            The type of the quantity.
 * @version 1.6, $Date: 2014-04-08 $
 */
public class BaseQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q>
		implements Quantity<Q>, Comparable<BaseQuantity<Q>> {
//FIXME Bug 338334 overwrite equals()
    

	/**
	 * 
	 */
//	private static final long serialVersionUID = 7312161895652321241L;

	private final Number value;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractMeasurement#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (this.getClass() == obj.getClass()) {
			return super.equals(obj);
		} else {
			if (obj instanceof Quantity) {
				@SuppressWarnings("rawtypes")
				Quantity m = (Quantity) obj;
				if (m.value().getClass() == this.getValue().getClass()
						&& m.unit().getClass() == this.getUnit().getClass()) {
					return super.equals(obj);
				} else {
					// if (this.getQuantityUnit() instanceof AbstractUnit<?>) {
					// if
					// }
					return super.equals(obj);
				}
			}
			return false;
		}
	}

	/**
	 * Indicates if this measure is exact.
	 */
	private final boolean isExact;

	/**
	 * Indicates if this measure is big.
	 */
	private final boolean isBig;
	
	/**
	 * Holds the exact value (when exact) stated in this measure unit.
	 */
	// private long exactValue;

	/**
	 * Holds the minimum value stated in this measure unit. For inexact
	 * measures: minimum < maximum
	 */
	// private double minimum;

	/**
	 * Holds the maximum value stated in this measure unit. For inexact
	 * measures: maximum > minimum
	 */
	// private double maximum;

	protected BaseQuantity(Number number, Unit<Q> unit) {
		super(unit);
		value = number;
		isExact = false;
		isBig = false;;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Measurement#doubleValue(javax.measure.Unit)
	 */
	public double doubleValue(Unit<Q> unit) {
		Unit<Q> myUnit = getUnit();
		try {
			UnitConverter converter = unit.getConverterTo(myUnit);
			return converter.convert(getValue().doubleValue());
		} catch (UnconvertibleException e) {
			throw e;
		} // catch (IncommensurableException e) {
		// throw new IllegalArgumentException(e.getMessage());
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.uomo.units.AbstractMeasurement#longValue(javax.measure
	 * .Unit)
	 */
	public long longValue(Unit<Q> unit) {
		Unit<Q> myUnit = getUnit();
		try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			if ((getValue() instanceof BigDecimal || getValue() instanceof BigInteger) 
					&& converter instanceof AbstractConverter) {
				return (((AbstractConverter)converter).convert(
						BigDecimal.valueOf(getValue().longValue()),
						MathContext.DECIMAL128)).longValue();
			} else {
		        double result = doubleValue(unit);
		        if ((result < Long.MIN_VALUE) || (result > Long.MAX_VALUE)) {
		            throw new ArithmeticException("Overflow (" + result + ")");
		        }
		        return (long) result;
			}
		} catch (UnconvertibleException e) {
			throw e;
		} catch (IncommensurableException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.measure.Quantity#getValue()
	 */
	public Number getValue() {
		return value;
	}

	/**
	 * Indicates if this measured amount is exact. An exact amount is guarantee
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
	
	/**
	 * Indicates if this measured amount is a big number, i.E. BigDecimal or BigInteger.
	 * In all other cases this would be false.
	 * 
	 * @return <code>true</code> if this measure is big; <code>false</code>
	 *         otherwise.
	 */
	public boolean isBig() {
		return isBig;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseQuantity<Q> add(AbstractQuantity<Q> that) {
		final AbstractQuantity<Q> thatToUnit = that.to(getUnit());
		return new BaseQuantity(this.getValue().doubleValue()
				+ thatToUnit.getValue().doubleValue(), 
                                  getUnit());
	}
	
	public String toString() {
		return  String.valueOf(getValue()) + " " 
                        + String.valueOf(getUnit());
	}

	@Override
	public IMeasure<Q> add(IMeasure<Q> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMeasure<Q> substract(IMeasure<Q> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMeasure<?> multiply(IMeasure<?> that) {
		final Unit<?> unit = getUnit().multiply(that.unit());
		return of((getValue().doubleValue() * that.value()
				.doubleValue()), unit);	
	}
        
        @Override
	public BaseQuantity<?> multiply(Number that) {
		return (BaseQuantity<Q>) of((getValue().doubleValue() * that
				.doubleValue()), getUnit());	
	}
        
	@Override
	public IMeasure<?> divide(IMeasure<?> that) {
		final Unit<?> unit = getUnit().divide(that.unit());
		return of((getValue().doubleValue() / that.value()
				.doubleValue()), unit);	
	}

	public IMeasure<?> divide(Number that) {
		// TODO may use isBig() here, too
		if (value instanceof BigDecimal && that instanceof BigDecimal) {
			return of(((BigDecimal)value).divide((BigDecimal)that), 
                                getUnit());
		}
		return of(getValue().doubleValue() / that.doubleValue(), 
                        getUnit());	
	}
	
//	@Override
//	public IMeasure<Q> inverse() {
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		final IMeasure<Q> m = new BaseQuantity(getValue(),
//				getUnit().inverse()); // TODO keep value same?
//		return m;
//	}

	@Override
	public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx)
			throws ArithmeticException {
		if (value instanceof BigDecimal) {
                    return (BigDecimal)value;
                }
                if (value instanceof BigInteger) {
                    return new BigDecimal((BigInteger)value);
                }
		return BigDecimal.valueOf(value.doubleValue());
	}

	@Override
	public int compareTo(BaseQuantity<Q> o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number value() {
		return getValue();
	}

	@Override
	public IMeasure<? extends IMeasure<Q>> inverse() {
		final IMeasure<? extends IMeasure<Q>> m = new BaseQuantity(getValue(),
		getUnit().inverse()); // TODO keep value same?
		return m;
	}
}
