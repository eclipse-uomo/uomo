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

import static org.eclipse.uomo.core.impl.OutputHelper.*;
import static org.eclipse.uomo.units.SI.METRE;
import static org.eclipse.uomo.units.SI.Prefix.CENTI;
import static org.eclipse.uomo.units.SI.Prefix.KILO;
import static org.eclipse.uomo.units.SI.Prefix.MILLI;
import static org.eclipse.uomo.units.impl.system.USCustomary.ELECTRON_VOLT;
import static org.eclipse.uomo.units.impl.system.USCustomary.FOOT;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Locale;

import org.eclipse.uomo.units.AbstractUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitsofmeasurement.quantity.Length;
import org.unitsofmeasurement.unit.Unit;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version $Revision: 206 $, $Date: 2010-02-25 02:40:17 +0100 (Do, 25 Feb 2010)
 *          $
 */
public class UnitFormatTest {
	private static final String COMPARISON_FOOT = "ft";
	private static final String COMPARISON_KM = "km";
	private static final Locale COMPARISON_LOCALE = Locale.UK;
	private static final Locale MULTI_LOCALE = Locale.ENGLISH;

	LocalUnitFormatImpl format;
	Unit<Length> cm;
	Unit<Length> mm;
	Unit<Length> foot;

	@Before
	public void setUp() throws Exception {
		// setName(UCUMFormatTest.class.getSimpleName());
		cm = CENTI(METRE);
		mm = MILLI(METRE);
		foot = FOOT;
	}

	@After
	public void tearDown() throws Exception {
		// super.tearDown();
	}

	@Test
	public void testDefault() {
		format = LocalUnitFormatImpl.getInstance();
		// format.format(unit, appendable);
		String formattedText = format.format(cm);
		println(formattedText);
		// System.out.println(unit2);
		formattedText = format.format(mm);
		println(formattedText);
		formattedText = format.format(foot);
		print(formattedText);
	}

	@Test
	public void testGetInstanceLocale() {
		format = LocalUnitFormatImpl.getInstance(COMPARISON_LOCALE);
		String formattedText = format.format(cm);
		print(formattedText);
		// System.out.println(unit2);
		formattedText = format.format(mm);
		print(formattedText);
		formattedText = format.format(foot);
		print(formattedText);
		assertEquals(COMPARISON_FOOT, formattedText);
	}

	@Test
	public void testUSVolt() {
		print(ELECTRON_VOLT.getDimension().toString());
		println(ELECTRON_VOLT.toString());
	}

	@Test
	public void testSubMultiples() {
		Unit<Length> u = CENTI(METRE);
		println(u.toString());
	}

	/**
	 * Tests the {@link AbstractUnit#toString()} method, which is backed by
	 * {@link BaseFormat}.
	 * 
	 * @see http://kenai.com/jira/browse/JSR_275-43
	 */
	@Test
	public void testMultiples() {
		format = LocalUnitFormatImpl.getInstance(MULTI_LOCALE);
		assertEquals("m", format.format(METRE));
		// Multiples
		assertEquals(COMPARISON_KM, format.format(KILO(METRE)));
		assertEquals(COMPARISON_KM, format.format(METRE.multiply(1000)));
		assertEquals(COMPARISON_KM, format.format(METRE.multiply(1000d)));
		assertEquals("Tm", METRE.multiply(BigInteger.TEN.pow(12).longValue())
				.toString());
		// Submultiples
		assertEquals("cm", format.format(METRE.divide(100d)));
		assertEquals("mm", format.format(METRE.divide(1000d)));
		assertEquals("fm", format.format(METRE.divide(BigInteger.TEN.pow(15).
				longValue())));
	}
}
