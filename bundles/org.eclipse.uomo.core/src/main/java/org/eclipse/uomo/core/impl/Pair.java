/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.core.impl;

import java.math.BigDecimal;

import org.eclipse.uomo.core.ICode;
import org.eclipse.uomo.core.IValue;

// TODO make this really generic, like Pair<F,S>
public class Pair implements ICode, IValue {

	private BigDecimal value;
	private String code;
	/**
	 * @param value
	 * @param code
	 */
	public Pair(BigDecimal value, String code) {
		super();
		this.value = value;
		this.code = code;
	}
	/**
	 * @return the value
	 */
	public Number getValue() {
		return value;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}	
}
