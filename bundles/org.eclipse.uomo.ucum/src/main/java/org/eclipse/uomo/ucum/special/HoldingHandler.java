/*******************************************************************************
 * Crown Copyright (c) 2006, 2008, Copyright (c) 2006, 2017 Kestral Computing P/L and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *    Werner Keil - Implementing JSR 363
 *******************************************************************************/

package org.eclipse.uomo.ucum.special;

import java.math.BigDecimal;

import org.eclipse.uomo.units.AbstractUnit;
import javax.measure.Quantity;
import javax.measure.Unit;

/**
 * If you want to actually use one of these units, then you'll
 * have to figure out how to implement them
 * 
 * @author Grahame Grieve
 * @author Werner Keil
 * @param <Q>
 *
 */
public class HoldingHandler<Q extends Quantity<Q>> extends SpecialUnitHandler<Q> {

	private final String code;
	private final String units;
	private BigDecimal value = BigDecimal.ONE;
	@SuppressWarnings("unchecked")
	private Unit<Q> unit = (Unit<Q>) AbstractUnit.ONE;
	
	/**
	 * @param code
	 * @param units
	 */
	public HoldingHandler(String code, String units) {
		super();
		this.code = code;
		this.units = units;
	}

	public HoldingHandler(String code, String units, BigDecimal value) {
		super();
		this.code = code;
		this.units = units;
		this.value = value;
	}
	
	public HoldingHandler(String code, Unit<Q> unit, BigDecimal value) {
		super();
		this.code = code;
		this.unit = unit;
		this.units = unit.getSymbol();
		this.value = value;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getUnits() {
		return units;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.ucum.special.SpecialUnitHandler#getValue()
	 */
	@Override
	public BigDecimal getValue() {		
		return value;
	}

	@Override
	public Unit<Q> getUnit() {
		return unit;
	}

	@Override
	public Quantity<Q> add(Quantity<Q> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Quantity<T>> Quantity<T> asType(Class<T> arg0) throws ClassCastException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Quantity<Q> divide(Number arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<?> inverse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<?> multiply(Quantity<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Q> multiply(Number arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Q> subtract(Quantity<Q> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Q> to(Unit<Q> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
