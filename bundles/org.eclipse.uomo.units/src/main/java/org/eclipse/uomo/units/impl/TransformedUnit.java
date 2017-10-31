/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl;

import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.AbstractUnit;
import javax.measure.Quantity;
import javax.measure.unit.IncommensurableException;
import javax.measure.unit.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.UnitConverter;

/**
 * <p>
 * This class represents the units derived from other units using
 * {@linkplain AbstractConverter converters}.
 * </p>
 * 
 * <p>
 * Examples of transformed units:[code] CELSIUS = KELVIN.add(273.15); FOOT =
 * METRE.times(3048).divide(10000); MILLISECOND = MILLI(SECOND); [/code]
 * </p>
 * 
 * <p>
 * Transformed units have no label. But like any other units, they may have
 * labels attached to them (see
 * {@link org.eclipse.uomo.units.AbstractFormat.SymbolMap SymbolMap}
 * </p>
 * 
 * <p>
 * Instances of this class are created through the
 * {@link AbstractUnit#transform} method.
 * </p>
 * 
 * @param <Q>
 *            The type of the quantity measured by this unit.
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.2 ($Revision: 212 $), $Date: 2013-03-10 $
 */
public final class TransformedUnit<Q extends Quantity<Q>> extends
		AbstractUnit<Q> {

	@Override
	public UnitConverter getConverterToAny(Unit<?> that)
			throws IncommensurableException, UnconvertibleException {
		if (toParentUnit != null) {
			return toParentUnit.inverse();
		}
		return super.getConverterToAny(that);
	}

	/**
	 * For cross-version compatibility.
	 */
	private static final long serialVersionUID = -442449068482939939L;

	/**
	 * Holds the parent unit (not a transformed unit).
	 */
	private final Unit<Q> parentUnit;

	/**
	 * Holds the converter to the parent unit.
	 */
	private final AbstractConverter toParentUnit;

	/**
	 * Creates a transformed unit from the specified parent unit.
	 * 
	 * @param parentUnit
	 *            the untransformed unit from which this unit is derived.
	 * @param toParentUnit
	 *            the converter to the parent units.
	 * @throws IllegalArgumentException
	 *             if <code>toParentUnit ==
	 *         {@link AbstractConverter#IDENTITY UnitConverter.IDENTITY}</code>
	 */
	public TransformedUnit(Unit<Q> parentUnit, AbstractConverter toParentUnit) {
		if (toParentUnit == AbstractConverter.IDENTITY)
			throw new IllegalArgumentException("Identity not allowed");
		this.parentUnit = parentUnit;
		this.toParentUnit = toParentUnit;
	}

	/**
	 * Returns the parent unit for this unit. The parent unit is the
	 * untransformed unit from which this unit is derived.
	 * 
	 * @return the untransformed unit from which this unit is derived.
	 */
	public Unit<Q> getParentUnit() {
		return parentUnit;
	}

	/**
	 * Returns the converter to the parent unit.
	 * 
	 * @return the converter to the parent unit.
	 */
	public UnitConverter toParentUnit() {
		return toParentUnit;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that)
			return true;
		if (!(that instanceof TransformedUnit<?>))
			return false;
		TransformedUnit<?> thatUnit = (TransformedUnit<?>) that;
		return this.parentUnit.equals(thatUnit.parentUnit)
				&& (this.toParentUnit != null && this.toParentUnit
						.equals(thatUnit.toParentUnit));
	}

	@Override
	public int hashCode() {
		return parentUnit.hashCode()
				+ (toParentUnit != null ? toParentUnit.hashCode() : 0);
	}

	@Override
	protected Unit<Q> toMetric() {
		return parentUnit.getSystemUnit();
	}

	@Override
	public UnitConverter getConverterToMetric() {
		return ((AbstractUnit<Q>) parentUnit).getConverterToMetric()
				.concatenate(toParentUnit);
	}

	@Override
	public Unit<Q> getSystemUnit() {
		return toMetric();
	}
}
