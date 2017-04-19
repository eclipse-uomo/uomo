/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2017 Kestral Computing P/L and others.
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

import org.eclipse.uomo.core.IDescription;
import org.eclipse.uomo.core.ISymbol;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.Unit;

public class Value<Q extends Quantity<Q>> implements Quantity<Q>, IDescription, ISymbol {
	// TODO should use Quantity
	// TODO make this a "real" unit, should be at least UcumUnit
	private final String unit;
	private final String unitUC;
	private final BigDecimal value;
	private Unit<Q> realUnit;
	private String text;

	/**
	 * @param unit
	 * @param unitUC
	 * @param value
	 */
	public Value(String unit, String unitUC, BigDecimal value) {
		super();
		this.unit = unit;
		this.unitUC = unitUC;
		this.value = value;
	}

	/**
	 * @return the unit label
	 */
	public String getSymbol() {
		return unit;
	}

	/**
	 * @return the Uppercase variant of the symbol
	 * @deprecated currently not used
	 */
	public String getSymbolUC() {
		return unitUC;
	}

	/**
	 * @return the text
	 */
	String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
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
}
