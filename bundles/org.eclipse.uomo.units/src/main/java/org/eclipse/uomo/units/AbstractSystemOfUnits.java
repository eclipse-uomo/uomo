/*
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units;

import static org.eclipse.uomo.units.impl.format.UnitStyle.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.SystemOfUnits;

import org.eclipse.uomo.units.impl.DefaultQuantityFactory;
import org.eclipse.uomo.units.impl.format.SimpleUnitFormat;
import org.eclipse.uomo.units.impl.format.UnitStyle;

import tec.uom.lib.common.function.Nameable;

/**
 * <p>
 * An abstract base class for unit systems.
 * </p>
 *
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.2, $Date: 2017-06-16 $
 */
public abstract class AbstractSystemOfUnits implements SystemOfUnits, Nameable {
	protected static final Logger logger = Logger.getLogger(AbstractSystemOfUnits.class.getName());

	/**
	 * Holds collection of units.
	 */
	protected static final Set<Unit<?>> UNITS = new HashSet<>();

	/**
	 * Holds the mapping quantity to unit.
	 */
	@SuppressWarnings("rawtypes")
	protected final Map<Class<? extends Quantity>, Unit> quantityToUnit = new HashMap<>();

	// ///////////////////
	// Collection View //
	// ///////////////////
	/**
	 * Returns a read only view over the units defined in this class.
	 * 
	 * @return the collection of units.
	 */
	public Set<Unit<?>> getUnits() {
		return Collections.unmodifiableSet(UNITS);
	}

	@Override
	public Set<Unit<?>> getUnits(Dimension dimension) {
		return Helper.getUnitsOfDimension(UNITS, dimension);
	}

	@Override
	public <T extends Quantity<T>> Unit<T> getUnit(Class<T> quantityType) {
		return DefaultQuantityFactory.getInstance(quantityType).getSystemUnit();
	}

	/**
	 * Adds a new named unit to the collection.
	 * 
	 * @param unit
	 *            the unit being added.
	 * @param name
	 *            the name of the unit.
	 * @return <code>unit</code>.
	 */
	@SuppressWarnings("unchecked")
	protected static <U extends Unit<?>> U addUnit(U unit, String name) {
		if (name != null && unit instanceof AbstractUnit) {
			AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
			aUnit.setName(name);
			UNITS.add(aUnit);
			return (U) aUnit;
		}
		UNITS.add(unit);
		return unit;
	}

	/**
	 * Adds a new unit to the collection.
	 * 
	 * @param unit
	 *            the unit being added.
	 * @return <code>unit</code>.
	 */
	protected static <U extends Unit<?>> U addUnit(U unit) {
		UNITS.add(unit);
		return unit;
	}

	protected static class Helper {
	    static Set<Unit<?>> getUnitsOfDimension(final Set<Unit<?>> units, Dimension dimension) {
	        if (dimension != null) {
	          Set<Unit<?>> dimSet = new HashSet<Unit<?>>();
	          for (Unit<?> u : units) {
	            if (dimension.equals(u.getDimension())) {
	              dimSet.add(u);
	            }
	          }
	          return dimSet;
	        }
	        return null;
	      }

		/**
		 * Adds a new named unit to the collection.
		 * 
		 * @param unit
		 *            the unit being added.
		 * @param name
		 *            the name of the unit.
		 * @return <code>unit</code>.
		 * @since 1.0
		 */
		public static <U extends Unit<?>> U addUnit(Set<Unit<?>> units, U unit, String name) {
			return addUnit(units, unit, name, NAME);
		}

		/**
		 * Adds a new named unit to the collection.
		 * 
		 * @param unit
		 *            the unit being added.
		 * @param name
		 *            the name of the unit.
		 * @param name
		 *            the symbol of the unit.
		 * @return <code>unit</code>.
		 * @since 1.0
		 */
		@SuppressWarnings("unchecked")
		public static <U extends Unit<?>> U addUnit(Set<Unit<?>> units, U unit, String name, String symbol) {
			if (name != null && symbol != null && unit instanceof AbstractUnit) {
				AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
				aUnit.setName(name);
				aUnit.setSymbol(symbol);
				units.add(aUnit);
				return (U) aUnit;
			}
			if (name != null && unit instanceof AbstractUnit) {
				AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
				aUnit.setName(name);
				units.add(aUnit);
				return (U) aUnit;
			}
			units.add(unit);
			return unit;
		}

		/**
		 * Adds a new named unit to the collection.
		 * 
		 * @param unit
		 *            the unit being added.
		 * @param name
		 *            the name of the unit.
		 * @param name
		 *            the symbol of the unit.
		 * @param style
		 *            style of the unit.
		 * @return <code>unit</code>.
		 * @since 1.0.1
		 */
		@SuppressWarnings("unchecked")
		public static <U extends Unit<?>> U addUnit(Set<Unit<?>> units, U unit, final String name, final String symbol,
				UnitStyle style) {
			switch (style) {
			case NAME:
			case SYMBOL:
			case SYMBOL_AND_LABEL:
				if (name != null && symbol != null && unit instanceof AbstractUnit) {
					AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
					aUnit.setName(name);
					if (SYMBOL.equals(style) || SYMBOL_AND_LABEL.equals(style)) {
						aUnit.setSymbol(symbol);
					}
					if (LABEL.equals(style) || SYMBOL_AND_LABEL.equals(style)) {
						SimpleUnitFormat.getInstance().label(unit, symbol);
					}
					units.add(aUnit);
					return (U) aUnit;
				}
				if (name != null && unit instanceof AbstractUnit) {
					AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
					aUnit.setName(name);
					units.add(aUnit);
					return (U) aUnit;
				}
				break;
			default:
				if (logger.isLoggable(Level.FINEST)) {
					logger.log(Level.FINEST,
							"Unknown style " + style + "; unit " + unit + " can't be rendered with '" + symbol + "'.");
				}
				break;
			}
			if (LABEL.equals(style) || SYMBOL_AND_LABEL.equals(style)) {
				SimpleUnitFormat.getInstance().label(unit, symbol);
			}
			units.add(unit);
			return unit;
		}

		/**
		 * Adds a new labeled unit to the set.
		 * 
		 * @param units
		 *            the set to add to.
		 * 
		 * @param unit
		 *            the unit being added.
		 * @param text
		 *            the text for the unit.
		 * @param style
		 *            style of the unit.
		 * @return <code>unit</code>.
		 * @since 1.0.1
		 */
		@SuppressWarnings("unchecked")
		public static <U extends Unit<?>> U addUnit(Set<Unit<?>> units, U unit, String text, UnitStyle style) {
			switch (style) {
			case NAME:
				if (text != null && unit instanceof AbstractUnit) {
					AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
					aUnit.setName(text);
					units.add(aUnit);
					return (U) aUnit;
				}
				break;
			case SYMBOL:
				if (text != null && unit instanceof AbstractUnit) {
					AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
					aUnit.setSymbol(text);
					units.add(aUnit);
					return (U) aUnit;
				}
				break;
			case SYMBOL_AND_LABEL:
				if (text != null && unit instanceof AbstractUnit) {
					AbstractUnit<?> aUnit = (AbstractUnit<?>) unit;
					aUnit.setSymbol(text);
					units.add(aUnit);
					SimpleUnitFormat.getInstance().label(aUnit, text);
					return (U) aUnit;
				} else { // label in any case, returning below
					SimpleUnitFormat.getInstance().label(unit, text);
				}
				break;
			case LABEL:
				SimpleUnitFormat.getInstance().label(unit, text);
				break;
			default:
				logger.log(Level.FINEST,
						"Unknown style " + style + "; unit " + unit + " can't be rendered with '" + text + "'.");
				break;
			}
			units.add(unit);
			return unit;
		}
	}
}
