/*******************************************************************************
 * Crown Copyright (c) 2006, 2014, Copyright (c) 2006, 2017 Kestral Computing P/L and others.
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

//import tec.uom.lib.common.function.Coded;
import javax.measure.Quantity;

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
	public Quantity<?> divide(Quantity<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Quantity<?> multiply(Quantity<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Quantity<?> inverse() {
		// TODO Auto-generated method stub
		return null;
	}
}
