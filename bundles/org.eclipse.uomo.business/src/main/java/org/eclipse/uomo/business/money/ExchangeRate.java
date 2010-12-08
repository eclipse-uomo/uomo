/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.business.money;

import java.util.Date;

import org.eclipse.uomo.business.types.IMoney;

import com.ibm.icu.util.Currency;
import com.ibm.icu.util.DateInterval;

/**
 * @author Werner Keil
 * @version 0.2.1
 */
public class ExchangeRate {

	/**
	 * Holds the source currency.
	 */
	private final Currency source;

	/**
	 * Holds the target currency.
	 */
	private final Currency target;

	/**
	 * Holds the exchange factor.
	 */
	private final Number factor;

	/**
	 * Holds the effective (start) date.
	 */
	private final Date date;

	private final DateInterval interval;
	
	public ExchangeRate(Currency source, Currency target, Number factor,
			Date fromDate, Date toDate) {
		super();
		this.source = source;
		this.target = target;
		this.factor = factor;
		this.date = fromDate;
		this.interval = new DateInterval(fromDate.getTime(), toDate.getTime());
	}
	
	public ExchangeRate(Currency source, Currency target, Number factor,
			Date date) {
		this(source, target, factor, date, date);
	}

	public ExchangeRate(Currency source, Currency target, Number factor) {
		this(source, target, factor, new Date());
	}

	public Currency getSource() {
		return source;
	}

	@SuppressWarnings("unchecked")
	public Currency getSourceUnit() {
		return (CurrencyUnit<IMoney>) source;
	}

	public Currency getTarget() {
		return target;
	}

	@SuppressWarnings("unchecked")
	public Currency getTargetUnit() {
		return (CurrencyUnit<IMoney>) target;
	}

	public Number getFactor() {
		return factor;
	}

	public Date getDate() {
		return date;
	}

	public DateInterval getInterval() {
		return interval;
	}
}
