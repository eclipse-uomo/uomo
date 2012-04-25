/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2008 Kestral Computing P/L.
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
import org.eclipse.uomo.core.IText;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.Unit;

public class Value<Q extends Quantity<Q>> implements Quantity<Q>, IDescription, IText {
	// TODO should use Quantity
	// TODO make this a "real" unit, should be at least UcumUnit
	private String unit;
	private Unit<Q> realUnit;
	private String unitUC;
	
	private BigDecimal value;
	
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
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @return the unitUC
	 * @deprecated currently not used
	 */
	public String getUnitUC() {
		return unitUC;
	}

	/**
	 * @param unitUC the unitUC to set
	 * @deprecated currently not used
	 */
	public void setUnitUC(String unitUC) {
		this.unitUC = unitUC;
	}

	/**
	 * @param value the value to set
	 * @deprecated
	 */
	void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * @return the text
	 */
	public String getText() {
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

	public Number value() {
		return value;
	}

	public Unit<Q> unit() {
		return realUnit;
	}		
}
