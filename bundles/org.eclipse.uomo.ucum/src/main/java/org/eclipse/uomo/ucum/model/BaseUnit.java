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

import org.eclipse.uomo.units.impl.DimensionImpl;
import org.unitsofmeasurement.unit.Dimension;
import org.unitsofmeasurement.unit.IncommensurableException;
import org.unitsofmeasurement.unit.UnconvertibleException;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

/**
 * @author Werner Keil
 * @version 1.1
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
	public Unit add(double arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit alternate(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit asType(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit divide(double arg0) {
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
		return DimensionImpl.valueOf(dim);
	}

	@Override
	public Map getProductUnits() {
		// TODO Auto-generated method stub
		return null;
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
	public boolean isCompatible(Unit arg0) {
		return arg0.getDimension() != null && arg0.getDimension().equals(getDimension());
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
	public Unit pow(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit root(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Unit transform(UnitConverter arg0) {
		// TODO Auto-generated method stub
		return null;
	}


}
