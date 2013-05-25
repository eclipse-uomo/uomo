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

import java.util.Enumeration;

import org.eclipse.uomo.business.internal.ExchangeRate;
import org.eclipse.uomo.business.internal.ExchangeRateType;

import com.ibm.icu.util.Currency;

/**
 * @version 0.3
 * @author Werner Keil
 * 
 */
public class MoneyExchangeRate implements ExchangeRate {

	private final ExchangeRateType type;

	private final Currency source;

	private final Currency target;

	private final Number factor;

	public MoneyExchangeRate(Currency source, Currency target, Number factor) {
		this.source = source;
		this.target = target;
		this.factor = factor;
		type = ExchangeRateType.of("DEFAULT");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getExchangeRateType()
	 */
	public ExchangeRateType getExchangeRateType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getSource()
	 */
	public Currency getSource() {
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getTarget()
	 */
	public Currency getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getFactor()
	 */
	public Number getFactor() {
		return factor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getValidFrom()
	 */
	public Long getValidFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getValidUntil()
	 */
	public Long getValidUntil() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#isValid()
	 */
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getProvider()
	 */
	public String getProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getExchangeRateChain()
	 */
	public ExchangeRate[] getExchangeRateChain() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#isDerived()
	 */
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#isIdentity()
	 */
	public boolean isIdentity() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.uomo.business.money.ExchangeRate#getAttribute(java.lang.String
	 * , java.lang.Class)
	 */
	public <T> T getAttribute(String key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uomo.business.money.ExchangeRate#getAttributeKeys()
	 */
	public Enumeration<String> getAttributeKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.uomo.business.money.ExchangeRate#getAttributeType(java.lang
	 * .String)
	 */
	public Class<?> getAttributeType(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}
