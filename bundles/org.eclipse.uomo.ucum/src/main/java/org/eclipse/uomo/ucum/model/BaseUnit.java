/*******************************************************************************
 * Crown Copyright (c) 2006, 2008, Copyright (c) 2006, 2008 Kestral Computing P/L.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *    Werner Keil - Refactoring and improvements
 *******************************************************************************/

package org.eclipse.uomo.ucum.model;

import java.util.Map;

import javax.measure.Dimension;
import javax.measure.IncommensurableException;
import javax.measure.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import tec.uom.se.quantity.QuantityDimension;


/**
 * @author Werner Keil
 * @version 2.0
 */
public class BaseUnit extends UcumUnit {


	/**
	 * abbrevation for property
	 */
	private char dim;
	
	/**
	 * @param code
	 * @param codeUC
	 */
	public BaseUnit(String code, String codeUC) {
		super(ConceptKind.BASEUNIT, code, codeUC);
	}

	/**
	 * @return the dim
	 */
	public char getDim() {
		return dim;
	}

	/**
	 * @param dim the dim to set
	 */
	public void setDim(char dim) {
		this.dim = dim;
	}

	@Override
	public Unit shift(double arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit asType(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit divide(Unit arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitConverter getConverterTo(Unit arg0)
			throws UnconvertibleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitConverter getConverterToAny(Unit arg0)
			throws IncommensurableException, UnconvertibleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getDimension() {
		return QuantityDimension.parse(dim);
	}

	@Override
	public Unit getSystemUnit() {
		return this;
	}

	@Override
	public Unit inverse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit multiply(double arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit multiply(Unit arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit transform(UnitConverter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getBaseUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


}
