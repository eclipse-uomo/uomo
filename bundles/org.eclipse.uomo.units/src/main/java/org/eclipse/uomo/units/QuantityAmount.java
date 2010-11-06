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
package org.eclipse.uomo.units;

import java.math.BigDecimal;
import java.math.MathContext;

import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.IncommensurableException;
import org.unitsofmeasurement.unit.UnconvertibleException;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;

/**
 * An amount of quantity, consisting of a Number and a Unit.
 * QuantityAmount objects are immutable.
 *
 * @see java.lang.Number
 * @see MeasureUnit
 * @author <a href="mailto:jcp@catmedia.us">Werner Keil</a>
 * @param <Q> The type of the quantity.
 * @version 1.1 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 * TODO rename to Amount or MeasureAmount?
 */
public abstract class QuantityAmount<Q extends Quantity<Q>> extends Measure implements Measurable<Q> {

	protected QuantityAmount(Number number, MeasureUnit unit) {
		super(number, unit);
	}

    /**
     * Returns the value of this quantity as <code>double</code> stated
     * in the specified unit. This method is recommended over <code>
     * q.getUnit().getConverterTo(unit).convert(q.getNumber()).doubleValue()</code>
     *
     * @param unit the unit in which the returned value is stated.
     * @return the value of this quantity when stated in the specified unit.
     */
    public double doubleValue(Unit<Q> unit) {
    	Unit<Q> myUnit = getQuantityUnit();
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
     * Returns the value of this quantity as <code>long</code> stated
     * in the specified unit. This method is recommended over <code>
     * q.getUnit().getConverterTo(unit).convert(q.getNumber()).longValue()</code>
     *
     * @param unit the unit in which the returned value is stated.
     * @return the value of this quantity when stated in the specified unit.
     */
    public long longValue(Unit<Q> unit) {
    	Unit<Q> myUnit = getQuantityUnit();
    	try {
			UnitConverter converter = unit.getConverterToAny(myUnit);
			return (converter.convert(BigDecimal.valueOf(getNumber().longValue()), MathContext.DECIMAL128)).longValue();
		} catch (UnconvertibleException e) {
			throw e;
		} catch (IncommensurableException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
    }
    
    /**
     * Get the unit (convenience to avoid cast).
     * @draft UOMo 0.5
     * @provisional This API might change or be removed in a future release.
     */
    @SuppressWarnings("unchecked")
	public Unit<Q> getQuantityUnit() {
    	return (Unit<Q>) getUnit();
    }
}
