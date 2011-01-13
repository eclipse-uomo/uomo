/**
 * Copyright (c) 2005, 2011, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Eric Russell - initial API and implementation
 */
package org.eclipse.uomo.units;

import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

/**
 * <p> This interface provides a set of mappings between
 *     {@link Unit Units} and symbols (both ways),
 *     and from {@link org.unitsofmeasurement.UnitConverter
 *     UnitConverter}s to prefixes symbols (also both ways).</p>
 *     
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author Eric Russell
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.1 , $Date: 2011-01-11 $
 */
public interface SymbolMap {

    /**
     * Attaches a label to the specified unit. For example:[code]
     *    symbolMap.label(DAY.multiply(365), "year");
     *    symbolMap.label({@link USCustomary USCustomary}.FOOT, "ft");
     * [/code]
     *
     * @param unit the unit to label.
     * @param symbol the new symbol for the unit.
     * @throws UnsupportedOperationException if setting a unit label
     *         is not allowed.
     */
    void label(Unit<?> unit, String symbol);

    /**
     * Attaches an alias to the specified unit. Multiple aliases may be
     * attached to the same unit. Aliases are used during parsing to
     * recognize different variants of the same unit.[code]
     *     symbolMap.alias({@link USCustomary USCustomary}.FOOT, "foot");
     *     symbolMap.alias({@link USCustomary USCustomary}.FOOT, "feet");
     *     symbolMap.alias({@link SI SI}.METER, "meter");
     *     symbolMap.alias({@link SI SI}.METER, "metre");
     * [/code]
     *
     * @param unit the unit to label.
     * @param symbol the new symbol for the unit.
     * @throws UnsupportedOperationException if setting a unit alias
     *         is not allowed.
     */
    void alias(Unit<?> unit, String symbol);

    /**
     * Attaches a label to the specified prefix. For example:[code]
     *    symbolMap.prefix(new RationalConverter(1000000000, 1), "G"); // GIGA
     *    symbolMap.prefix(new RationalConverter(1, 1000000), "Âµ"); // MICRO
     * [/code]
     *
     * @param cvtr the unit converter.
     * @param prefix the prefix for the converter.
     * @throws UnsupportedOperationException if setting a prefix
     *         is not allowed.
     */
    void prefix(UnitConverter cvtr, String prefix);

    /**
     * Returns the unit for the specified symbol.
     *
     * @param symbol the symbol.
     * @return the corresponding unit or <code>null</code> if none.
     */
    Unit<?> getUnit(String symbol);

    /**
     * Returns the symbol (label) for the specified unit.
     *
     * @param unit the corresponding symbol.
     * @return the corresponding symbol or <code>null</code> if none.
     */
    String getSymbol(Unit<?> unit);

    /**
     * Returns the unit converter for the specified prefix.
     *
     * @param prefix the prefix symbol.
     * @return the corresponding converter or <code>null</code> if none.
     */
    UnitConverter getConverter(String prefix);

    /**
     * Returns the prefix for the specified converter.
     *
     * @param converter the unit converter.
     * @return the corresponding prefix or <code>null</code> if none.
     */
    String getPrefix(UnitConverter converter);
}
