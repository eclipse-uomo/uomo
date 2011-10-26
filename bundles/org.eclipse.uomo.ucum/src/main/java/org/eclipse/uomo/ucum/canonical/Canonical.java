/*******************************************************************************
 * Crown Copyright (c) 2006, 2008, Copyright (c) 2006, 2008 Kestral Computing P/L.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *******************************************************************************/

package org.eclipse.uomo.ucum.canonical;

import java.math.BigDecimal;
import java.math.MathContext;

import org.eclipse.uomo.ucum.expression.Term;

// TODO replace this with extended QuantityAmount
public class Canonical {

	private BigDecimal value;
	private Term unit;
	
	/**
	 * @param value
	 * @param unit
	 */
	public Canonical(BigDecimal value, Term unit) {
		super();
		this.value = value;
		this.unit = unit;
	}

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @return the unit
	 */
	public Term getUnit() {
		return unit;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void multiplyValue(Number number) {
		value = value.multiply((BigDecimal) number);		
	}

	public void multiplyValue(int multiplicand) {
		value = value.multiply(new BigDecimal(multiplicand));		
	}

	public boolean hasUnit() {
		return unit != null;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(Term unit) {
		this.unit = unit;
	}

	public void divideValue(BigDecimal divisor) {
		value = value.divide(divisor, new MathContext(20));		
	}
	
	
	
}
