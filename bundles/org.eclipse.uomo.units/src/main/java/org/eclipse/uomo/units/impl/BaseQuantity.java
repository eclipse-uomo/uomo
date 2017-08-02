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

import javax.measure.IncommensurableException;
import javax.measure.Quantity;
import javax.measure.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.UnitConverter;

/**
 * An amount of quantity, consisting of a Number and a Unit. BaseMeasurement
 * objects are immutable.
 * 
 * @see java.lang.Number
 * @see MeasureUnit
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @param <Q>
 *            The type of the quantity.
 * @version 1.7, $Date: 2017-07-30 $
 */
public class BaseQuantity<Q extends Quantity<Q>> extends AbstractQuantity<Q> implements Quantity<Q> {
	// FIXME Bug 338334 overwrite equals()

	/**
	 * 
	 */
	// private static final long serialVersionUID = 7312161895652321241L;

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
				if (m.getValue().getClass() == this.getValue().getClass()
						&& m.getUnit().getClass() == this.getUnit().getClass()) {
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
		isBig = false;
		;
	}

	/**
	 * Returns the scalar quantity for the specified <code>long</code> stated in
	 * the specified unit.
	 *
	 * @param longValue
	 *            the quantity value.
	 * @param unit
	 *            the measurement unit.
	 * @return the corresponding <code>int</code> quantity.
	 */
	public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(long longValue, Unit<Q> unit) {
		return new LongQuantity<Q>(longValue, unit);
	}

	/**
	 * Returns the scalar quantity for the specified <code>int</code> stated in
	 * the specified unit.
	 *
	 * @param intValue
	 *            the quantity value.
	 * @param unit
	 *            the measurement unit.
	 * @return the corresponding <code>int</code> quantity.
	 */
	public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(int intValue, Unit<Q> unit) {
		return new IntegerQuantity<Q>(intValue, unit);
	}

	/**
	 * Returns the scalar quantity for the specified <code>short</code> stated
	 * in the specified unit.
	 *
	 * @param value
	 *            the quantity value.
	 * @param unit
	 *            the measurement unit.
	 * @return the corresponding <code>short</code> quantity.
	 */
	public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(short value, Unit<Q> unit) {
		return new ShortQuantity<Q>(value, unit);
	}

	/**
	 * Returns the scalar quantity for the specified <code>float</code> stated
	 * in the specified unit.
	 *
	 * @param floatValue
	 *            the measurement value.
	 * @param unit
	 *            the measurement unit.
	 * @return the corresponding <code>float</code> quantity.
	 */
	public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(float floatValue, Unit<Q> unit) {
		return new FloatQuantity<Q>(floatValue, unit);
	}

	/**
	 * Returns the scalar quantity for the specified <code>double</code> stated
	 * in the specified unit.
	 *
	 * @param doubleValue
	 *            the measurement value.
	 * @param unit
	 *            the measurement unit.
	 * @return the corresponding <code>double</code> quantity.
	 */
	public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(double doubleValue, Unit<Q> unit) {
		return new DoubleQuantity<Q>(doubleValue, unit);
	}

	/**
	 * Returns the scalar quantity for the specified <code>double</code> stated
	 * in the specified unit.
	 *
	 * @param doubleValue
	 *            the measurement value.
	 * @param unit
	 *            the measurement unit.
	 * @return the corresponding <code>double</code> quantity.
	 */
	public static <Q extends Quantity<Q>> AbstractQuantity<Q> of(BigDecimal doubleValue, Unit<Q> unit) {
		return new BaseAmount<Q>(doubleValue, unit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Measurement#doubleValue(javax.measure.Unit)
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
	 * @see org.eclipse.uomo.units.AbstractMeasurement#longValue(javax.measure
	 * .Unit)
	 */
	public long longValue(Unit<Q> unit) {
		Unit<Q> myUnit = getUnit();
		try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			if ((getValue() instanceof BigDecimal || getValue() instanceof BigInteger)
					&& converter instanceof AbstractConverter) {
				return (((AbstractConverter) converter).convert(BigDecimal.valueOf(getValue().longValue()),
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
	 * Indicates if this measured amount is a big number, i.E. BigDecimal or
	 * BigInteger. In all other cases this would be false.
	 * 
	 * @return <code>true</code> if this measure is big; <code>false</code>
	 *         otherwise.
	 */
	public boolean isBig() {
		return isBig;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseQuantity<Q> add(AbstractQuantity<Q> that) {
		final Quantity<Q> thatToUnit = that.to(getUnit());
		return new BaseQuantity(this.getValue().doubleValue() + thatToUnit.getValue().doubleValue(), getUnit());
	}

	public String toString() {
		return String.valueOf(getValue()) + " " + String.valueOf(getUnit());
	}

	@Override
	public Quantity<Q> add(Quantity<Q> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Q> subtract(Quantity<Q> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<?> multiply(Quantity<?> that) {
		final Unit<?> unit = getUnit().multiply(that.getUnit());
		return of((getValue().doubleValue() * that.getValue().doubleValue()), unit);
	}

	@Override
	public Quantity<Q> multiply(Number that) {
		return (BaseQuantity<Q>) of((getValue().doubleValue() * that.doubleValue()), getUnit());
	}

	@Override
	public Quantity<?> divide(Quantity<?> that) {
		final Unit<?> unit = getUnit().divide(that.getUnit());
		return of((getValue().doubleValue() / that.getValue().doubleValue()), unit);
	}

	public Quantity<Q> divide(Number that) {
		// TODO may use isBig() here, too
		if (value instanceof BigDecimal && that instanceof BigDecimal) {
			return of(((BigDecimal) value).divide((BigDecimal) that), getUnit());
		}
		return of(getValue().doubleValue() / that.doubleValue(), getUnit());
	}

	// @Override
	// public Quantity<Q> inverse() {
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// final Quantity<Q> m = new BaseQuantity(getValue(),
	// getUnit().inverse()); // TODO keep value same?
	// return m;
	// }

	public BigDecimal decimalValue(Unit<Q> unit, MathContext ctx) throws ArithmeticException {
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		if (value instanceof BigInteger) {
			return new BigDecimal((BigInteger) value);
		}
		return BigDecimal.valueOf(value.doubleValue());
	}

	public int compareTo(BaseQuantity<Q> o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Quantity<? extends Quantity<Q>> inverse() {
		final Quantity<? extends Quantity<Q>> m = new BaseQuantity(getValue(), getUnit().inverse()); // TODO
																										// keep
																										// value
																										// same?
		return m;
	}
}
