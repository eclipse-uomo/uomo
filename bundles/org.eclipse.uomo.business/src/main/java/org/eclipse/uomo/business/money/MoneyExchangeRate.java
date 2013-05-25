/**
 * Copyright (c) 2012, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.business.money;

import org.eclipse.uomo.business.internal.CurrencyUnit;
import org.eclipse.uomo.business.internal.ExchangeRate;
import org.eclipse.uomo.business.internal.ExchangeRateType;

/**
 * @version 0.3
 * @author Werner Keil
 * 
 */
@SuppressWarnings("deprecation")
public class MoneyExchangeRate extends ExchangeRate {
	public static final ExchangeRateType DEFAULT_RATETYPE = ExchangeRateType.of("DEFAULT");
	
	public MoneyExchangeRate(CurrencyUnit base, CurrencyUnit term, Number factor) {
		super(DEFAULT_RATETYPE, base, term, factor, "DEFAULT");
	}
}
