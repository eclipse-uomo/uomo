/*******************************************************************************
 * Crown Copyright (c) 2006, 2013, Copyright (c) 2006, 2008 Kestral Computing P/L.
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

import org.unitsofmeasurement.unit.Unit;

/**
 * @author Werner Keil
 * @version 1.1
 */
@SuppressWarnings("rawtypes")
public abstract class UcumUnit extends Concept implements Unit {
	
	/**
	 * Holds the dimensionless unit <code>ONE</code>.
	 */
	public static final UcumUnit ONE = new DefinedUnit("", "");
	
	/**
	 * kind of thing this base unit represents
	 */
	private String property;

	protected UcumUnit(ConceptKind kind, String code, String codeUC) {
		super(kind, code, codeUC);
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.ucum.model.Concept#getDescription()
	 */
	@Override
	public String getDescription() {
		return super.getDescription()+" ("+property+")";
	}
	
	@Override
	public Unit<?> alternate(String a) {
		return this;
	}
	
	@Override
	public Unit<?> divide(double divisor) {
		if (divisor == 1)
			return this;
		return null;
	}
	
	@Override
	public boolean isCompatible(Unit u) {
		return u.getDimension() != null && u.getDimension().equals(getDimension());
	}
	
	/**
	 * Returns a unit equals to this unit raised to an exponent.
	 * 
	 * @param n
	 *            the exponent.
	 * @return the result of raising this unit to the exponent.
	 */
	public final Unit<?> pow(int n) {
		if (n > 0)
			return this.multiply(this.pow(n - 1));
		else if (n == 0)
			return ONE;
		else
			// n < 0
			return ONE.divide(this.pow(-n));
	}

	@Override
	public Unit<?> root(int n) {
		if (n == 0)
			throw new ArithmeticException("Root's order of zero"); //$NON-NLS-1$
		return null;
	}
}
