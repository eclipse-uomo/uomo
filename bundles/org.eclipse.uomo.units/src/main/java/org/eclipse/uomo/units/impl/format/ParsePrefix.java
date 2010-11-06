/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl.format;

import java.math.BigInteger;

import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.impl.RationalConverter;


/**
 * This class represents the prefixes recognized when parsing/formatting.
 *
 * @version 1.0
 */
enum ParsePrefix  {

    YOTTA(new RationalConverter(BigInteger.TEN.pow(24), BigInteger.ONE)),
    ZETTA(new RationalConverter(BigInteger.TEN.pow(21), BigInteger.ONE)),
    EXA(new RationalConverter(BigInteger.TEN.pow(18), BigInteger.ONE)),
    PETA(new RationalConverter(BigInteger.TEN.pow(15), BigInteger.ONE)),
    TERA(new RationalConverter(BigInteger.TEN.pow(12), BigInteger.ONE)),
    GIGA(new RationalConverter(BigInteger.TEN.pow(9), BigInteger.ONE)),
    MEGA(new RationalConverter(BigInteger.TEN.pow(6), BigInteger.ONE)),
    KILO(new RationalConverter(BigInteger.TEN.pow(3), BigInteger.ONE)),
    HECTO(new RationalConverter(BigInteger.TEN.pow(2), BigInteger.ONE)),
    DEKA(new RationalConverter(BigInteger.TEN.pow(1), BigInteger.ONE)),
    DECI(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(1))),
    CENTI(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(2))),
    MILLI(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(3))),
    MICRO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(6))),
    NANO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(9))),
    PICO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(12))),
    FEMTO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(15))),
    ATTO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(18))),
    ZEPTO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(21))),
    YOCTO(new RationalConverter( BigInteger.ONE, BigInteger.TEN.pow(24)));

    private final AbstractConverter converter;

    /**
     * Creates a new prefix.
     *
     * @param converter the associated unit converter.
     */
    ParsePrefix (AbstractConverter converter) {
        this.converter = converter;
    }

    /**
     * Returns the corresponding unit converter.
     *
     * @return the unit converter.
     */
    public AbstractConverter getConverter() {
        return converter;
    }
}
