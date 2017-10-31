/**
 * Copyright (c) 2005, 2010, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Jean-Marie Dautelle - initial API and implementation
 */
package org.eclipse.uomo.units;

import java.math.BigInteger;

import org.eclipse.uomo.units.impl.converter.RationalConverter;
import javax.measure.Quantity;
import javax.measure.Unit;

// ///////////////////
// BINARY PREFIXES  //
// ///////////////////
/**
 * Utility class holding binary prefixes.
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.1 ($Revision: 212 $), $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 * @see <a href="http://en.wikipedia.org/wiki/Binary_prefix">Wikipedia: Binary
 *      Prefix</a>
 */
public final class BinaryPrefix {

	private BinaryPrefix() {
		// Utility class no visible constructor.
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>10</sup></code> (binary prefix).
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1024)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<?> KIBI(Unit<?> unit) {
		return unit.multiply(K);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>20</sup></code> (binary prefix).
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1048576)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<?> MEBI(Unit<?> unit) {
		return unit.multiply(K2);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>30</sup></code> (binary prefix).
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1073741824)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> GIBI(Unit<?> unit) {
		return unit.multiply(K3);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>40</sup></code> (binary prefix).
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1099511627776L)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<?> TEBI(Unit<?> unit) {
		return unit.multiply(1099511627776L);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>50</sup></code> (binary prefix).
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1125899906842624L)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<?> PEBI(Unit<?> unit) {
		return unit.multiply(1125899906842624L);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>60</sup></code> (binary prefix).
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1152921504606846976L)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<?> EXBI(Unit<?> unit) {
		return unit.multiply(1152921504606846976L);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>70</sup></code> (binary prefix).
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.transform(2<sup>70</sup>)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<?> ZEBI(Unit<?> unit) {
		return unit.transform(K70);
	}

	/**
	 * Returns the specified unit multiplied by the factor
	 * <code>2<sup>80</sup></code> (binary prefix).
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.transform(2<sup>80</sup>)</code>.
	 */
	public static <Q extends Quantity<Q>> Unit<?> YOBI(Unit<?> unit) {
		return unit.transform(K80);
	}

	// ///////////////////
	//  JDEC PREFIXES   //
	// ///////////////////
	
	/**
	 * <p>
	 * kilo (K)
	 * </p>
	 * This JEDEC prefix is equivalent to <{@link #KIBI}>.
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1024)</code>.
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/JEDEC_memory_standards">Wikipedia:
	 *      JEDEC memory standards</a>
	 */
	public static <Q extends Quantity<Q>> Unit<?> JEDEC_KILO(Unit<?> unit) {
		return unit.multiply(K);
	}

	/**
	 * <p>
	 * mega (M)
	 * </p>
	 * This JEDEC prefix is equal to <{@link #MEBI}>.
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1048576)</code>.
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/JEDEC_memory_standards">Wikipedia:
	 *      JEDEC memory standards</a>
	 */
	public static <Q extends Quantity<Q>> Unit<?> JEDEC_MEGA(Unit<?> unit) {
		return unit.multiply(K2);
	}

	/**
	 * <p>
	 * giga (G)
	 * </p>
	 * This JEDEC prefix is equal to <{@link #GIBI}>.
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1073741824)</code>.
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/JEDEC_memory_standards">Wikipedia:
	 *      JEDEC memory standards</a>
	 */
	public static <Q extends Quantity<Q>> Unit<?> JEDEC_GIGA(Unit<?> unit) {
		return unit.multiply(K3);
	}

	// Holds prefix converters (optimization).
	private static int K = 1024;

	private static int K2 = 1048576;

	private static int K3 = 1073741824;

	private static RationalConverter K70 = new RationalConverter(BigInteger
			.valueOf(2).pow(70), BigInteger.ONE);

	private static RationalConverter K80 = new RationalConverter(BigInteger
			.valueOf(2).pow(80), BigInteger.ONE);
}
