/*******************************************************************************
 * Crown Copyright (c) 2006, 2014, Copyright (c) 2006, 2021 Kestral Computing P/L and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *    Werner Keil - modularization, integration with other UOMo parts
 *******************************************************************************/

package org.eclipse.uomo.ucum.special;

import javax.measure.Quantity;
import javax.measure.Quantity.Scale;
import javax.measure.Unit;
import javax.measure.quantity.Temperature;

import org.eclipse.uomo.core.ICode;

public abstract class SpecialUnitHandler<Q extends Quantity<Q>> implements Quantity<Q>, ICode<String> {

	/**
	 * Used to connect this handler with the case sensitive unit
	 * @return
	 */
	public abstract String getCode();

	/** 
	 * the alternate units to convert to
	 * TODO use Unit-API here where possible
	 * @return
	 */
	public abstract String getUnits();	

	@Override
	public <T extends Quantity<T>> Quantity<T> asType(Class<T> arg0) throws ClassCastException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<?> divide(Quantity<?> arg0) {
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
	public Quantity<?> multiply(Quantity<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isEquivalentTo(Quantity<Q> q) {
		//return this.getConverterTo(q).isIdentity();
		return false;
	}
	
	@Override
	public Scale getScale() {
		return Scale.ABSOLUTE;
	}
}
