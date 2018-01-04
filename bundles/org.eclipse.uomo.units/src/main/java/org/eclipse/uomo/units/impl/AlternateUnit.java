/*
 * Copyright (c) 2005, 2017, Werner Keil and others.
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

import java.util.Map;

import javax.measure.Dimension;
import javax.measure.Unit;
import javax.measure.UnitConverter;


/**
 * <p> This class represents metric units used in expressions to distinguish
 *     between quantities of a different nature but of the same dimensions.
 *     Alternate units are always unscaled metric units.</p>
 *
 * <p> Instances of this class are created through the
 *     {@link AbstractUnit#alternate(String)} method.</p>
 *
 * @param <Q> The type of the quantity measured by this unit.
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 1.2, $Date: 2017-12-21 $
 */
public final class AlternateUnit<Q extends Quantity<Q>> extends AbstractUnit<Q> {

    /** The serialVersionUID */
    private static final long serialVersionUID = -1234567854321234567L;

	/**
     * Holds the parent unit (a system unit).
     */
    private final Unit<?> parent;

    /**
     * Holds the parent unit (a system unit).
     */
    private final String symbol;

    /**
     * Creates an alternate unit for the specified unit identified by the
     * specified name and symbol.
     *
     * @param symbol the symbol for this alternate unit.
     * @param parent the system unit from which this alternate unit is
     *        derived.
     * @throws UnsupportedOperationException if the parent is not
     *         an unscaled metric unit.
     * @throws IllegalArgumentException if the specified symbol is
     *         associated to a different unit.
     */
    @SuppressWarnings("rawtypes")
	public AlternateUnit(Unit<?> parent, String symbol) {
	this((AbstractUnit)parent, symbol);
    }
    
    
    /**
     * Creates an alternate unit for the specified unit identified by the
     * specified name and symbol.
     *
     * @param symbol the symbol for this alternate unit.
     * @param parent the system unit from which this alternate unit is
     *        derived.
     * @throws UnsupportedOperationException if the parent is not
     *         an unscaled metric unit.
     * @throws IllegalArgumentException if the specified symbol is
     *         associated to a different unit.
     */
    public AlternateUnit(AbstractUnit<?> parent, String symbol) {
        if (parent == null || !parent.isUnscaledMetric())
            throw new UnsupportedOperationException(parent + " is not an unscaled metric unit");
        this.parent = parent;
        this.symbol = symbol;
        // Checks if the symbol is associated to a different unit.
        synchronized (AbstractUnit.SYMBOL_TO_UNIT) {
            AbstractUnit<?> unit = (AbstractUnit<?>) AbstractUnit.SYMBOL_TO_UNIT.get(symbol);
            if (unit == null) {
                AbstractUnit.SYMBOL_TO_UNIT.put(symbol, this);
                return;
            }
            if (unit instanceof AlternateUnit<?>) {
                AlternateUnit<?> existingUnit = (AlternateUnit<?>) unit;
                if (symbol.equals(existingUnit.getSymbol()) && this.parent.equals(existingUnit.parent))
                    return; // OK, same unit.
            }
            throw new IllegalArgumentException("Symbol " + symbol + " is associated to a different unit");
        }
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
    
	@Override
	public Unit<Q> getSystemUnit() {
		return toMetric();
	}
	
    @Override
    protected final AbstractUnit<Q> toMetric() {
        return this;
    }

    @Override
    public final UnitConverter getConverterToMetric() {
        return AbstractConverter.IDENTITY;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (!(that instanceof AlternateUnit<?>))
            return false;
        AlternateUnit<?> thatUnit = (AlternateUnit<?>) that;
        return this.symbol.equals(thatUnit.symbol); // Symbols are unique.
    }

    @Override
    public Dimension getDimension() {
        return parent.getDimension();
    }

    @SuppressWarnings("rawtypes")
	@Override
    public UnitConverter getDimensionalTransform() {
        return ((AbstractUnit)parent).getDimensionalTransform();
    }

    @Override
    public Map<? extends Unit<?>, Integer> getBaseUnits() {
      return parent.getBaseUnits();
    }
    
    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}