/**
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units;

import java.io.IOException;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Locale;

import org.eclipse.uomo.units.impl.format.LocalUnitFormat;
import org.eclipse.uomo.units.impl.system.USCustomary;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;


/**
 * <p> This class provides the interface for formatting and parsing {@link
 *     AbstractUnit units}.</p>
 *
 * <p> For all metric units, the 20 SI prefixes used to form decimal
 *     multiples and sub-multiples of SI units are recognized.
 *     {@link USCustomary US Customary} units are directly recognized. For example:[code]
 *        Unit.valueOf("m°C").equals(SI.MILLI(SI.CELSIUS))
 *        Unit.valueOf("kW").equals(SI.KILO(SI.WATT))
 *        Unit.valueOf("ft").equals(SI.METRE.multiply(3048).divide(10000))[/code]</p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.6, $Date: 2017-07-30 $
 * 
 */
public abstract class AbstractUnitFormat extends Format implements UnitFormat {

   /**
     * Returns the {@link SymbolMap} for this unit format.
     *
     * @return the symbol map used by this format.
     */
    protected abstract SymbolMap getSymbolMap();

    /**
     * Formats the specified unit.
     *
     * @param unit the unit to format.
     * @param appendable the appendable destination.
     * @return The appendable destination passed in as {@code appendable},
     *         with formatted text appended.
     * @throws IOException if an error occurs.
     */
    public abstract Appendable format(Unit<?> unit, Appendable appendable)
            throws IOException;

    /**
     * Parses a portion of the specified <code>CharSequence</code> from the
     * specified position to produce a unit. If there is no unit to parse
     * {@link AbstractUnit#ONE} is returned.
     *
     * @param csq the <code>CharSequence</code> to parse.
     * @param cursor the cursor holding the current parsing index.
     * @return the unit parsed from the specified character sub-sequence.
     * @throws IllegalArgumentException if any problem occurs while parsing the
     *         specified character sequence (e.g. illegal syntax).
     */
    public abstract Unit<?> parse(CharSequence csq, ParsePosition cursor)
            throws IllegalArgumentException;

    /**
     * Return a formatter for CurrencyAmount objects in the given
     * locale.
     * @param locale desired locale
     * @return a formatter object
     * @stable ICU 3.0
     */
    public static AbstractUnitFormat getUnitFormat(Locale locale) {
        return LocalUnitFormat.getInstance(locale);
    }

    /**
     * Return a formatter for CurrencyAmount objects in the default
     * locale.
     * @return a formatter object
     * @stable ICU 3.0
     */
    public static AbstractUnitFormat getUnitFormat() {
        return getUnitFormat(Locale.getDefault());
    }
    
    /**
     * Convenience method equivalent to {@link #format(AbstractUnit, Appendable)}
     * except it does not raise an IOException.
     *
     * @param unit the unit to format.
     * @param dest the appendable destination.
     * @return the specified <code>StringBuilder</code>.
     */
    final StringBuilder format(AbstractUnit<?> unit, StringBuilder dest) {
        try {
            return (StringBuilder) this.format(unit, (Appendable) dest);
        } catch (IOException ex) {
            throw new Error(ex); // Can never happen.
        }
    }
    
    /**
     * Formats an object to produce a string. This is equivalent to <blockquote> {@link #format(Unit, StringBuilder) format}<code>(unit,
     *         new StringBuilder()).toString();</code> </blockquote>
     *
     * @param obj
     *          The object to format
     * @return Formatted string.
     * @exception IllegalArgumentException
     *              if the Format cannot format the given object
     */
    public final String format(Unit<?> unit) {
      if (unit instanceof AbstractUnit) {
        return format((AbstractUnit<?>) unit, new StringBuilder()).toString();
      } else {
        try {
          return (this.format(unit, new StringBuilder())).toString();
        } catch (IOException ex) {
          throw new ParserException(ex); // Should never happen.
        }
      }
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2046025267890654321L;
}
