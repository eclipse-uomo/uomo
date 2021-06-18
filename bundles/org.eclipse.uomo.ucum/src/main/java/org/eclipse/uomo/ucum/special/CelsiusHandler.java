/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2017 Kestral Computing P/L and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *    Werner Keil - adjusted to Unit-API
 *******************************************************************************/

package org.eclipse.uomo.ucum.special;

import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Temperature;

import static tech.units.indriya.unit.Units.KELVIN;

public class CelsiusHandler extends SpecialUnitHandler<Temperature> {

	@Override
	public String getCode() {
		return "Cel";
	}

	@Override
	public String getUnits() {
		return "K";
	}

	/* (non-Javadoc)
	 * @see javax.measure.Quantity#unit()
	 */
	@Override
	public Unit<Temperature> getUnit() {
		return KELVIN;
	}
	
	/* (non-Javadoc)
	 * @see javax.measure.Quantity#value()
	 */
	@Override
	public BigDecimal getValue() {
		return new BigDecimal(1);
	}
	
	@Override
	public Quantity<Temperature> add(Quantity<Temperature> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Quantity<Temperature> divide(Number arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Quantity<Temperature> multiply(Number arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Temperature> subtract(Quantity<Temperature> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Temperature> to(Unit<Temperature> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isEquivalentTo(Quantity<Temperature> that) {
		//return this.getConverterTo(that).isIdentity();
		return false;
	}
}
