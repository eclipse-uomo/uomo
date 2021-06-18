/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2021 Kestral Computing P/L and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *    Werner Keil - Cleaning, unification with other UOMo parts and Unit-API
 *******************************************************************************/

package org.eclipse.uomo.ucum.model;

import java.math.BigDecimal;

import org.eclipse.uomo.core.ICode;
import org.eclipse.uomo.core.IDescription;
import javax.measure.Quantity;
import javax.measure.Quantity.Scale;
import javax.measure.Unit;

public class Value<Q extends Quantity<Q>> implements Quantity<Q>, IDescription, ICode<String> {
	// TODO should use Quantity
	// TODO make this a "real" unit, should be at least UcumUnit
	private final String unit;
	private final String unitUC;
	private final BigDecimal value;
	private final Scale scale;
	private Unit<Q> realUnit;
	@SuppressWarnings("unused")
	private String text;
	
	/**
	 * @param unit
	 * @param unitUC
	 * @param value
	 * @param scale
	 */
	public Value(String unit, String unitUC, BigDecimal value, Scale scale) {
		super();
		this.unit = unit;
		this.unitUC = unitUC;
		this.value = value;
		this.scale = scale;
	}
	
	/**
	 * @param unit
	 * @param unitUC
	 * @param value
	 */
	public Value(String unit, String unitUC, BigDecimal value) {
		this(unit, unitUC, value, Scale.ABSOLUTE);
	}

	/**
	 * @return the unit code
	 */
	public String getCode() {
		return unit;
	}

	/**
	 * @return the Uppercase variant of the label
	 * @deprecated currently not used
	 */
	public String getCodeUC() {
		return unitUC;
	}
	
	/**
	 * @return the scale
	 */
	public Scale getScale() {
		return scale;
	}

	/**
	 * @param string the string value to set
	 */
	public void setString(String text) {
		this.text = text;
	}

	public String getDescription() {
		if (value == null)
			return unit;
		return value.toEngineeringString()+unit;
	}

	public Number getValue() {
		return value;
	}

	public Unit<Q> getUnit() {
		return realUnit;
	}	
	
	/**
	 * @deprecated use {@link #getValue()}
	 */
	public Number value() {
		return value;
	}

	/**
	 * @deprecated use {@link #getUnit()}
	 */
	public Unit<Q> unit() {
		return realUnit;
	}

	@Override
	public Quantity<Q> add(Quantity<Q> augend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Q> subtract(Quantity<Q> subtrahend) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<?> divide(Quantity<?> divisor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Q> divide(Number divisor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<?> multiply(Quantity<?> multiplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Q> multiply(Number multiplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<?> inverse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Q> negate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Quantity<Q> to(Unit<Q> unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Quantity<T>> Quantity<T> asType(Class<T> type) throws ClassCastException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isEquivalentTo(Quantity<Q> q) {
		//return this.getConverterTo(q).isIdentity();
		return false;
	}
}
