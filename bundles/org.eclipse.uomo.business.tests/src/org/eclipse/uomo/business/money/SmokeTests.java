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

package org.eclipse.uomo.business.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.uomo.business.internal.CurrencyUnit;
import org.eclipse.uomo.business.internal.ExchangeRate;
import org.eclipse.uomo.business.internal.ExchangeRateType;
import org.eclipse.uomo.business.internal.MonetaryAmount;
import org.eclipse.uomo.business.types.IMoney;

import java.math.RoundingMode;
import java.util.Locale;

import org.junit.Test;
import java.util.logging.Logger;

public class SmokeTests {
	private static final Logger LOGGER = Logger.getLogger(SmokeTests.class.getName());

	private static final ExchangeRateType RATE_TYPE = ExchangeRateType
			.of("EZB");

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = MoneyUnit.of("CHF");
		MonetaryAmount amount1 = MoneyAmount.of(1.0d, currency);
		MonetaryAmount amount2 = MoneyAmount.of(1.0d, currency);
		MonetaryAmount amount3 = amount1.add(amount2);
		LOGGER.fine(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals("ISO-4217", currency.getNamespace());
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}

	@Test
	public void testCreateIMoney() {
		// Creating one
		IMoney amount1 = MoneyAmount.of("CHF", 1.0d);
		IMoney amount2 = MoneyAmount.of("CHF", 1.0d);
		IMoney amount3 = (IMoney) amount1.add(amount2);
		LOGGER.fine(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals(1.0d, amount1.value().doubleValue(), 0);
		assertEquals(1.0d, amount2.value().doubleValue(), 0);
		assertEquals(2.0d, amount3.value().doubleValue(), 0);
	}
}

